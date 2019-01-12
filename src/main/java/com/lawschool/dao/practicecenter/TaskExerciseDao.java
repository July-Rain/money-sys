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
                            @Param("num") Integer num);

    boolean updateStatus(@Param("id") String id, @Param("status") Integer status);

    AnalysisForm analysis(@Param("month") String month, @Param("userId") String userId);
}
