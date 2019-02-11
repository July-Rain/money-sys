package com.lawschool.beans.exam;

import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;

/**
 * ClassName: UserExamAnswer
 * Description:用户考试答题表
 * date: 2019/1/1011:58
 *
 * @author 王帅奇
 */
@TableName("LAW_USER_EXAM_ANSWER")
public class UserExamAnswer extends DataEntity<UserExamAnswer> {

    private String userExamId;//已考试卷ID

    private String examDetailId;//考试详情ID

    private String testQueId;//试题ID

    private String rightAnsId;//正确答案ID

    private String userAnsId;//用户ID

    private String testQueType;//题目类型

    private String answerIdOrder; //答案ID顺序拼接

    private int orderNum;//排序

    private double score; //题目分数

    private double userScore;  //考生获得的分数

    private double firCheckScore; //第一人阅卷分数

    private double  secCheckScore;  //第二人阅卷分数

    private double  audCheckScore;  //审核人阅卷分数

    private String firCheckUserId;  //第一阅卷人ID

    private String secCheckUserId;  //第二阅卷人ID

    private String audCheckUserId;  //审核人ID

    public String getFirCheckUserId() {
        return this.firCheckUserId;
    }

    public void setFirCheckUserId(final String firCheckUserId) {
        this.firCheckUserId = firCheckUserId;
    }

    public String getSecCheckUserId() {
        return this.secCheckUserId;
    }

    public void setSecCheckUserId(final String secCheckUserId) {
        this.secCheckUserId = secCheckUserId;
    }

    public String getAudCheckUserId() {
        return this.audCheckUserId;
    }

    public void setAudCheckUserId(final String audCheckUserId) {
        this.audCheckUserId = audCheckUserId;
    }

    public String getUserExamId() {
        return this.userExamId;
    }

    public void setUserExamId(final String userExamId) {
        this.userExamId = userExamId;
    }

    public String getExamDetailId() {
        return this.examDetailId;
    }

    public void setExamDetailId(final String examDetailId) {
        this.examDetailId = examDetailId;
    }

    public String getTestQueId() {
        return this.testQueId;
    }

    public void setTestQueId(final String testQueId) {
        this.testQueId = testQueId;
    }

    public String getRightAnsId() {
        return this.rightAnsId;
    }

    public void setRightAnsId(final String rightAnsId) {
        this.rightAnsId = rightAnsId;
    }

    public String getUserAnsId() {
        return this.userAnsId;
    }

    public void setUserAnsId(final String userAnsId) {
        this.userAnsId = userAnsId;
    }

    public String getTestQueType() {
        return this.testQueType;
    }

    public void setTestQueType(final String testQueType) {
        this.testQueType = testQueType;
    }

    public int getOrderNum() {
        return this.orderNum;
    }

    public void setOrderNum(final int orderNum) {
        this.orderNum = orderNum;
    }

    public String getAnswerIdOrder() {
        return this.answerIdOrder;
    }

    public void setAnswerIdOrder(final String answerIdOrder) {
        this.answerIdOrder = answerIdOrder;
    }

    public double getScore() {
        return this.score;
    }

    public void setScore(final double score) {
        this.score = score;
    }

    public double getUserScore() {
        return this.userScore;
    }

    public void setUserScore(final double userScore) {
        this.userScore = userScore;
    }

    public double getFirCheckScore() {
        return this.firCheckScore;
    }

    public void setFirCheckScore(final double firCheckScore) {
        this.firCheckScore = firCheckScore;
    }

    public double getSecCheckScore() {
        return this.secCheckScore;
    }

    public void setSecCheckScore(final double secCheckScore) {
        this.secCheckScore = secCheckScore;
    }

    public double getAudCheckScore() {
        return this.audCheckScore;
    }

    public void setAudCheckScore(final double audCheckScore) {
        this.audCheckScore = audCheckScore;
    }
}
