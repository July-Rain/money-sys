package com.lawschool.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 
 * @Title:ExamQueConfig.java
 * @Description: 考试题型设置
 * @author: 王帅奇
 * @date 2018年12月3日
 */
@TableName("LAW_EXAM_QUESTION_CONFIG")
public class ExamQueConfig implements Serializable{
	  /**
	 * 
	 */
	private static final long serialVersionUID = -2328174082418076685L;

	/**
     * null
     */
    private String id;

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
     * null
     */
    private Date addTime;

    /**
     * null
     */
    private String updateUser;

    /**
     * null
     */
    private Date updateTime;

    /**
     * null
     * @return ID null
     */
    public String getId() {
        return id;
    }

    /**
     * null
     * @param id null
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

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

    /**
     * null
     * @return ADD_USER null
     */
    public String getAddUser() {
        return addUser;
    }

    /**
     * null
     * @param addUser null
     */
    public void setAddUser(String addUser) {
        this.addUser = addUser == null ? null : addUser.trim();
    }

    /**
     * null
     * @return ADD_TIME null
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * null
     * @param addTime null
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * null
     * @return UPDATE_USER null
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * null
     * @param updateUser null
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    /**
     * null
     * @return UPDATE_TIME null
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * null
     * @param updateTime null
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public String getSpecialKnowledgeId() {
		return specialKnowledgeId;
	}

	public void setSpecialKnowledgeId(String specialKnowledgeId) {
		this.specialKnowledgeId = specialKnowledgeId;
	}
}
