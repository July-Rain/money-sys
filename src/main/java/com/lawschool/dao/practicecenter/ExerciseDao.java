package com.lawschool.dao.practicecenter;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.practicecenter.ExerciseEntity;
import com.lawschool.form.AnalysisForm;
import com.lawschool.form.QuestForm;
import com.lawschool.form.RandomExerciseForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @Description: 练习任务Dao
 * @author: 中石电子科技 徐祥
 * @date: 2018-12-07 15:29
 */
public interface ExerciseDao extends AbstractDao<ExerciseEntity> {

    boolean saveForm(RandomExerciseForm form);

    /**
     * 根据答题情况更新主题任务
     * @param entity
     */
    void updateAnswerRecord(ExerciseEntity entity);

    List<Map<String, String>> analysis(@Param("month") String month, @Param("userId") String userId);

    List<QuestForm> getQuestions(@Param("list") List<String> ids,
                                 @Param("id") String id,
                                 @Param("userId") String userId,
                                 @Param("isReview") String isReview);

    boolean updateNum(@Param("answerNum") Integer answerNum,
                      @Param("rightNum") Integer rightNum,
                      @Param("id") String id);

    boolean updateCollect(@Param("id") String id);

}
