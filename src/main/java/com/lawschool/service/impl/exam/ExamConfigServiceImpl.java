package com.lawschool.service.impl.exam;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.IdWorker;

import com.lawschool.beans.StuMedia;
import com.lawschool.beans.SysLogEntity;
import com.lawschool.beans.exam.ExamConfig;
import com.lawschool.beans.exam.ExamDetail;
import com.lawschool.beans.exam.ExamQueConfig;
import com.lawschool.beans.exam.ExamQuestions;
import com.lawschool.dao.exam.ExamConfigDao;
import com.lawschool.dao.exam.ExamDetailDao;
import com.lawschool.dao.exam.ExamQueConfigDao;
import com.lawschool.dao.exam.ExamQuestionsDao;
import com.lawschool.service.exam.ExamConfigService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Query;
import com.lawschool.util.UtilValidate;

@Service
public class ExamConfigServiceImpl extends ServiceImpl<ExamConfigDao, ExamConfig> implements ExamConfigService {

	@Autowired
	private ExamConfigDao examConfigDao;

	@Autowired
	private ExamQuestionsDao examQuestionsDao;

	@Autowired
	private ExamQueConfigDao examQueConfigDao;

	@Autowired
	private ExamDetailDao examDetailDao;

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
	public void examConfig(String type, ExamConfig examConfig, List<ExamQueConfig> examQueList,
			List<ExamQuestions> queList) throws Exception {
		if ("1".equals(type)) {
			// 预览试卷
			preview(examConfig, examQueList, queList);
		} else {
			// 生成试卷
			generate(examConfig, examQueList, queList);
		}
	}

	private void generate(ExamConfig examConfig, List<ExamQueConfig> examQueList, List<ExamQuestions> queList)
			throws Exception {
		// TODO Auto-generated method stub
		// 获取考试出题类型
		examConfig.setId(IdWorker.getIdStr());
		// 题目数量
		int queNum = 0;
		int score = 0;

		List<ExamQuestions> eqList = new ArrayList<ExamQuestions>();
		if ("1".equals(examConfig.getQuestionWay())) {
			// 随机出题
			// 获取题目配置规则
			for(int i=0; i<50;i++) {
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
					//获取试题答案 根据试题类型获取答案选项
					
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

	
}
