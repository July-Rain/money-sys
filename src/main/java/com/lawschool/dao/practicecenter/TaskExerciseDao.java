package com.lawschool.dao.practicecenter;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.practicecenter.TaskExerciseEntity;
import com.lawschool.form.CommonForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther: Moon
 * @Date: 2018/12/21 17:56
 * @Description:
 */
public interface TaskExerciseDao extends AbstractDao<TaskExerciseEntity> {

    List<CommonForm> findByUserAndConIds(@Param("userId") String userId,
                                         @Param("list") List<String> list);

}
