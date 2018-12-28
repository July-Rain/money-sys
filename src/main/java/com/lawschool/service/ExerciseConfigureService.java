package com.lawschool.service;

import com.lawschool.base.AbstractService;
import com.lawschool.base.Page;
import com.lawschool.beans.ExerciseConfigureEntity;
import com.lawschool.form.QuestForm;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Moon
 * @Date: 2018/12/20 15:19
 * @Description: 练习配置Service
 */
public interface ExerciseConfigureService extends AbstractService<ExerciseConfigureEntity> {

    void saveConfigure(ExerciseConfigureEntity entity);

    List<QuestForm> showPaper(String id);

    Page<ExerciseConfigureEntity> findPageByUser(Page<ExerciseConfigureEntity> page,
                                           ExerciseConfigureEntity entity);

    Integer updateDelflag(String id);

    Map<String, String> getQuestions(String id);

    String findQuestionsById(String id);
}
