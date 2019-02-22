package com.lawschool.dao.exam;

import com.lawschool.base.AbstractDao;
import com.lawschool.form.CheckUserExamForm;

import java.util.List;

public interface CheckUserExamFormDao extends AbstractDao<CheckUserExamForm> {

    List<CheckUserExamForm> getAuditList(List<String> list);
}
