package com.lawschool.service.exam;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.exam.CheckExamUser;
import com.lawschool.form.CheckUserExamForm;
import com.lawschool.util.Result;

import java.util.Map;

public interface CheckExamUserService extends AbstractService<CheckExamUser> {

    Result login(CheckExamUser checkUser);

    Result saveCheckExamUser(CheckExamUser checkExamUser);

    Result list(Map<String, Object> params);


}
