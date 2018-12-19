package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;

import java.math.BigDecimal;
@TableName("LAW_ANSWER")
public class Answer extends DataEntity<Answer> {

    private String questionId;

    private String questionContent;

    private BigDecimal ordersort;

    private BigDecimal score;


    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId == null ? null : questionId.trim();
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent == null ? null : questionContent.trim();
    }

    public BigDecimal getOrdersort() {
        return ordersort;
    }

    public void setOrdersort(BigDecimal ordersort) {
        this.ordersort = ordersort;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id='" + id + '\'' +
                ", questionId='" + questionId + '\'' +
                ", questionContent='" + questionContent + '\'' +
                ", ordersort=" + ordersort +
                ", score=" + score +
                ", optuser='" + optUser + '\'' +
                ", opttime=" + optTime +
                '}';
    }
}