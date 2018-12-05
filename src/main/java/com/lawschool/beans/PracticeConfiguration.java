package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

@TableName("LAW_PRACTICE_CONFIGURATION")
public class PracticeConfiguration implements Serializable {
    /**
     * 练习卷配置ID
     */
    @TableId
    private String id;

    /**
     * 前缀
     */
    private String prefix;

    /**
     * 练习卷试题类型
     */
    private String questionType;

    /**
     * 适用范围
     */
    private String range;

    /**
     * 题型数量
     */
    private Integer totalCount;

    /**
     * 初级题目数
     */
    private Integer primaryCount;

    /**
     * 中级题目数
     */
    private Integer intermediateCount;

    /**
     * 高级题目数
     */
    private Integer seniorCount;

    /**
     * 练习人员
     */
    private String practitioners;

    /**
     * 创建人
     */
    private String addUser;

    /**
     * 创建时间
     */
    private Date addDate;

    /**
     * 操作人
     */
    private String updateUser;

    /**
     * 操作时间
     */
    private Date updateDate;

    /**
     *知识点ID
     */
    private String specialKnowledgeId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPrimaryCount() {
        return primaryCount;
    }

    public void setPrimaryCount(Integer primaryCount) {
        this.primaryCount = primaryCount;
    }

    public Integer getIntermediateCount() {
        return intermediateCount;
    }

    public void setIntermediateCount(Integer intermediateCount) {
        this.intermediateCount = intermediateCount;
    }

    public Integer getSeniorCount() {
        return seniorCount;
    }

    public void setSeniorCount(Integer seniorCount) {
        this.seniorCount = seniorCount;
    }

    public String getPractitioners() {
        return practitioners;
    }

    public void setPractitioners(String practitioners) {
        this.practitioners = practitioners;
    }

    public String getAddUser() {
        return addUser;
    }

    public void setAddUser(String addUser) {
        this.addUser = addUser;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getSpecialKnowledgeId() {
        return specialKnowledgeId;
    }

    public void setSpecialKnowledgeId(String specialKnowledgeId) {
        this.specialKnowledgeId = specialKnowledgeId;
    }

    @Override
    public String toString() {
        return "PracticeConfiguration{" +
                "id='" + id + '\'' +
                ", prefix='" + prefix + '\'' +
                ", questionType='" + questionType + '\'' +
                ", range='" + range + '\'' +
                ", totalCount=" + totalCount +
                ", primaryCount=" + primaryCount +
                ", intermediateCount=" + intermediateCount +
                ", seniorCount=" + seniorCount +
                ", practitioners='" + practitioners + '\'' +
                ", addUser='" + addUser + '\'' +
                ", addDate=" + addDate +
                ", updateUser='" + updateUser + '\'' +
                ", updateDate=" + updateDate +
                ", specialKnowledgeId='" + specialKnowledgeId + '\'' +
                '}';
    }
}