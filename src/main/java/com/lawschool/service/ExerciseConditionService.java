package com.lawschool.service;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.ExerciseConditionEntity;

import java.util.List;

/**
 * @Auther: Moon
 * @Date: 2018/12/20 15:42
 * @Description:
 */
public interface ExerciseConditionService extends AbstractService<ExerciseConditionEntity> {

    /**
     * 批量保存练习配置条件信息
     * @param list
     * @param conId
     */
    void saveBatch(List<ExerciseConditionEntity> list, String conId);
}
