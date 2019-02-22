package com.lawschool.service.exam;

import com.lawschool.base.AbstractService;
import com.lawschool.form.CheckUserExamForm;

import java.util.List;

public interface CheckUserExamFormService  extends AbstractService<CheckUserExamForm> {

    List<CheckUserExamForm> getAuditList(List<String> list);
}
