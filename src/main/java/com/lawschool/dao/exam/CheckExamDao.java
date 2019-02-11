package com.lawschool.dao.exam;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.exam.CheckExam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckExamDao extends AbstractDao<CheckExam> {

    List<String> getUserExamIdByCheckUserId(@Param("checkUserId") String checkUserId);
}
