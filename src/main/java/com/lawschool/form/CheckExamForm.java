package com.lawschool.form;

/**
 * ClassName: CheckExamForm
 * Description: TODO
 * date: 2019/1/3014:13
 *
 * @author 王帅奇
 */
public class CheckExamForm {

    private String userExamAnsId;   //答题编号ID
    private String queId;// 答案 ID
    private double score;// 题目ID

    public String getUserExamAnsId() {
        return this.userExamAnsId;
    }

    public void setUserExamAnsId(final String userExamAnsId) {
        this.userExamAnsId = userExamAnsId;
    }

    public String getQueId() {
        return this.queId;
    }

    public void setQueId(final String queId) {
        this.queId = queId;
    }

    public double getScore() {
        return this.score;
    }

    public void setScore(final double score) {
        this.score = score;
    }
}
