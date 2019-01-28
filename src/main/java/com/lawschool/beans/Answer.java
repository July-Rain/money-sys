package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;

import java.math.BigDecimal;
@TableName("LAW_ANSWER")
public class Answer extends DataEntity<Answer> {

    private String questionId;// 试题ID
    private String questionContent;// 选项内容
    private BigDecimal ordersort;// 排序
    private BigDecimal score;// 分数

    @TableField(exist = false)
    private Integer isAnswer;// 是否为答案

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

    public Integer getIsAnswer() {
        return isAnswer;
    }

    public void setIsAnswer(Integer isAnswer) {
        this.isAnswer = isAnswer;
    }
}