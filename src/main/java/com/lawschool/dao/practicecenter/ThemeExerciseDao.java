package com.lawschool.dao.practicecenter;

import java.util.List;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.practicecenter.ThemeExerciseEntity;
import com.lawschool.form.AnalysisForm;
import com.lawschool.form.QuestForm;
import com.lawschool.form.ThemeExerciseForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author xuxiang
 * @date 2018/12/4 11:45
 */
public interface ThemeExerciseDao extends AbstractDao<ThemeExerciseEntity> {

    Integer save(ThemeExerciseEntity entity);

    /**
     * 获取题目信息关联答题情况
     * @param list 题目Ids
     * @param id 任务ID
     * @param userId 用户ID
     * @return
     */
    List<QuestForm> getQuestions(@Param("list") List<String> list,
                                 @Param("id") String id,
                                 @Param("userId") String userId);

    /**
      * 获取用户的主题练习任务
      * @param userId 用户Id
      * @param status 任务状态（sql中为“不等于”请知悉）
      * @return
    */
    List<ThemeExerciseForm> findAllByUser(@Param("userId") String userId, @Param("status") Integer status);

    void updateAnswerNum(@Param("id")String id,
                         @Param("answerNum")Integer answerNum,
                         @Param("rightNum")Integer rightNum,
                         @Param("type") Integer type);

    Integer updateStatus(@Param("id") String id, @Param("status") Integer status);

    AnalysisForm analysis(@Param("month") String month, @Param("userId") String userId);

}
