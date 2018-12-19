package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * 配置从表
 */
@TableName("LAW_PRACTICE_CONFIGURATION02")
public class PracticeConfiguration02 implements Serializable {
    /**
     * 从表ID
     */
    @TableId
    private String id;

    /**
     * 专项知识点ID
     */
    private String specialKnowledgeId;

    /**
     * 初级题量
     */
    private Integer primaryCount;

    /**
     * 中级题量
     */
    private Integer intermediateCount;

    /**
     *高级题量
     */
    private Integer seniorCount;

    /**
     * 配置主表ID
     */
    private String configurationId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpecialKnowledgeId() {
        return specialKnowledgeId;
    }

    public void setSpecialKnowledgeId(String specialKnowledgeId) {
        this.specialKnowledgeId = specialKnowledgeId;
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

    public String getConfigurationId() {
        return configurationId;
    }

    public void setConfigurationId(String configurationId) {
        this.configurationId = configurationId;
    }

    @Override
    public String toString() {
        return "PracticeConfiguration02{" +
                "id='" + id + '\'' +
                ", specialKnowledgeId='" + specialKnowledgeId + '\'' +
                ", primaryCount=" + primaryCount +
                ", intermediateCount=" + intermediateCount +
                ", seniorCount=" + seniorCount +
                ", configurationId='" + configurationId + '\'' +
                '}';
    }
}