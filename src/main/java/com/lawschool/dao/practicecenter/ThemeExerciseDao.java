package com.lawschool.dao.practicecenter;

import java.util.List;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.practicecenter.ThemeExerciseEntity;
import com.lawschool.form.ThemeExerciseForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author xuxiang
 * @date 2018/12/4 11:45
 */
public interface ThemeExerciseDao extends AbstractDao<ThemeExerciseEntity> {

    List<ThemeExerciseForm> findAllByUser(ThemeExerciseEntity entity);
    
    Integer save(ThemeExerciseEntity entity);

    boolean updateStatus(@Param("id") String id, @Param("status") Integer status);

    /**
     * 根据答题情况更新主题任务
     * @param entity
     */
    void updateAnswerRecord(ThemeExerciseEntity entity);
}
