package com.lawschool.service.exam;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.User;
import com.lawschool.beans.exam.CheckExam;
import com.lawschool.form.UserAnswerForm;
import com.lawschool.util.Result;

import java.util.List;

public interface CheckExamService extends AbstractService<CheckExam> {

    List<String> getUserExamIdBuCheckUserId(String checkUserId);

    Result startCheckExam(String userExamId);

    Result continueCheckExam(String userExamId,String checkExamUserId);

    Result commitCheckExam(UserAnswerForm userAnswerForm);

    Result saveCheckExam(UserAnswerForm userAnswerForm);
}
