package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawschool.base.DataEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author liuhuan
 */
@TableName("LAW_DAILY_QUESTION")
public class DailyQuestionConfiguration extends DataEntity<DailyQuestionConfiguration>{
    /**
     * 每日一题ID
     */
    /*@TableId
    private String id;*/

    /**
     * 专项知识点
     */
    private String specialKnowledgeId;

    //前端js传到controller时候接多选知识点id
    @TableField(exist = false)
    private List<String> specialKnowledgeIds=new ArrayList<String>();

    /**
     *出题规则 1:统一试题.0:随机不同
     */
    private Integer createRule;

    /**
     * 获得积分
     */
    private Integer obtainPoint;

    /**
     * 是否显示法律依据 （是：1 ，否：0）
     */
    private Integer isShowLegal;

    /**
     * 出题方式（2: 随机、1: 自定义）
     */
    private Integer createWay;

    /**
     * 是否显示答案（是：1，否：0）
     */
    private Integer isShowAnswer;

    /**
     * 试题难度（初级：10001，中级：10002，高级：10003）
     */
    private String questionDifficulty;

    /**
     * 参与人ID
     */
    private String joinPeople;

    /**
     * 参与部门ID
     */
    private String joinDept;

    /**
     * 题目ID
     */
    private String questionId;

    /**
     *每日一题规则开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date beginTime;

    /**
     *每日一题规则结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;

    /**
     *试题类型
     */
    private String questionType;

    public List<String> getSpecialKnowledgeIds() {
        return specialKnowledgeIds;
    }

    public void setSpecialKnowledgeIds(List<String> specialKnowledgeIds) {
        this.specialKnowledgeIds = specialKnowledgeIds;
    }

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

    public Integer getCreateRule() {
        return createRule;
    }

    public void setCreateRule(Integer createRule) {
        this.createRule = createRule;
    }

    public Integer getObtainPoint() {
        return obtainPoint;
    }

    public void setObtainPoint(Integer obtainPoint) {
        this.obtainPoint = obtainPoint;
    }

    public Integer getIsShowLegal() {
        return isShowLegal;
    }

    public void setIsShowLegal(Integer isShowLegal) {
        this.isShowLegal = isShowLegal;
    }

    public Integer getCreateWay() {
        return createWay;
    }

    public void setCreateWay(Integer createWay) {
        this.createWay = createWay;
    }

    public Integer getIsShowAnswer() {
        return isShowAnswer;
    }

    public void setIsShowAnswer(Integer isShowAnswer) {
        this.isShowAnswer = isShowAnswer;
    }

    public String getQuestionDifficulty() {
        return questionDifficulty;
    }

    public void setQuestionDifficulty(String questionDifficulty) {
        this.questionDifficulty = questionDifficulty;
    }

    public String getJoinPeople() {
        return joinPeople;
    }

    public void setJoinPeople(String joinPeople) {
        this.joinPeople = joinPeople;
    }

    public String getJoinDept() {
        return joinDept;
    }

    public void setJoinDept(String joinDept) {
        this.joinDept = joinDept;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    @Override
    public String toString() {
        return "DailyQuestionConfiguration{" +
                "specialKnowledgeId='" + specialKnowledgeId + '\'' +
                ", createRule='" + createRule + '\'' +
                ", obtainPoint=" + obtainPoint +
                ", isShowLegal=" + isShowLegal +
                ", createWay=" + createWay +
                ", isShowAnswer=" + isShowAnswer +
                ", questionDifficulty=" + questionDifficulty +
                ", joinPeople='" + joinPeople + '\'' +
                ", joinDept='" + joinDept + '\'' +
                ", questionId='" + questionId + '\'' +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", questionType='" + questionType + '\'' +
                '}';
    }
}
