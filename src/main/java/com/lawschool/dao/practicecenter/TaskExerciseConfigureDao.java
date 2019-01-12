package com.lawschool.dao.practicecenter;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.practicecenter.TaskExerciseConfigureEntity;
import org.apache.ibatis.annotations.Param;

/**
 * @Auther: Moon
 * @Date: 2018/12/29 13:57
 * @Description: 练习中心--练习任务配置Dao
 */
public interface TaskExerciseConfigureDao extends AbstractDao<TaskExerciseConfigureEntity> {

    boolean logicDelete(@Param("id") String id, @Param("delFlag") Integer delFlag);
}
