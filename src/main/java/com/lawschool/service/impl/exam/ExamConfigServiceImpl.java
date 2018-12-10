package com.lawschool.service.impl.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.base.BaseEntity;
import com.lawschool.base.DataEntity;
import com.lawschool.beans.auth.AuthRelationBean;
import com.lawschool.beans.exam.ExamConfig;
import com.lawschool.beans.exam.ExamDetail;
import com.lawschool.beans.exam.ExamQueConfig;
import com.lawschool.beans.exam.ExamQuestions;
import com.lawschool.dao.exam.ExamConfigDao;
import com.lawschool.dao.exam.ExamDetailDao;
import com.lawschool.dao.exam.ExamQueConfigDao;
import com.lawschool.dao.exam.ExamQuestionsDao;
import com.lawschool.service.auth.AuthRelationService;
import com.lawschool.service.exam.ExamConfigService;
import com.lawschool.util.GetUUID;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Query;
import com.lawschool.util.UtilValidate;

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

	/**
	 * 查询试卷列表
	 */
	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String key = (String) params.get("key");
		EntityWrapper<ExamConfig> ew = new EntityWrapper<>();

		if (UtilValidate.isNotEmpty(key)) {
			key = "%" + key + "%";
			ew.addFilter("add_user like {0}", key);
		}
		Page<ExamConfig> page = this.selectPage(new Query<ExamConfig>(params).getPage(), ew);

		return new PageUtils(page);
	}

	/**
	 * 
	 * @Description:查询此用户所有考试信息
	 * @Pwarms:@param userCode
	 * @Pwarms:@param orgCode
	 * @Pwarms:@return
	 * @return:List<ExamConfig>
	 */
	public List<ExamConfig> findByUser(String userCode, String orgCode) {
		// 先查
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userCode", userCode);
		param.put("orgCode", orgCode);
		List<ExamConfig> list = examConfigDao.findByUser(param);
		return list;
	}

	/**
	 * 
	 * @Description:删除考试配置
	 * @Pwarms:@return
	 * @return:int
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
	
	public void updateConfig(ExamConfig examConfig, List<ExamQueConfig> examQueList,
			List<ExamQuestions> queList) throws Exception {
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
			generate(examConfig, examQueList, queList);
		}
	}
	/**
	 * 
	 * @Description:根据考试配置ID获取试卷考试
	 * @Pwarms:@param examConfigId
	 * @return:void
	 */
	public void startExam(String examConfigId) {

	}

	/**
	 * 
	 * @Description:考试配置页面请求
	 * @Pwarms:@param type 预览试卷or生成试卷
	 * @Pwarms:@param examConfig
	 * @Pwarms:@param list
	 * @return:void
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void examConfig(String type, ExamConfig examConfig, List<ExamQueConfig> examQueList,
			List<ExamQuestions> queList) throws Exception {
		if ("1".equals(type)) {
			// 预览试卷
			preview(examConfig, examQueList, queList);
		} else {
			// 生成试卷
			examConfig.setId(GetUUID.getUUIDs("EC"));
			generate(examConfig, examQueList, queList);
			//存考试配置主表
			examConfigDao.insert(examConfig);
		}
	}

	/**
	 * 
	 * @Description:生成考试试题
	 * @Pwarms:@param examConfig
	 * @Pwarms:@param examQueList
	 * @Pwarms:@param queList
	 * @Pwarms:@throws Exception
	 * @return:void
	 */
	private void generate(ExamConfig examConfig, List<ExamQueConfig> examQueList, List<ExamQuestions> queList)
			throws Exception {
		// TODO Auto-generated method stub
		// 获取考试出题类型
		
		// 题目数量
		int queNum = 0;
		int score = 0;

		List<ExamQuestions> eqList = new ArrayList<ExamQuestions>();
		if ("1".equals(examConfig.getQuestionWay())) {
			// 随机出题
			// 获取题目配置规则
			for (int i = 0; i < 50; i++) {
				for (ExamQueConfig examQueConfig : examQueList) {
					// 根据配置从题库中获取
					// TODO
					examQueConfig.getQuestionNumber();
					examQueConfig.getQuestionType();
					List<ExamQuestions> typeList = new ArrayList<ExamQuestions>();
					for (ExamQuestions examQues : typeList) {
						// 保存试题到试题库
						examQues.setId(IdWorker.getIdStr());
						examQues.setExamConfigId(examConfig.getId());
						examQuestionsDao.insert(examQues);
					}
					// 将获取的题目添加到试题中
					eqList.addAll(typeList);
					// 保存题目配置
					examQueConfig.setId(IdWorker.getIdStr());
					examQueConfig.setLawExamConfigId(examConfig.getId());
					examQueConfigDao.insert(examQueConfig);
					// 保存考试试题详情
					ExamDetail entity = new ExamDetail();
					entity.setId(IdWorker.getIdStr());
					entity.setExamConfigId(examConfig.getId());
					examDetailDao.insert(entity);

					queNum += examQueConfig.getQuestionNumber().intValue();
					score += examQueConfig.getQuestionScore().intValue();

				}
			}
			if (queNum != examConfig.getExamCount().intValue() || score != examConfig.getExamScore().intValue()) {
				throw new Exception("随机出题设置的分数题数与总题数总分值不对应");
			}
		}
		
		// 存权限表
		String[] deptId = examConfig.getDeptId();
		String[] userId = examConfig.getUserId();

		authService.insertAuthRelation(deptId, userId, examConfig.getId(), "ExamConfig", examConfig.getGroupForm());
		
	}

	// 预览试卷
	private List<ExamQuestions> preview(ExamConfig examConfig, List<ExamQueConfig> examQueList,
			List<ExamQuestions> queList) throws Exception {
		// 获取考试出题类型
		examConfig.setId(IdWorker.getIdStr());
		// 题目数量
		int queNum = 0;
		int score = 0;

		List<ExamQuestions> eqList = new ArrayList<ExamQuestions>();
		if ("1".equals(examConfig.getQuestionWay())) {
			// 随机出题
			// 获取题目配置规则
			for (ExamQueConfig examQueConfig : examQueList) {
				// 根据配置从题库中获取
				// TODO
				examQueConfig.getQuestionNumber();
				examQueConfig.getQuestionType();
				List<ExamQuestions> typeList = new ArrayList<ExamQuestions>();
				for (ExamQuestions examQues : typeList) {
					// 保存试题到试题库
					examQues.setId(IdWorker.getIdStr());
					examQues.setExamConfigId(examConfig.getId());
					examQuestionsDao.insert(examQues);
					// 获取试题答案 根据试题类型获取答案选项

				}
				// 将获取的题目添加到试题中
				eqList.addAll(typeList);
				// 保存题目配置
				examQueConfig.setId(IdWorker.getIdStr());
				examQueConfig.setLawExamConfigId(examConfig.getId());
				examQueConfigDao.insert(examQueConfig);
				// 保存考试试题详情
				ExamDetail entity = new ExamDetail();
				entity.setId(IdWorker.getIdStr());
				entity.setExamConfigId(examConfig.getId());
				examDetailDao.insert(entity);

				queNum += examQueConfig.getQuestionNumber().intValue();
				score += examQueConfig.getQuestionScore().intValue();

			}
			if (queNum != examConfig.getExamCount().intValue() || score != examConfig.getExamScore().intValue()) {
				throw new Exception("随机出题设置的分数题数与总题数总分值不对应");
			}
		} else {
			for (ExamQuestions que : queList) {
				score += que.getScore();
			}
			queNum = queList.size();
			eqList.addAll(queList);

		}
		return eqList;
	}
	
	/**
	 * 
	 * @Description:根据考试配置主键删除关联关系表
	 * @Pwarms:@param id
	 * @return:void
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
	 * 
	 * @Description:生成阅卷指令
	 * @Pwarms:@return
	 * @return:String
	 */
	public String generatePassword() {
		String readPassword = GetUUID.getUUIDs("YJ");
		return readPassword;
	}

	
}
