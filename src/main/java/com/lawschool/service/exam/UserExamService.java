package com.lawschool.service.exam;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.exam.ExamConfig;
import com.lawschool.beans.exam.UserExam;
import com.lawschool.form.QuestForm;
import com.lawschool.util.Result;

import java.util.List;

/**
 * ClassName: UserExamService
 * Description: TODO
 * date: 2018/12/2910:20
 *
 * @author 王帅奇
 */
public interface UserExamService  extends AbstractService<UserExam> {

    public Result getExam(String examConfigId);
}
