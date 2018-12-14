package com.lawschool.service.impl.practicecenter;

import java.util.*;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.practicecenter.ThemeAnswerRecordEntity;
import com.lawschool.form.*;
import com.lawschool.service.AnswerService;
import com.lawschool.service.TestQuestionService;
import com.lawschool.service.practicecenter.ThemeAnswerRecordService;
import com.lawschool.service.system.TopicTypeService;
import com.lawschool.util.RedisUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

	@Autowired
    private RedisUtil redisUtil;

	@Autowired
	private ThemeAnswerRecordService themeAnswerRecordService;

	@Autowired
	private TestQuestionService testQuestionService;

	@Autowired
	private TopicTypeService topicTypeService;

	@Autowired
	private AnswerService answerService;

	/**
	 * 主题练习首页列表数据
	 * @param userId
	 * @return
	 */
    @Override
    public List<ThemeExerciseForm> indexList(String userId){

        // 获取当前用户进行中的练习
    	ThemeExerciseEntity entity = new ThemeExerciseEntity();
    	entity.setCreateUser(userId);
    	// 已结束
    	entity.setStatus(3);
    	// 用户已进行的主题练习
    	List<ThemeExerciseForm> list = dao.findAllByUser(entity);
    	
    	// 用于存放已有练习的主题信息
    	List<String> hasList = new ArrayList<>(list.size());
    	for(ThemeExerciseForm form : list) {
    		hasList.add(form.getTypeId());
    	}

    	// 获取所有主题信息(除进行中的主题)
		List<CommonForm> formList = topicTypeService.findAll(hasList);

    	// 定义返回结果集，并初始化大小
    	List<ThemeExerciseForm> resultList = new ArrayList<>(formList.size());
    	
    	if(CollectionUtils.isNotEmpty(list)) {
    		resultList.addAll(list);
    	}
    	
    	if(CollectionUtils.isEmpty(formList)) {
    		// 所有主题都已进行 或 数据问题(无主题类型信息)
    		return resultList;
    	}

    	// 对尚未开始的练习进行初始化，便于页面展示

    	for(CommonForm cf : formList) {
			// 专项知识IDS
			List<String> problems = this.getTotalQuestions(cf.getKey());

    		ThemeExerciseForm form = new ThemeExerciseForm();
    		form.setId("");
    		form.setStatus("0");
    		form.setTotal(problems.size() + "");
    		form.setAnswerNum("0");
    		form.setTypeId(cf.getKey());
    		form.setTypeName(cf.getValue());
    		
    		resultList.add(form);
    	}
    	
        return resultList;
    }
    
    /**
     * 新建主题练习
     */
    @Override
    public boolean mysave(ThemeExerciseEntity entity) {
		dao.save(entity);
    	
    	return false;
    }

	/**
	 * 开始练习、重新练习
	 * @param id 主题练习任务ID
	 * @param status 状态
	 * @param typeId 主题类型ID
	 * @param userId 用户ID
	 * @return
	 */
	@Transactional
	@Override
	public String startTheme(String id, Integer status, String typeId, String userId, String typeName){

		String result = "";
		ThemeExerciseEntity entity = new ThemeExerciseEntity();
		entity.setStatus(status);
		entity.setTypeId(typeId);
		entity.setCreateUser(userId);
		entity.setCreateTime(new Date());
		entity.setOptUser(userId);
		entity.setOptTime(new Date());
		entity.setAnswerNum(0);
		entity.setRightNum(0);
		entity.setTypeName(typeName);

		entity.setStatus(ThemeExerciseEntity.STATUS_ON);

		List<String> questionList = this.getTotalQuestions(typeId);

		entity.setTotal(questionList.size());

		if(StringUtils.isNotBlank(id)){
			// 归档原先主题任务，并创建新的主题任务
			this.updateStatus(id, ThemeExerciseEntity.STATUS_FILE);
			// 并且移除redis中的记录信息
			String key = "theme" + userId + id;
			redisUtil.delete(key);
		}
		this.save(entity);
		result = entity.getId();
		String key = "theme" + userId + result;
		redisUtil.set(key, questionList, -1);

    	return result;
	}

	/**
	 * 更新主题任务状态
	 * @param id
	 * @param status
	 * @return
	 */
	public boolean updateStatus(String id, Integer status){
		boolean result = dao.updateStatus(id, status);

		return result;
	}

	/**
	 * 主题练习获取题目
	 * @param id 主题ID
	 * @param userId 用户ID
	 * @return
	 */
	public List<QuestForm> getQuestions(String id, String userId, List<String> removeList){

		// 先从redis中取剩余的题目ID，然后去取题目具体信息
		String key = "theme" + userId + id;
		List<String> ids = redisUtil.get(key, List.class, -1);

		if(CollectionUtils.isNotEmpty(ids) && CollectionUtils.isNotEmpty(removeList)){
			ids.removeAll(removeList);
		}

		List<String> questList = new ArrayList<>();

		// 根据系统配置题目数量取题目，如果没有配置，默认取50
		if(CollectionUtils.isNotEmpty(ids)){
			String qid = ids.get(0);
			questList.add(qid);
		}

		List<QuestForm> result = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(removeList)){
			redisUtil.set(key, ids, -1);
		}

		if(CollectionUtils.isNotEmpty(questList)){

			result	= testQuestionService.findByIds(questList);

			List<AnswerForm> answerForms = answerService.findByQuestionIds(questList);
			// 遍历处理选项信息
			for(QuestForm qf : result){
				String qid = qf.getId();
				List<AnswerForm> tempList = new ArrayList<>();

				for(AnswerForm af : answerForms){
					String aqid = af.getQuestionId();
					if(qid.equals(aqid)){
						tempList.add(af);
					}
				}

				qf.setAnswer(tempList);

				answerForms.removeAll(tempList);
			}
		}

		return result;
	}

	/**
	 * 主题练习保存
	 * @param form
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<String> preserve(ThemeForm form){
		List<String> removeList = new ArrayList<>();
		// 答题记录
		List<ThemeAnswerForm> list = form.getList();
		String themeId = form.getId();

		ThemeExerciseEntity theme = null;

		if(CollectionUtils.isNotEmpty(list) && StringUtils.isNotBlank(themeId)){

			// 若无答题记录或主题ID为空，不做任何操作
			int total = list.size();// 本次总答题数
			List<ThemeAnswerRecordEntity> saveList = new ArrayList<>(total);
			// 回答正确数
			int rightNum = 0;

			for(ThemeAnswerForm af : list){
				removeList.add(af.getqId());

				ThemeAnswerRecordEntity entity = new ThemeAnswerRecordEntity();
				entity.setId(IdWorker.getIdStr());
				entity.setAnswer(af.getAnswer());
				entity.setQuestionId(af.getqId());
				entity.setRight(af.getRight());
				entity.setThemeId(themeId);
				entity.setTypeName(af.getTypeName());
				entity.setAnswerTime(new Date());
				saveList.add(entity);

				if(af.getRight().intValue() == 1){
					rightNum ++;
				}
			}

			// 保存答题记录
			themeAnswerRecordService.saveBatch(saveList);

			// 更新主题任务
			theme = dao.selectById(themeId);
			Integer right = theme.getRightNum();
			theme.setRightNum(right + rightNum);
			theme.setAnswerNum(total + theme.getAnswerNum());
			theme.setStatus(form.getStatus());
			theme.setOptTime(new Date());

		} else if(StringUtils.isNotBlank(themeId)){
			// 特殊情况，未答题直接提交，更新主题任务
			theme = dao.selectById(themeId);
			theme.setStatus(form.getStatus());
		}

		if(theme != null){
			dao.updateAnswerRecord(theme);
		}

		return removeList;
	}

	/**
	 * 提交主题任务，并返回统计分析信息
	 * @param form
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public AnalysisForm commit(ThemeForm form){

		// 先判断是否需要保存答题信息
		this.preserve(form);

		// 获取主题任务的分析统计数据
		AnalysisForm result = this.analysisAnswer(form.getId());

		return result;
	}

	/**
	 * 获取主题任务的分析统计信息
	 * @param themeId
	 * @return
	 */
	@Override
	public AnalysisForm analysisAnswer(String themeId){
		AnalysisForm result = new AnalysisForm();

		ThemeExerciseEntity theme = dao.selectById(themeId);
		result.setId(theme.getId());
		result.setRightNum(theme.getRightNum());
		result.setTotal(theme.getTotal());

		// 获取答题记录的统计信息
		List<ThemeAnswerForm> answerList = themeAnswerRecordService.analysisAnswer(theme.getId());

		result.setList(answerList);

		return result;
	}

	/**
	 *
	 * @param form
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<QuestForm> saveAndGetQuestions(ThemeForm form){

		List<String> removeList = new ArrayList<>();

		if(CollectionUtils.isNotEmpty(form.getList())){
			// 保存答题信息
			removeList = this.preserve(form);
		}

		List<QuestForm> questions = this.getQuestions(form.getId(), form.getUserId(), removeList);

		return questions;
	}

	/**
	 * 获取符合条件的题目IDs
	 * @param konwId
	 * @return
	 */
	private List<String> getTotalQuestions(String konwId){
		List<String> resultList = new ArrayList<>();

		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("isEnble", "0");// 启用

		// 题目类型
		List<String> questionType = new ArrayList<>();
		questionType.add("4");
		questionType.add("5");
		questionType.add("6");

		paramsMap.put("list", questionType);
		paramsMap.put("specialKnowledgeId", konwId);

		resultList = testQuestionService.findIdBySpecialKnowledgeId(paramsMap);

		return resultList;
	}
}
