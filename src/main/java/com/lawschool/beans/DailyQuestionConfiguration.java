package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * @Author liuhuan
 */
@TableName("LAW_DAILY_QUESTION")
public class DailyQuestionConfiguration {
    /**
     * 每日一题ID
     */
    @TableId
    private String id;

    /**
     * 专项知识点
     */
    private String specialKnowledgeId;

    /**
     *出题规则（统一试题...）
     */
    private String createRule;

    /**
     * 获得积分
     */
    private Integer obtainPoint;

    /**
     * 是否显示法律依据 （是：1 ，否：2）
     */
    private Integer isShowLegal;

    /**
     * 出题方式（随机：1，自定义：2）
     */
    private Integer createWay;

    /**
     * 是否显示答案（是：1，否：2）
     */
    private Integer isShowAnswer;

    /**
     * 试题难度（初级：10001，中级：10002，高级：10003）
     */
    private Integer questionDifficulty;

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

    public String getCreateRule() {
        return createRule;
    }

    public void setCreateRule(String createRule) {
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

    public Integer getQuestionDifficulty() {
        return questionDifficulty;
    }

    public void setQuestionDifficulty(Integer questionDifficulty) {
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

    @Override
    public String toString() {
        return "DailyQuestionConfiguration{" +
                "id='" + id + '\'' +
                ", specialKnowledgeId='" + specialKnowledgeId + '\'' +
                ", createRule='" + createRule + '\'' +
                ", obtainPoint=" + obtainPoint +
                ", isShowLegal=" + isShowLegal +
                ", createWay=" + createWay +
                ", isShowAnswer=" + isShowAnswer +
                ", questionDifficulty=" + questionDifficulty +
                ", joinPeople='" + joinPeople + '\'' +
                ", joinDept='" + joinDept + '\'' +
                ", questionId='" + questionId + '\'' +
                '}';
    }
}
