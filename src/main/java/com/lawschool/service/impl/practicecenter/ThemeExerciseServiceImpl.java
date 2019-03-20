package com.lawschool.service.impl.practicecenter;

import java.util.*;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.Collection;
import com.lawschool.beans.User;
import com.lawschool.beans.personalCenter.CollectionEntity;
import com.lawschool.form.*;

import com.lawschool.service.AnswerService;
import com.lawschool.service.TestQuestionService;
import com.lawschool.service.personalCenter.CollectionService;
import com.lawschool.service.practicecenter.ThemeAnswerRecordService;
import com.lawschool.util.RedisUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawschool.beans.practicecenter.ThemeExerciseEntity;
import com.lawschool.dao.practicecenter.ThemeExerciseDao;
import com.lawschool.service.practicecenter.ThemeExerciseService;
import org.springframework.transaction.annotation.Transactional;


/**
 * 主题练习
 * @author xuxiang
 * @date 2018/12/4 11:51
 */
@Service
public class ThemeExerciseServiceImpl extends AbstractServiceImpl<ThemeExerciseDao, ThemeExerciseEntity> implements ThemeExerciseService {

	@Autowired private RedisUtil redisUtil;

	@Autowired private ThemeAnswerRecordService themeAnswerRecordService;

	@Autowired private TestQuestionService testQuestionService;

	@Autowired private AnswerService answerService;

	@Autowired private CollectionService collectionService;

	/**
	 * 主题练习首页列表数据
	 * Version2.0
	 * @param userId
	 * @return
	 */
    @Override
    public List<ThemeExerciseForm> indexList(String userId){

    	// 获取主题信息关联已做任务信息
		List<ThemeExerciseForm> list = dao.findAllByUser(userId, ThemeExerciseEntity.STATUS_FILE);

		List<ThemeExerciseForm> resultList = new ArrayList<>(list.size());

		// 获取主题对应的题库数量
		Map<String, String> numMap = testQuestionService.countByThemeId();

		for(ThemeExerciseForm form : list){
			String key = form.getThemeId();
			String total = form.getTotal();
			if(StringUtils.isNotBlank(total) && Integer.parseInt(total) > 0){
				resultList.add(form);
			} else {
				String value = numMap.get(key);

				if(value != null){
					form.setTotal(value);
					resultList.add(form);
				}
			}
		}
    	
        return resultList;
    }

	/**
	 * 保存个人任务并返回id
	 * @param form
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String saveTask(ThemeExerciseForm form, String userId){
		String total = form.getTotal();
		int num = 0;
		if(StringUtils.isNotBlank(total)){
			num = Integer.parseInt(total);
		}

		ThemeExerciseEntity entity
				= new ThemeExerciseEntity(
						form.getThemeId(),
						form.getThemeName(),
						num,
						ThemeExerciseEntity.STATUS_ON);
		entity.preInsert(userId);

		int result = dao.save(entity);
		if(result != 1){
			throw new RuntimeException("保存失败...");
		}

		String id = entity.getId();
		return id;
	}

	/**
	 * 根据题目IDs获取题目信息，
	 * 此处与testQuestionService中的区别在于需要回显用户的答题情况
	 * @param ids
	 * @return
	 */
	@Override
	public List<QuestForm> getQuestions(List<String> ids, String id, String userId){

		return dao.getQuestions(ids, id, userId);
	}

	/**
	 * 题目展示
	 * @param id 个人任务ID
	 * @param userId 当前用户ID
	 * @param index 当前题目序号
	 * @param isReview 是否错题回顾
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> showPaper(String id, String userId,
										 Integer index, String isReview){
		// 定义返回结果的Map
		Map<String, Object> resultMap = new HashMap<String, Object>();

		ThemeExerciseEntity entity = dao.findOne(id);
		int total = entity.getTotal() == null ? 0 : entity.getTotal();

		List<String> ids = new ArrayList<String>();

		if(StringUtils.isBlank(isReview)){
			// 封装查询参数
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("start", index);
			params.put("themeId", entity.getTypeId());
			ids = testQuestionService.selectIdsForPage(params);
		} else {

			ids = dao.selectIdsForPage(userId, id, index);
		}

		if(CollectionUtils.isEmpty(ids)){
			resultMap.put("list", null);
			return resultMap;
		}

		// 获取题目详细信息
		List<QuestForm> resultList = this.getQuestions(ids, id, userId);

		if(CollectionUtils.isEmpty(resultList)){
			resultMap.put("list", null);
			return resultMap;
		}
		resultMap.put("list", resultList);

		List<AnswerForm> answerForms = answerService.findByQuestionIds(ids);

		if(CollectionUtils.isEmpty(answerForms)){
			return resultMap;
		}

		resultList = testQuestionService.handleAnswers(resultList, answerForms);
		resultMap.put("question", resultList.get(0));
		resultMap.put("typeName", entity.getTypeName());

		return resultMap;
	}

	/**
	 * 保存个人主题任务信息
	 * @param entity
	 * @return
	 */
	private Integer mySave (ThemeExerciseEntity entity){

		return dao.save(entity);
	}

	/**
	 * 保存答题记录
	 * @param userId
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void preserve(ThemeAnswerForm form, String userId, String id){
		Date date = new Date();

		// 记录正确答题数量
		int rightNum = 0;

		form.setCreateUser(userId);
		form.setId(IdWorker.getIdStr());
		form.setCreateTime(date);

		if(form.getRight() == 1){
			rightNum++;
		}
		themeAnswerRecordService.saveForm(form);

		dao.updateAnswerNum(id, 1, rightNum, -1);
	}

	/**
	 * 保存 OR 提交
	 * @param form
	 * @return
	 */
	public AnalysisForm commit(ThemeForm form){
		AnalysisForm result = null;
		if(form.getList() != null && CollectionUtils.isNotEmpty(form.getList())){
			// 若有答题记录，先保存答题记录
			// this.preserve(form);
		}

		if(form.getStatus() == ThemeExerciseEntity.STATUS_OFF){
			// 提交练习，获取分析信息
			result = this.analysisAnswer(form.getId());
		}

		return result;
	}

	@Override
	public AnalysisForm analysisAnswer(String themeId){
		AnalysisForm result = new AnalysisForm();

		ThemeExerciseEntity theme = dao.selectById(themeId);
		result.setId(theme.getId());
		result.setRightNum(theme.getRightNum());
		result.setTotal(theme.getTotal());
		result.setTypeName(theme.getTypeName());

		// 获取答题记录的统计信息
		List<ThemeAnswerForm> answerList = themeAnswerRecordService.analysisAnswer(theme.getId());

		result.setList(answerList);

		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String restart(String id){
		// 更新原先任务状态
		ThemeExerciseEntity entityOld = dao.findOne(id);

		// 归档
		dao.updateStatus(id, ThemeExerciseEntity.STATUS_FILE);

		// 获取主题对应的题库数量
		Map<String, String> numMap = testQuestionService.countByThemeId();

		ThemeExerciseForm form = new ThemeExerciseForm();
		form.setTotal(numMap.get(entityOld.getTypeId()));
		form.setStatus(ThemeExerciseEntity.STATUS_ON+"");
		form.setThemeId(entityOld.getTypeId());
		form.setThemeName(entityOld.getTypeName());

		String idNew = this.saveTask(form, entityOld.getCreateUser());
		return idNew;
	}

	@Override
	public AnalysisForm analysis(String month, String userId){
		AnalysisForm form = dao.analysis(month, userId);

		return form;
	}

	/**
	 * 随机练习收藏/取消
	 * @param id
	 * @param recordId
	 * @param type 1收藏，0取消收藏
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean doCollect(String id, String recordId, Integer type){

		User user = (User) SecurityUtils.getSubject().getPrincipal();

		boolean result = collectionService.doCollect(id, CollectionEntity.VITAL_QUESTION, type==1?true:false, user.getId());

		dao.updateCollect(recordId, type);

		return true;
	}
}
