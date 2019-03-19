package com.lawschool.service.impl.practicecenter;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.Collection;
import com.lawschool.beans.User;
import com.lawschool.beans.personalCenter.CollectionEntity;
import com.lawschool.beans.practicecenter.ExerciseEntity;
import com.lawschool.dao.practicecenter.ExerciseDao;
import com.lawschool.form.*;
import com.lawschool.service.AnswerService;
import com.lawschool.service.TestQuestionService;
import com.lawschool.service.personalCenter.CollectionService;
import com.lawschool.service.practicecenter.ExerciseAnswerRecordService;
import com.lawschool.service.practicecenter.ExerciseService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
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

    @Autowired private TestQuestionService testQuestionService;

    @Autowired private ExerciseAnswerRecordService exerciseAnswerRecordService;

    @Autowired private AnswerService answerService;

    @Autowired private CollectionService collectionService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String startExercise(RandomExerciseForm form){
        // 生成随机练习的任务信息
        String id = IdWorker.getIdStr();
        form.setId(id);

        dao.saveForm(form);

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

        List<QuestForm> questions = this.getQuestions(form.getId(), form.getUserId(), 0, 0);

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
            // 回答正确数
            int rightNum = 0;

            Date createTime = new Date();
            User user = (User) SecurityUtils.getSubject().getPrincipal();

            for(ThemeAnswerForm af : list){
                removeList.add(af.getqId());

                af.setCreateTime(createTime);
                af.setCreateUser(user.getId());
                af.setTaskId(form.getId());
                af.setId(IdWorker.getIdStr());

                if(af.getRight().intValue() == 1){
                    rightNum ++;
                }
            }

            // 保存答题记录
            exerciseAnswerRecordService.saveBatch(list);

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
     * 随机练习获取题目
     * @param id 主题ID
     * @param userId 用户ID
     * @return
     */
    public List<QuestForm> getQuestions(String id, String userId, Integer limit, Integer page){

        List<String> list = testQuestionService.selectIdsForPage(null);

        return null;
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
    public Map<String, Object> analysis(String month, String userId){
        Map<String, Object> resultMap = new HashMap<String, Object>();

        List<Map<String, String>> mapList = dao.analysis(month, userId);

        Set<String> idSet = new HashSet<String>();// 存放ID,利用set去重
        int total = 0;// 总回答数
        int right = 0;// 回答正确数

        for(Map<String, String> temp : mapList){
            String id = temp.get("id");
            idSet.add(id);

            String themeName = temp.get("themeName");
            Integer answerNum = Integer.parseInt(temp.get("answerNum"));
            Integer rightNum = Integer.parseInt(temp.get("rightNum"));

            total += answerNum;
            right += rightNum;

            List<Integer> list = new ArrayList<Integer>(2);
            if(resultMap.get(themeName) != null){
                list = (List<Integer>) resultMap.get(themeName);
                list.add(0, list.get(0) + answerNum);
                list.add(1, list.get(1) + rightNum);
            } else {
                // 初始化
                list.add(0, answerNum);
                list.add(1, rightNum);
            }

            resultMap.put(themeName, list);
        }

        resultMap.put("total", total);// 回答总数
        resultMap.put("right", right);// 回答正确数
        resultMap.put("num", idSet.size());// 练习次数

        return resultMap;
    }

    /**
     * 获取题目信息，每次只取一条
     * @param id
     * @param index
     * @param isReview
     * @return
     */
    public QuestForm getQuestion(String id, Integer index, String userId, String isReview){

        List<String> ids = new ArrayList<>();

        if(StringUtils.isBlank(isReview)){
            // 查询配置信息
            ExerciseEntity config = this.findOne(id);

            // 封装查询参数
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("start", index);
            params.put("themeId", config.getTopicType());
            params.put("difficulty", config.getDifficulty());
            params.put("type", config.getType());
            params.put("classify", config.getClassify());

            ids = testQuestionService.selectIdsForPage(params);
        } else {

            ids = dao.selectIdsForPage(userId, id, index);
        }

        if(CollectionUtils.isEmpty(ids)){
            return null;
        }

        List<QuestForm> resultList = dao.getQuestions(ids, id, userId, isReview);
        if(CollectionUtils.isEmpty(resultList)){
            return null;
        }
        List<AnswerForm> answerForms = answerService.findByQuestionIds(ids);

        resultList = testQuestionService.handleAnswers(resultList, answerForms);

        return resultList.get(0);
    }

    public boolean updateNum(Integer answerNum, Integer rightNum, String id){

        return dao.updateNum(answerNum, rightNum, id);
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

        User user = (User)SecurityUtils.getSubject().getPrincipal();

        if(type == 1){
            Collection collect = new Collection();
            collect.setId(IdWorker.getIdStr());
            collect.setType(20);
            collect.setComStucode(id);

            // 0成功，1失败
             boolean result = collectionService.doCollect(id, CollectionEntity.VITAL_QUESTION, true, "");
        } else {

            // 取消收藏
            boolean result = collectionService.doCollect(id, CollectionEntity.VITAL_QUESTION, false, "");
        }

        dao.updateCollect(recordId, type);

        return true;
    }
}
