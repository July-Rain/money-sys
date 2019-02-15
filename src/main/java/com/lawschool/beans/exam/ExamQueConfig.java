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
    private int questionNumber;

    /**
     * 题目分值
     */
    private float questionScore;

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

	public String getSpecialKnowledgeId() {
		return specialKnowledgeId;
	}

	public void setSpecialKnowledgeId(String specialKnowledgeId) {
		this.specialKnowledgeId = specialKnowledgeId;
	}

    public int getQuestionNumber() {
        return this.questionNumber;
    }

    public void setQuestionNumber(final int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public float getQuestionScore() {
        return this.questionScore;
    }

    public void setQuestionScore(final float questionScore) {
        this.questionScore = questionScore;
    }

}
