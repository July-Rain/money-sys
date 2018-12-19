package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
     * 练习卷试题类型 选择、判断、主观题
     */
    @TableField(exist = false)
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
     * 练习人员
     */
    private String practitioners;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 操作人
     */
    private String optUser;

    /**
     * 操作时间
     */
    private Date optTime;

    @TableField(exist = false)
    private List<PracticeConfiguration02> list =new ArrayList<PracticeConfiguration02>();

    //知识点ID中文
    @TableField(exist = false)
    private String specialKnowledgeName;

    private String questionId;

    /**
     *练习卷名称（前缀+日期+系统编号）
     */
    private String practiceName;

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

    public String getPractitioners() {
        return practitioners;
    }

    public void setPractitioners(String practitioners) {
        this.practitioners = practitioners;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getSpecialKnowledgeName() {
        return specialKnowledgeName;
    }

    public void setSpecialKnowledgeName(String specialKnowledgeName) {
        this.specialKnowledgeName = specialKnowledgeName;
    }

    public List<PracticeConfiguration02> getList() {
        return list;
    }

    public void setList(List<PracticeConfiguration02> list) {
        this.list = list;
    }

    public String getPracticeName() {
        return practiceName;
    }

    public void setPracticeName(String practiceName) {
        this.practiceName = practiceName;
    }

    @Override
    public String toString() {
        return "PracticeConfiguration{" +
                "id='" + id + '\'' +
                ", prefix='" + prefix + '\'' +
                ", questionType='" + questionType + '\'' +
                ", range='" + range + '\'' +
                ", totalCount=" + totalCount +
                ", practitioners='" + practitioners + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                ", optUser='" + optUser + '\'' +
                ", optTime=" + optTime +
                ", list=" + list +
                ", specialKnowledgeName='" + specialKnowledgeName + '\'' +
                ", questionId='" + questionId + '\'' +
                ", practiceName='" + practiceName + '\'' +
                '}';
    }
}