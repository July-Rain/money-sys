package com.lawschool.service.practicecenter;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.practicecenter.ExerciseAnswerRecordEntity;
import com.lawschool.form.ThemeAnswerForm;

import java.util.List;

/**
 * @version V1.0
 * @Description: 随机练习Service
 * @author: 中石电子科技 徐祥
 * @date: 2018-12-14 09:33
 */
public interface ExerciseAnswerRecordService extends AnswerRecordService<ExerciseAnswerRecordEntity> {

    List<ThemeAnswerForm> analysisAnswer(String randomId);
}
