package com.lawschool.service.impl.exam;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.base.Page;
import com.lawschool.beans.auth.AuthRelationBean;
import com.lawschool.beans.exam.ExamConfig;
import com.lawschool.beans.exam.ExamDetail;
import com.lawschool.beans.exam.ExamQueConfig;
import com.lawschool.beans.exam.ExamQuestions;
import com.lawschool.dao.exam.ExamConfigDao;
import com.lawschool.dao.exam.ExamDetailDao;
import com.lawschool.dao.exam.ExamQueConfigDao;
import com.lawschool.dao.exam.ExamQuestionsDao;
import com.lawschool.form.*;
import com.lawschool.service.AnswerService;
import com.lawschool.service.TestQuestionService;
import com.lawschool.service.auth.AuthRelationService;
import com.lawschool.service.exam.ExamConfigService;
import com.lawschool.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ExamConfigServiceImpl extends AbstractServiceImpl<ExamConfigDao, ExamConfig> implements ExamConfigService {

	@Autowired
	private ExamConfigDao examConfigDao;

	@Autowired
	private ExamQuestionsDao examQuestionsDao;

	@Autowired
	private ExamQueConfigDao examQueConfigDao;

	@Autowired
	private ExamDetailDao examDetailDao;

	@Autowired
	private AuthRelationService authService;

	@Autowired
	private TestQuestionService testQuestionService;


	@Autowired
	private  AnswerService answerService;

	/**
	 * 删除考试配置
	 * @param id
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void deleteExamConfig(String id) throws Exception {
		// 获取考试详细信息
		ExamConfig examConfig = examConfigDao.selectById(id);
		// 获取当前时间
		Date date = new Date();
		if (date.after(examConfig.getStartTime())) {
			throw new Exception("考试开始后不允许删除");
		} else {
			// 删除主表
			examConfigDao.deleteById(id);
			//删除关联关系表
			deleteRelated(id);
		}
	}
	
	public void updateConfig(ExamConfig examConfig) throws Exception {
		//更新前校对时间
		Date date = new Date();
		if (date.after(examConfig.getStartTime())) {
			throw new Exception("考试开始后不允许修改");
		}else {
			//生成前先删除之前的记录
			deleteRelated(examConfig.getId());
			//更新主表
			examConfigDao.updateById(examConfig);
			//生成试卷
			generate(examConfig);
		}
	}

	/**
	 *
	 * @param examConfig
	 * @throws Exception
	 */
	@Override
	public void generate(ExamConfig examConfig)
			throws Exception {
		// TODO Auto-generated method stub
		// 获取考试出题类型
		List<ExamQueConfig> examQueList = examConfig.getExamQueConfigList();
		// 题目数量
		int queNum = 0;
		int score = 0;
		//保存考试基本配置
		examConfig.setId(GetUUID.getUUIDs("EC"));
		examConfig.setOptTime(new Date());
		examConfig.setCreateTime(new Date());
		dao.insert(examConfig);

		if("10027".equals(examConfig.getGroupForm())){
			//统一组卷,一套试题
			//保存考试详情信息
			ExamDetail examDetail = saveExamDetail(examConfig.getId());
			examDetailDao.insert(examDetail);
			//预览的试卷当做一套试题
			List<QuestForm> qfList = examConfig.getQfList();
			if(UtilValidate.isNotEmpty(qfList)){
				//预览了试卷，将预览试卷的题目直接保存
				//保存考试试题信息
				if("10033".equals(examConfig.getQuestionWay())){
					for (ExamQueConfig examQueConfig : examQueList) {
						//保存考试题目配置表
						saveExamQueConfig(examConfig.getId(),examQueConfig);
					}
				}
				saveExamQueByQueList(qfList,examConfig.getId(),examDetail.getId());
			}else{
				//没有预览试卷，根据出题方式出题
				if("10033".equals(examConfig.getQuestionWay())){
					//随机出题
					for (ExamQueConfig examQueConfig : examQueList) {
						// 根据配置从题库中获取
						//保存考试题目配置表
						saveExamQueConfig(examConfig.getId(),examQueConfig);
						//根据考试配置获取题目ID
						List<String> idLists = getIdList(examQueConfig);
						saveExamQueByIdList(idLists,examConfig.getId(),examDetail.getId(),examQueConfig.getQuestionScore()/examQueList.size());
					}
				}else{
					//自主出题，直接获取试题ID存
					List<CommonForm> commonForms = examConfig.getCommonForms();
					saveExamQueByComm(commonForms,examConfig.getId(),examDetail.getId());
				}
			}
		}else{
			//随机组卷
			int num = 50; //TODO 待改从配置文件取
			//随机组卷：随机组卷时只能随机出题
			//是否预览试卷
			List<QuestForm> qfList = examConfig.getQfList();
			if(UtilValidate.isNotEmpty(qfList)){
				//不为空
				ExamDetail examDetail = saveExamDetail(examConfig.getId());
				examDetailDao.insert(examDetail);
				saveExamQueByQueList(qfList,examConfig.getId(),examDetail.getId());
				//在生成49套试卷
				generateNumExam(num-1,examConfig.getId(),examQueList);
			}else{
				generateNumExam(num,examConfig.getId(),examQueList);
			}
		}

		// 存权限表
		//存权限表
		String[] deptIdArr=examConfig.getDeptArr();
		String[] userIdArr=examConfig.getUserArr();
		authService.insertAuthRelation(deptIdArr, userIdArr, examConfig.getId(), "ExamConfig", examConfig.getGroupForm());
		
	}

	// 预览试卷
	@Override
	public List<QuestForm> preview(ExamConfig examConfig) throws Exception {
		// 获取考试出题类型
		// 题目数量
		int queNum = 0;
		int score = 0;
        List<ExamQueConfig> examQueList = examConfig.getExamQueConfigList();

		List<QuestForm> eqList = new ArrayList<QuestForm>();
		if ("10033".equals(examConfig.getQuestionWay())) {
			// 随机出题
			// 获取题目配置规则
			for (ExamQueConfig examQueConfig : examQueList) {
				// 根据配置从题库中获取
				List<String> idList = getIdList(examQueConfig);
				if(UtilValidate.isNotEmpty(idList)) {
					eqList.addAll( getList(idList));
				}
			}
		} else {
		    //
			List<String> idList = examConfig.getIdList();
			if(UtilValidate.isNotEmpty(idList)) {
				eqList = getList(idList);
			}
		}
		return eqList;
	}

	/**
	 * 根据考试配置主键删除关联关系表
	 * @param id
	 */
	public void deleteRelated(String id) {
		// 删除考试详情表
		examDetailDao.delete(new EntityWrapper<ExamDetail>().eq("EXAM_CONFIG_ID", id));
		//删除考试题型设置
		examQueConfigDao.delete(new EntityWrapper<ExamQueConfig>().eq("LAW_EXAM_CONFIG_ID", id));
		//删除考试试卷表
		examQuestionsDao.delete(new EntityWrapper<ExamQuestions>().eq("EXAM_CONFIG_ID", id));
		//删除权限关联表
		authService.delete(new EntityWrapper<AuthRelationBean>().eq("FUNCTION_ID", id));

	}

	/**
	 * 根據試題編號獲取试题详情
	 * @param idList
	 * @return
	 */
	@Override
	public List<QuestForm> getList(List<String> idList){

		List<QuestForm> eqList = testQuestionService.findByIds(idList);
		List<AnswerForm> answerForms = answerService.findByQuestionIds(idList);
		// 遍历处理选项信息
		for(QuestForm qf : eqList){
			String qid = qf.getId();
			List<AnswerForm> tempList = new ArrayList<AnswerForm>();

			for(AnswerForm af : answerForms){
				String aqid = af.getQuestionId();
				if(qid.equals(aqid)){
					tempList.add(af);
				}
			}

			qf.setAnswer(tempList);
		}
		return  eqList;
	}

	/**
	 * 获取题目ID列表
	 * @param examQueConfig
	 * @return
	 */
	private List<String> getIdList(ExamQueConfig examQueConfig){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("specialKnowledgeId",examQueConfig.getSpecialKnowledgeId());
		paramsMap.put("questionType",examQueConfig.getQuestionType());
		paramsMap.put("num",examQueConfig.getQuestionNumber());
		List<String> idList = testQuestionService.findByNum(paramsMap);
		return idList;
	}

	/**
	 * 保存考试详情信息
	 * @param examConfigId
	 */
	private ExamDetail saveExamDetail(String examConfigId){
		ExamDetail examDetail = new ExamDetail();
		examDetail.setId(GetUUID.getUUIDs("ED"));
		examDetail.setOptTime(new Date());
		examDetail.setCreateTime(new Date());
		examDetail.setExamConfigId(examConfigId);
		return examDetail;
	}

	private void saveExamQueConfig(String examConfigId,ExamQueConfig examQueConfig){
		examQueConfig.setId(GetUUID.getUUIDs("EQC"));
		examQueConfig.setLawExamConfigId(examConfigId);
		examQueConfigDao.insert(examQueConfig);
	}
	/**
	 * 根据试题详情保存试题信息
	 * @param qfList
	 * @param examConfigId
	 * @param examDetailId
	 */
	private void saveExamQueByQueList(List<QuestForm> qfList,String examConfigId,String examDetailId){
		for (QuestForm qf : qfList) {
			ExamQuestions examQuestions = new ExamQuestions();
			examQuestions.setExamConfigId(examConfigId);
			examQuestions.setId(GetUUID.getUUIDs("EQ"));
			examQuestions.setExamDetailId(examDetailId);
			examQuestions.setExamLibraryId(qf.getId());
			//
			//examQuestions.setScore();
			examQuestionsDao.insert(examQuestions);
		}
	}

	/**
	 * 根据试题编号保存试题信息,随机出题
	 * @param idLists
	 * @param examConfigId
	 * @param examDetailId
	 */
	private void saveExamQueByIdList(List<String> idLists,String examConfigId,String examDetailId,float score){
		for (String tqId : idLists){
			ExamQuestions examQuestions = new ExamQuestions();
			examQuestions.setExamConfigId(examConfigId);
			examQuestions.setId(GetUUID.getUUIDs("EQ"));
			examQuestions.setExamDetailId(examDetailId);
			examQuestions.setExamLibraryId(tqId);
			examQuestions.setScore(score);
			examQuestionsDao.insert(examQuestions);
		}
	}

	/**
	 * 根据试题编号保存试题信息,自主出题
	 * @param commonForms
	 * @param examConfigId
	 * @param examDetailId
	 */
	private void saveExamQueByComm(List<CommonForm> commonForms,String examConfigId,String examDetailId){
		for (CommonForm commonForm : commonForms){
			ExamQuestions examQuestions = new ExamQuestions();
			examQuestions.setExamConfigId(examConfigId);
			examQuestions.setId(GetUUID.getUUIDs("EQ"));
			examQuestions.setExamDetailId(examDetailId);
			examQuestions.setExamLibraryId(commonForm.getKey());
			examQuestions.setScore(Float.parseFloat(commonForm.getValue()));
			examQuestionsDao.insert(examQuestions);
		}
	}

	/**
	 * 生成不同数量的试卷
	 * @param num 生成数量
	 * @param examConfigId 考试配置ID
	 */
	private void generateNumExam(int num,String examConfigId,List<ExamQueConfig> examQueList){
		for(int i=0;i<num;i++){
			ExamDetail ed = saveExamDetail(examConfigId);
			examDetailDao.insert(ed);
			//随机出题
			for (ExamQueConfig examQueConfig : examQueList) {
				//保存考试题目配置信息
				saveExamQueConfig(examConfigId,examQueConfig);
				// 根据配置从题库中获取
				//根据考试配置获取题目ID
				List<String> idLists = getIdList(examQueConfig);
				saveExamQueByIdList(idLists,examConfigId,ed.getId(),examQueConfig.getQuestionScore()/examQueList.size());
			}
		}
	}

	@Override
	public List<QuestForm> getQueList(List<String> idList, String examDetailId) {
		List<QuestForm> eqList = examConfigDao.findByQueAndEdId(idList,examDetailId);
		List<AnswerForm> answerForms = answerService.findByQuestionIds(idList);
		// 遍历处理选项信息
		for(QuestForm qf : eqList){
			String qid = qf.getId();
			List<AnswerForm> tempList = new ArrayList<AnswerForm>();

			for(AnswerForm af : answerForms){
				String aqid = af.getQuestionId();
				if(qid.equals(aqid)){
					tempList.add(af);
				}
			}

			qf.setAnswer(tempList);
		}
		return  eqList;
	}

	@Override
	public void checkset(CheckSetForm checkSetForm) {

		checkSetForm.setCheckPassword(GetUUID.getUUIDs("CE"));
		if("0".equals(checkSetForm.getIsAibitration())){
			checkSetForm.setAuditCheckPass(GetUUID.getUUIDs("ACE"));
		}

		examConfigDao.checkset(checkSetForm);

	}

	@Override
	public ExamConfig findByCheckPassword(String checkPassword, String checkUserType) {
		return dao.findByCheckPassword(checkPassword,checkUserType);
	}

	/**
	 * 获取考试列表
	 * @param params
	 * @return
	 */
	@Override
	public Result getExamList(Map<String, Object> params) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ExamConfig examConfig = new ExamConfig();
		if(UtilValidate.isNotEmpty(params.get("status"))){
	//		examConfig.s
		}
		if(UtilValidate.isNotEmpty(params.get("examName"))){
			examConfig.setExamName(params.get("examName").toString());
		}
		if(UtilValidate.isNotEmpty(params.get("startTime"))){
			examConfig.setStartTime( sdf.parse(params.get("startTime").toString()));
		}
		if(UtilValidate.isNotEmpty(params.get("endTime"))){
			examConfig.setEndTime(sdf.parse(params.get("endTime").toString()));
		}
		String orderBy = " IS_MUST_TEST, start_time ";
		params.put("orderBy",orderBy);
		Page<ExamConfig> examConfigPage = findPage(new Page<ExamConfig>(params),examConfig);

		return Result.ok().put("page",examConfigPage);
	}

	@Override
	public CheckSetForm getCheckSetting(String id) {
		return dao.getCheckSetting(id);
	}
}
