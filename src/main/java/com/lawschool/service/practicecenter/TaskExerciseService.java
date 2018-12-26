package com.lawschool.service.practicecenter;

import com.lawschool.base.AbstractService;
import com.lawschool.base.Page;
import com.lawschool.beans.ExerciseConfigureEntity;
import com.lawschool.beans.practicecenter.TaskExerciseEntity;
import com.lawschool.form.CommonForm;

import java.util.List;

/**
 * @Auther: Moon
 * @Date: 2018/12/21 17:58
 * @Description:
 */
public interface TaskExerciseService extends AbstractService<TaskExerciseEntity> {

    /**
     * 查询练习任务，根据用户和练习任务list
     * @param userId
     * @param list
     * @return
     */
    List<CommonForm> findByUserAndConIds(String userId, List<String> list);

}
