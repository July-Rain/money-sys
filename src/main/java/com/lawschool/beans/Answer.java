package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;
import java.util.Date;
@TableName("LAW_ANSWER")
public class Answer {
    private String id;

    private String questionId;

    private String questionContent;

    private BigDecimal order;

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

    public BigDecimal getOrder() {
        return order;
    }

    public void setOrder(BigDecimal order) {
        this.order = order;
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
}