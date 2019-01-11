package com.lawschool.service.practicecenter;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.practicecenter.TaskExerciseConfigureEntity;

/**
 * @Auther: Moon
 * @Date: 2018/12/29 14:00
 * @Description: 练习中心--练习任务配置Service
 */
public interface TaskExerciseConfigureService extends AbstractService<TaskExerciseConfigureEntity> {

    void mySave(TaskExerciseConfigureEntity entity);

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    boolean logicDelete(String id);
}
