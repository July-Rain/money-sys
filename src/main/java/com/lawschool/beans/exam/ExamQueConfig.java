package com.lawschool.beans.exam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;

/**
 * 
 * @Title:ExamQueConfig.java
 * @Description: 考试题型设置
 * @author: 王帅奇
 * @date 2018年12月3日
 */
@TableName("LAW_EXAM_QUESTION_CONFIG")
public class ExamQueConfig extends DataEntity<ExamQueConfig> {
	  /**
	 * 
	 */
	private static final long serialVersionUID = -2328174082418076685L;


    /**
     * 考试配置ID
     */
    private String lawExamConfigId;

    /**
     * 专项知识点
     */
    private String specialKnowledgeId;

    /**
     * 题目类型（单选、多选等）
     */
    private String questionType;

    /**
     * 题目数量
     */
    private BigDecimal questionNumber;

    /**
     * 题目分值
     */
    private BigDecimal questionScore;

    /**
     * null
     */
    private String addUser;

    /**
     * 考试配置ID
     * @return LAW_EXAM_CONFIG_ID 考试配置ID
     */
    public String getLawExamConfigId() {
        return lawExamConfigId;
    }

    /**
     * 考试配置ID
     * @param lawExamConfigId 考试配置ID
     */
    public void setLawExamConfigId(String lawExamConfigId) {
        this.lawExamConfigId = lawExamConfigId == null ? null : lawExamConfigId.trim();
    }

  

    /**
     * 题目类型（单选、多选等）
     * @return QUESTION_TYPE 题目类型（单选、多选等）
     */
    public String getQuestionType() {
        return questionType;
    }

    /**
     * 题目类型（单选、多选等）
     * @param questionType 题目类型（单选、多选等）
     */
    public void setQuestionType(String questionType) {
        this.questionType = questionType == null ? null : questionType.trim();
    }

    /**
     * 题目数量
     * @return QUESTION_NUMBER 题目数量
     */
    public BigDecimal getQuestionNumber() {
        return questionNumber;
    }

    /**
     * 题目数量
     * @param questionNumber 题目数量
     */
    public void setQuestionNumber(BigDecimal questionNumber) {
        this.questionNumber = questionNumber;
    }

    /**
     * 题目分值
     * @return QUESTION_SCORE 题目分值
     */
    public BigDecimal getQuestionScore() {
        return questionScore;
    }

    /**
     * 题目分值
     * @param questionScore 题目分值
     */
    public void setQuestionScore(BigDecimal questionScore) {
        this.questionScore = questionScore;
    }

	public String getSpecialKnowledgeId() {
		return specialKnowledgeId;
	}

	public void setSpecialKnowledgeId(String specialKnowledgeId) {
		this.specialKnowledgeId = specialKnowledgeId;
	}
}
