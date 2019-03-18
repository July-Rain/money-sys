package com.lawschool.dao.practicecenter;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.practicecenter.PaperExerciseEntity;
import com.lawschool.form.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther: Moon
 * @Date: 2018/12/21 17:56
 * @Description: 组卷练习Dao
 */
public interface PaperExerciseDao extends AbstractDao<PaperExerciseEntity> {

    /**
     * 更新练习任务的整体答题情况
     * @param id
     * @param num
     * @return
     */
    boolean updateAnswerNum(@Param("id") String id,
                            @Param("num") Integer num);

    boolean updateStatus(@Param("id") String id, @Param("status") Integer status);

    AnalysisForm analysis(@Param("month") String month, @Param("userId") String userId);

    /**
     * 获取组卷题目信息，关联答题记录
     * @param configureId
     * @param userId
     * @return
     */
    List<QuestionForm> showQuestions(@Param("configureId") String configureId,
                                           @Param("userId") String userId);

    List<AnswerForm> getAnswers(@Param("configureId") String configureId);

    /**
     * 更新练习记录
     * @param id
     * @param answerNum
     * @param status
     * @return
     */
    boolean updateRecord(@Param("id") String id,
                         @Param("answerNum") Integer answerNum,
                         @Param("status") Integer status);
}
