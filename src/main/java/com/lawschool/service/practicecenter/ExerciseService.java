package com.lawschool.service.practicecenter;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.TestQuestions;
import com.lawschool.beans.practicecenter.ExerciseEntity;
import com.lawschool.form.AnalysisForm;
import com.lawschool.form.QuestForm;
import com.lawschool.form.RandomExerciseForm;
import com.lawschool.form.ThemeForm;

import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @Description: 练习任务Dao 接口
 * @author: 中石电子科技 徐祥
 * @date: 2018-12-07 15:32
 */
public interface ExerciseService extends AbstractService<ExerciseEntity> {

    /**
     * 生成随机任务，并返回生成的主键
     * @param form
     * @return
     */
    String startExercise(RandomExerciseForm form);

    List<QuestForm> saveAndGetQuestions(ThemeForm form);

    List<String> preserve(ThemeForm form);

    AnalysisForm commit(ThemeForm form);

    AnalysisForm analysisAnswer(String themeId);

    Map<String, Object> analysis(String month, String userId);

    QuestForm getQuestion(String id, Integer index, String userId, String isReview);

    boolean updateNum(Integer answerNum, Integer rightNum, String id);

    /**
     * 更新收藏状态
     * @param id
     * @param recordId
     * @return
     */
    boolean doCollect(String id, String recordId, Integer type);
}
