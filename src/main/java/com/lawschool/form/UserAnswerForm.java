package com.lawschool.form;

import com.lawschool.beans.exam.ExamConfig;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName: UserAnswerForm
 * Description: TODO
 * date: 2018/12/1716:52
 *
 * @author 王帅奇
 */
public class  UserAnswerForm implements Serializable {

    //用戶考试试卷ID
    private String userExamId;

    //考试答案
    private List<ThemeAnswerForm> answerForm;

    //阅卷分数信息
    private List<CheckExamForm> checkExamForm;

    //考试剩余时间
    private Double remainingExamTime;

    //阅卷人ID
    private String checkExamUserId;

    private String checkExamId;

    public String getCheckExamId() {
        return this.checkExamId;
    }

    public void setCheckExamId(final String checkExamId) {
        this.checkExamId = checkExamId;
    }

    public String getCheckExamUserId() {
        return this.checkExamUserId;
    }

    public void setCheckExamUserId(final String checkExamUserId) {
        this.checkExamUserId = checkExamUserId;
    }

    public List<CheckExamForm> getCheckExamForm() {
        return this.checkExamForm;
    }

    public void setCheckExamForm(final List<CheckExamForm> checkExamForm) {
        this.checkExamForm = checkExamForm;
    }

    public String getUserExamId() {
        return this.userExamId;
    }

    public void setUserExamId(final String userExamId) {
        this.userExamId = userExamId;
    }

    public List<ThemeAnswerForm> getAnswerForm() {
        return this.answerForm;
    }

    public void setAnswerForm(final List<ThemeAnswerForm> answerForm) {
        this.answerForm = answerForm;
    }

    public void setRemainingExamTime(final Double remainingExamTime) {
        this.remainingExamTime = remainingExamTime;
    }

    public Double getRemainingExamTime() {
        return this.remainingExamTime;
    }
}
