package com.lawschool.service.impl.practicecenter;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.TestQuestions;
import com.lawschool.beans.practicecenter.ExerciseAnswerRecordEntity;
import com.lawschool.beans.practicecenter.ExerciseEntity;
import com.lawschool.beans.practicecenter.ThemeExerciseEntity;
import com.lawschool.dao.practicecenter.ExerciseDao;
import com.lawschool.form.*;
import com.lawschool.service.AnswerService;
import com.lawschool.service.TestQuestionService;
import com.lawschool.service.practicecenter.ExerciseAnswerRecordService;
import com.lawschool.service.practicecenter.ExerciseService;
import com.lawschool.util.RedisUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @version V1.0
 * @Description: 练习任务Service实现类
 * @author: 中石电子科技 徐祥
 * @date: 2018-12-07 15:33
 */
@Service
public class ExerciseServiceImpl extends AbstractServiceImpl<ExerciseDao, ExerciseEntity> implements ExerciseService {

    @Autowired
    private TestQuestionService testQuestionService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ExerciseAnswerRecordService exerciseAnswerRecordService;

    @Autowired
    private AnswerService answerService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String startExercise(RandomExerciseForm form){
        // 生成随机练习的任务信息
        String id = IdWorker.getIdStr();
        form.setId(id);

        dao.saveForm(form);

        // 根据筛选条件，获取符合的试题的idList，放到缓存中去
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("specialKnowledgeId", form.getTopic());
        paramsMap.put("questionDifficulty", form.getDifficulty());
        paramsMap.put("questionType", form.getType());
        paramsMap.put("typeId", form.getType());
        List<String> idList = testQuestionService.findIdBySpecialKnowledgeId(paramsMap);

        String key = "random" + id;
        redisUtil.set(key, idList, -1);

        return id;
    }

    /**
     * 保存并返回题目
     * @param form
     * @return
     */
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

        ExerciseEntity exercise = null;

        if(CollectionUtils.isNotEmpty(list) && StringUtils.isNotBlank(themeId)){

            // 若无答题记录或主题ID为空，不做任何操作
            int total = list.size();// 本次总答题数
            List<ExerciseAnswerRecordEntity> saveList = new ArrayList<>(total);
            // 回答正确数
            int rightNum = 0;

            for(ThemeAnswerForm af : list){
                removeList.add(af.getqId());

                ExerciseAnswerRecordEntity entity = new ExerciseAnswerRecordEntity();
                entity.setId(IdWorker.getIdStr());
                entity.setAnswer(af.getAnswer());
                entity.setQuestionId(af.getqId());
                entity.setRight(af.getRight());
                entity.setExerciseId(themeId);
                entity.setAnswerTime(new Date());
                entity.setTypeName(af.getTypeName());
                entity.setCreateUser(form.getUserId());
                saveList.add(entity);

                if(af.getRight().intValue() == 1){
                    rightNum ++;
                }
            }

            // 保存答题记录
            exerciseAnswerRecordService.saveBatch(saveList);

            // 更新随机练习任务
            exercise = dao.selectById(themeId);
            exercise.setAnswerNum(total + exercise.getAnswerNum());
            Integer right = exercise.getRightNum();
            exercise.setRightNum(right + rightNum);
            exercise.setOptTime(new Date());

        }

        if(exercise != null){
            dao.updateAnswerRecord(exercise);
        }

        return removeList;
    }

    /**
     * 主题练习获取题目
     * @param id 主题ID
     * @param userId 用户ID
     * @return
     */
    public List<QuestForm> getQuestions(String id, String userId, List<String> removeList){

        // 先从redis中取剩余的题目ID，然后去取题目具体信息
        String key = "random" + id;
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
            result	= testQuestionService.getQuestions(questList);
        }

        return result;
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
     * @param exerciseId
     * @return
     */
    @Override
    public AnalysisForm analysisAnswer(String exerciseId){
        AnalysisForm result = new AnalysisForm();

        ExerciseEntity exercise = dao.selectById(exerciseId);
        result.setId(exercise.getId());
        result.setRightNum(exercise.getRightNum());

        // 获取答题记录的统计信息
        List<ThemeAnswerForm> answerList = exerciseAnswerRecordService.analysisAnswer(exercise.getId());

        result.setList(answerList);

        return result;
    }

    /**
     * 获取本月练习答题情况
     * @return
     */
    @Override
    public AnalysisForm analysis(String month, String userId){
        AnalysisForm form = dao.analysis(month, userId);

        return form;
    }

}
