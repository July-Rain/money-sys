package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.annotation.SysLog;
import com.lawschool.base.DataEntity;

import java.util.Date;

/**
 * 统一试题记录表
 */
@TableName("LAW_DAILY_SAME")
public class DailySame extends DataEntity<DailySame> {

    /**
     * 统一试题记录ID
     */
    //private String id;

    /**
     * 试题内容
     */
    private String comContent;

    /**
     * 试题ID
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

    @Override
    public String toString() {
        return "DailySame{" +
                "comContent='" + comContent + '\'' +
                ", questionId='" + questionId + '\'' +
                ", questionDifficulty='" + questionDifficulty + '\'' +
                ", questionType='" + questionType + '\'' +
                ", answerId='" + answerId + '\'' +
                ", legalBasis='" + legalBasis + '\'' +
                ", answerDescrible='" + answerDescrible + '\'' +
                '}';
    }
}