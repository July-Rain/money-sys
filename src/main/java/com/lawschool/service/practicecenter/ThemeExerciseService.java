package com.lawschool.service.practicecenter;

import java.util.List;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.practicecenter.ThemeExerciseEntity;
import com.lawschool.form.AnalysisForm;
import com.lawschool.form.QuestForm;
import com.lawschool.form.ThemeExerciseForm;
import com.lawschool.form.ThemeForm;

/**
 * 主题练习service
 * @author xuxiang
 * @date 2018/12/4 11:50
 */
public interface ThemeExerciseService extends AbstractService<ThemeExerciseEntity> {

    List<ThemeExerciseForm> indexList(String userId);

    boolean mysave(ThemeExerciseEntity entity);

    String startTheme(String id, Integer status, String typeId, String userId, String typeName);

    boolean updateStatus(String id, Integer status);

    /**
     * 获取题目并保存已答题目
     * @param form
     * @return
     */
    List<QuestForm> saveAndGetQuestions(ThemeForm form);

    List<QuestForm> getQuestions(String id, String userId, List<String> list);

    List<String> preserve(ThemeForm form);

    AnalysisForm commit(ThemeForm form);

    AnalysisForm analysisAnswer(String themeId);
}
