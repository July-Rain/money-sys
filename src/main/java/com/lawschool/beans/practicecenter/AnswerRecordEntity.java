package com.lawschool.beans.practicecenter;

import com.lawschool.base.DataEntity;

/**
 * 练习任务的答题记录的Base类
 * @param <T>
 */
public class AnswerRecordEntity<T> extends DataEntity<T> {

    private static final long serialVersionUID = -3836468037726033723L;

    protected String taskId;// 练习ID
    protected String questionId;// 题库ID
    protected String answer;// 用户答案
    protected Integer right;// 是否正确 0错误，1正确
    protected String themeName;// 主题类型

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getRight() {
        return right;
    }

    public void setRight(Integer right) {
        this.right = right;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }
}
