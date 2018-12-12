package com.lawschool.service.practicecenter;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.TestQuestions;
import com.lawschool.beans.practicecenter.ExerciseEntity;
import com.lawschool.form.RandomExerciseForm;

/**
 * @version V1.0
 * @Description: 练习任务Dao 接口
 * @author: 中石电子科技 徐祥
 * @date: 2018-12-07 15:32
 */
public interface ExerciseService extends AbstractService<ExerciseEntity> {

    TestQuestions startExercise(RandomExerciseForm form);

    TestQuestions getQuestion(String id, String userId);

}
