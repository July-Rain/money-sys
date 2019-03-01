package com.lawschool.service.impl.exam;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.base.Page;
import com.lawschool.beans.TestQuestions;
import com.lawschool.beans.auth.AuthRelationBean;
import com.lawschool.beans.exam.ExamConfig;
import com.lawschool.beans.exam.ExamDetail;
import com.lawschool.beans.exam.ExamQueConfig;
import com.lawschool.beans.exam.ExamQuestions;
import com.lawschool.dao.exam.*;
import com.lawschool.form.*;
import com.lawschool.service.AnswerService;
import com.lawschool.service.TestQuestionService;
import com.lawschool.service.auth.AuthRelationService;
import com.lawschool.service.exam.ExamConfigService;
import com.lawschool.service.exam.ExamQueConfigService;
import com.lawschool.util.*;
import org.apache.commons.lang3.StringUtils;
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

	@Autowired
	private UserExamDao userExamDao;

	@Autowired
	private ExamQueConfigService examQueConfigService;
	/**
	 * 删除考试配置
	 * @param id
	 * @throws Exception
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result deleteExamConfig(String id) {
		// 获取考试详细信息
		ExamConfig examConfig = dao.selectById(id);
		// 获取当前时间
		Date date = new Date();
		if (date.after(examConfig.getStartTime())) {
			return	Result.error("考试开始后不允许删除");
		} else {
			// 删除主表
			examConfigDao.deleteById(id);
			//删除关联关系表
			deleteRelated(id);
			return Result.ok();
		}
	}

	/**
	 * 根据考试配置主键删除关联关系表
	 * @param id
	 */
	public void deleteRelated(String id) {
		// 删除考试详情表
		examDetailDao.delete(new EntityWrapper<ExamDetail>().eq("EXAM_CONFIG_ID", id));
		//删除考试题型设置
		examQueConfigDao.delete(new EntityWrapper<ExamQueConfig>().eq("EXAM_CONFIG_ID", id));
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
		examQueConfig.setExamConfigId(examConfigId);
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
		String orderBy = "create_time desc, IS_MUST_TEST, start_time ";
		params.put("orderBy",orderBy);
		Page<ExamConfig> examConfigPage = findPage(new Page<ExamConfig>(params),examConfig);

		return Result.ok().put("page",examConfigPage);
	}

	@Override
	public CheckSetForm getCheckSetting(String id) {
		return dao.getCheckSetting(id);
	}

	@Override
	public ExamConfig getExamDetail(String id) {
		ExamConfig examConfig = dao.selectById(id);
		if(UtilValidate.isNotEmpty(examConfig.getSpecialKnowledgeId())){
			examConfig.setSpecialKnowledgeArr(examConfig.getSpecialKnowledgeId().split(","));
		}
		if(UtilValidate.isNotEmpty(examConfig)){
			//获取适用人的id
			String [] userIdArr= authService.getUserIdArr(id,"ExamConfig") ;
			examConfig.setUserArr(userIdArr);
			//获取适用部门的id
			String [] deptIdArr= authService.getDeptIdArr(id,"ExamConfig") ;
			examConfig.setDeptArr(deptIdArr);
		}
		return examConfig;
	}

	@Override
	public Result getOneExam(String id) {
		//获取考试配置信息
		ExamConfig examConfig = dao.selectById(id);
		List<ExamQueConfig> examQueConfiglist = new ArrayList<>();
		if("10033".equals(examConfig.getQuestionWay())){
			//随机出题
			examQueConfiglist = examQueConfigService.getByExamConfigId(examConfig.getId());
			for (ExamQueConfig examQueConfig : examQueConfiglist){
				if(UtilValidate.isNotEmpty(examQueConfig.getSpecialKnowledgeId())) {
					examQueConfig.setSpecialKnowledgeArr(examQueConfig.getSpecialKnowledgeId().split(","));
				}
			}
		}
		//根据考试配置ID找到考试详情ID
		String examDetailId = userExamDao.getExamDetailId(id);
		//根据不同题型定义不同list返回，打乱顺序使用
		//单选
		List<TestQuestions> sinChoicList = new ArrayList<>();
		//多选
		List<TestQuestions> mulChoicList = new ArrayList<>();
		//判断
		List<TestQuestions> judgeList = new ArrayList<>();
		//主观
		List<TestQuestions> subjectList = new ArrayList<>();
		//其他题型
		List<TestQuestions> otherList = new ArrayList<>();
		if ("1".equals(examConfig.getStatus())) {
			//根据考试详情ID获取所有题目ID
			List<String> idList = userExamDao.getQueIdList(examDetailId);
			//获取所有题目详情
			List<TestQuestions> queList = getQueListForOneExam(idList, examDetailId);

			for (TestQuestions que : queList) {
				if ("10004".equals(que.getQuestionType())) {
					//单选
					sinChoicList.add(que);
				} else if ("10005".equals(que.getQuestionType())) {
					//多选
					mulChoicList.add(que);
				} else if ("10006".equals(que.getQuestionType())) {
					//判断
					judgeList.add(que);
				} else if ("10007".equals(que.getQuestionType())) {
					//主观
					subjectList.add(que);
				} else {
					//其他
					otherList.add(que);
				}
			}
		}
		return   Result.ok().put("sinChoicList", sinChoicList).put("mulChoicList", mulChoicList)
				.put("judgeList", judgeList).put("subjectList", subjectList).put("otherList", otherList).put("examConfig", examConfig).put("examQueConfiglist",examQueConfiglist);
	}

	public List<TestQuestions> getQueListForOneExam(List<String> idList, String examDetailId) {
		List<TestQuestions> eqList = examConfigDao.findByQueAndEdIdForExam(idList,examDetailId);
		for(TestQuestions tes : eqList){
			tes.setAnswerList(answerService.getAnswerByQid(tes.getId()));
		}
		return  eqList;
	}
}
