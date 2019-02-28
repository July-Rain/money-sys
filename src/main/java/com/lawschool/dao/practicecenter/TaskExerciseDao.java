package com.lawschool.dao.practicecenter;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.practicecenter.TaskExerciseEntity;
import com.lawschool.form.AnalysisForm;
import com.lawschool.form.QuestForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther: Moon
 * @Date: 2019/1/2 16:49
 * @Description: 个人联系任务Dao
 */
public interface TaskExerciseDao extends AbstractDao<TaskExerciseEntity> {

    List<QuestForm> getQuestions(@Param("list") List<String> list,
                                 @Param("taskId") String taskId,
                                 @Param("userId") String userId);

    /**
     * 更新练习任务的整体答题情况
     * @param id
     * @param num
     * @return
     */
    boolean updateAnswerNum(@Param("id") String id,
                            @Param("num") Integer num,
                            @Param("rightNum") Integer rightNum);

    boolean updateStatus(@Param("id") String id,
                         @Param("status") Integer status);

    AnalysisForm analysis(@Param("month") String month, @Param("userId") String userId);

    /**
     * 获取题目信息，关联答题记录
     * @param ids
     * @param id
     * @param userId
     * @param isReview
     * @return
     */
    List<QuestForm> getQuestions(@Param("list") List<String> ids,
                                 @Param("id") String id,
                                 @Param("userId") String userId,
                                 @Param("isReview") String isReview);

    boolean updateCollect(@Param("id") String id, @Param("type") Integer type);

    List<String> selectIdsForPage(@Param("userId") String userId,
                                  @Param("taskId") String taskId,
                                  @Param("index") Integer index);
}
