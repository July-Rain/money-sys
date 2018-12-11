package com.lawschool.service.practicecenter;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.practicecenter.ThemeAnswerRecordEntity;
import com.lawschool.form.ThemeAnswerForm;

import java.util.List;

public interface ThemeAnswerRecordService extends AbstractService<ThemeAnswerRecordEntity> {

    void saveBatch(List<ThemeAnswerRecordEntity> list);

    List<ThemeAnswerForm> analysisAnswer(String themeId);
}
