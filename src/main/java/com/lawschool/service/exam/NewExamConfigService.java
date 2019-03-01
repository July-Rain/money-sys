package com.lawschool.service.exam;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.User;
import com.lawschool.beans.exam.ExamConfig;
import com.lawschool.form.ExamConfigForm;
import com.lawschool.util.Result;

public interface NewExamConfigService extends AbstractService<ExamConfig> {

    Result saveOrUpdate(ExamConfig examConfig, User user);

    Result genAutoQue(ExamConfigForm examConfigForm);

    Result genRandomQue(ExamConfigForm examConfigForm);

    Result preview(ExamConfigForm examConfigForm);

    Result genRanQueAfterPreview(ExamConfigForm examConfigForm);
}
