package com.lawschool.service.practicecenter;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.practicecenter.ThemeAnswerRecordEntity;
import com.lawschool.form.ThemeAnswerForm;

import java.util.List;

/**
 * @Auther: Moon
 * @Date: 2018/12/21 17:58
 * @Description: 主题练习--答题记录--Service
 */
public interface ThemeAnswerRecordService extends AnswerRecordService<ThemeAnswerRecordEntity> {

    List<ThemeAnswerForm> analysisAnswer(String themeId);
}
