package com.lawschool.dao.practicecenter;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.practicecenter.ExerciseEntity;
import com.lawschool.form.RandomExerciseForm;

/**
 * @version V1.0
 * @Description: 练习任务Dao
 * @author: 中石电子科技 徐祥
 * @date: 2018-12-07 15:29
 */
public interface ExerciseDao extends AbstractDao<ExerciseEntity> {

    boolean saveForm(RandomExerciseForm form);

    /**
     * 根据答题情况更新主题任务
     * @param entity
     */
    void updateAnswerRecord(ExerciseEntity entity);
}
