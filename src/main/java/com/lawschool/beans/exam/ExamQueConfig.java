package com.lawschool.beans.exam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
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
    private String examConfigId;

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

    private float everyQuestionScore;   //每题分值

    @TableField( exist = false)
    private String typeName;

    @TableField( exist = false)
    private String[] specialKnowledgeArr;

    public float getEveryQuestionScore() {
        return this.everyQuestionScore;
    }

    public void setEveryQuestionScore(final float everyQuestionScore) {
        this.everyQuestionScore = everyQuestionScore;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(final String typeName) {
        this.typeName = typeName;
    }

    public String[] getSpecialKnowledgeArr() {
        return this.specialKnowledgeArr;
    }

    public void setSpecialKnowledgeArr(final String[] specialKnowledgeArr) {
        this.specialKnowledgeArr = specialKnowledgeArr;
    }

    public String getExamConfigId() {
        return this.examConfigId;
    }

    public void setExamConfigId(final String examConfigId) {
        this.examConfigId = examConfigId;
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
