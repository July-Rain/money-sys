package com.lawschool.beans;

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

    private String optuser;

    private Date opttime;

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

    public String getOptuser() {
        return optuser;
    }

    public void setOptuser(String optuser) {
        this.optuser = optuser == null ? null : optuser.trim();
    }

    public Date getOpttime() {
        return opttime;
    }

    public void setOpttime(Date opttime) {
        this.opttime = opttime;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id='" + id + '\'' +
                ", questionId='" + questionId + '\'' +
                ", questionContent='" + questionContent + '\'' +
                ", ordersort=" + ordersort +
                ", score=" + score +
                ", optuser='" + optuser + '\'' +
                ", opttime=" + opttime +
                '}';
    }
}