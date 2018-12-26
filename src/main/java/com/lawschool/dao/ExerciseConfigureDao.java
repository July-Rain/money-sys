package com.lawschool.dao;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.ExerciseConfigureEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther: Moon
 * @Date: 2018/12/20 15:18
 * @Description: 练习设置Dao
 */
public interface ExerciseConfigureDao extends AbstractDao<ExerciseConfigureEntity> {

    boolean updateQuestions(@Param("id") String id, @Param("questions") String questions);

    String findQuestionsById(@Param("id") String id);

    List<ExerciseConfigureEntity> findListByUser(ExerciseConfigureEntity entity);

    Integer updateDelflag(@Param("id") String id);
}
