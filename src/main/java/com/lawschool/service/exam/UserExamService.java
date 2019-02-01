package com.lawschool.service.exam;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.User;
import com.lawschool.beans.exam.ExamConfig;
import com.lawschool.beans.exam.UserExam;
import com.lawschool.beans.exam.UserExamAnswer;
import com.lawschool.form.QuestForm;
import com.lawschool.form.ThemeAnswerForm;
import com.lawschool.form.UserAnswerForm;
import com.lawschool.form.UserExamForm;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;

import java.util.List;
import java.util.Map;

/**
 * ClassName: UserExamService
 * Description: TODO
 * date: 2018/12/2910:20
 *
 * @author 王帅奇
 */

public interface UserExamService  extends AbstractService<UserExam> {

    Result getExam(String examConfigId, User user);

    void commitExam(UserAnswerForm userAnswerForm);

    PageUtils getList(Map<String, Object> params,User user);

    void saveExam(UserAnswerForm userAnswerForm);

    Result continueExam(String userExamId,User user);

    List<UserExamForm> getListByIds(List<String> ids);

    List<QuestForm> buildExam(List<UserExamAnswer> list);
}
