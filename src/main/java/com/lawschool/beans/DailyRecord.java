package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;
import com.lawschool.form.AnswerForm;

import java.util.List;

/**
 * @Author liuhuan
 * 每日一题记录表
 */
@TableName("LAW_DAILY_RECORD")
public class DailyRecord extends DataEntity<DailyRecord> {

    /**
     * 题目内容
     */
    private String comContent;

    /**
     * 题目ID
     */
    private String questionId;

    /**
     * 试题难度
     */
    private String questionDifficulty;

    /**
     * 试题类型
     */
    private String questionType;

    /**
     * 答案ID
     */
    private String answerId;

    /**
     * 法律依据
     */
    private String legalBasis;

    /**
     * 答案描述
     */
    private String answerDescrible;

    /**
     * 是否做过(已做、未做)
     */
    private String isDown;

    /**
     * 用户ID
     * @return
     */
    private String userId;

    /**
     * 答案数组
     * @return
     */
    @TableField(exist = false)
    private List<AnswerForm> answerList;

    public String getComContent() {
        return comContent;
    }

    public void setComContent(String comContent) {
        this.comContent = comContent;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionDifficulty() {
        return questionDifficulty;
    }

    public void setQuestionDifficulty(String questionDifficulty) {
        this.questionDifficulty = questionDifficulty;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getLegalBasis() {
        return legalBasis;
    }

    public void setLegalBasis(String legalBasis) {
        this.legalBasis = legalBasis;
    }

    public String getAnswerDescrible() {
        return answerDescrible;
    }

    public void setAnswerDescrible(String answerDescrible) {
        this.answerDescrible = answerDescrible;
    }

    public String getIsDown() {
        return isDown;
    }

    public void setIsDown(String isDown) {
        this.isDown = isDown;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<AnswerForm> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<AnswerForm> answerList) {
        this.answerList = answerList;
    }

    @Override
    public String toString() {
        return "DailyRecord{" +
                "comContent='" + comContent + '\'' +
                ", questionId='" + questionId + '\'' +
                ", questionDifficulty='" + questionDifficulty + '\'' +
                ", questionType='" + questionType + '\'' +
                ", answerId='" + answerId + '\'' +
                ", legalBasis='" + legalBasis + '\'' +
                ", answerDescrible='" + answerDescrible + '\'' +
                ", isDown='" + isDown + '\'' +
                ", userId='" + userId + '\'' +
                ", answerList=" + answerList +
                '}';
    }
}
