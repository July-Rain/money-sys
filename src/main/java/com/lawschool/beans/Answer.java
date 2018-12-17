package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;
import java.util.Date;
@TableName("LAW_ANSWER")
public class Answer {
    @TableId
    private String id;

    private String questionId;

    private String questionContent;

    private BigDecimal ordersort;

    private BigDecimal score;

    private String optUser;

    @TableField(exist = false)
    private Date optTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

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


    public String getOptUser() {
        return optUser;
    }

    public void setOptUser(String optUser) {
        this.optUser = optUser;
    }

    public Date getOptTime() {
        return optTime;
    }

    public void setOptTime(Date optTime) {
        this.optTime = optTime;
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