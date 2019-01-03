package com.lawschool.dao;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.ExerciseConfigureEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Moon
 * @Date: 2018/12/20 15:18
 * @Description: 练习设置Dao
 */
public interface ExerciseConfigureDao extends AbstractDao<ExerciseConfigureEntity> {

    boolean updateQuestions(@Param("id") String id,
                            @Param("questions") String questions,
                            @Param("total") Integer total);

    String findQuestionsById(@Param("id") String id);

    List<ExerciseConfigureEntity> findListByUser(ExerciseConfigureEntity entity);

    Integer updateDelflag(@Param("id") String id);

    Map<String, String> selectQuestions(@Param("id") String id);
}
