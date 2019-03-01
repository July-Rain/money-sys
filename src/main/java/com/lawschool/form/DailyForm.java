package com.lawschool.form;

import java.util.Date;
import java.util.List;

/**
 * @Auther: Moon
 * @Date: 2019/2/28 17:23
 * @Description:
 */
public class DailyForm {

    private String id;// 主键

    private String ruleName;// 每日一题规则名称
    private List<String> topics;// 主题，多选
    private String createRule;// 出题规则 1:统一试题.0:随机不同
    private String obtainPoint;// 获得积分

    private String createWay;// 出题方式（2: 随机、1: 自定义）
    private String isShowAnswer;// 是否显示答案（是：1，否：0）
    private List<String> diffcs;// 试题难度，多选
    private Date beginTime;// 每日一题规则开始时间

    private String questionDifficulty;
    private String specialKnowledgeId;
    private String createUserName;
    private String userId;

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public String getCreateRule() {
        return createRule;
    }

    public void setCreateRule(String createRule) {
        this.createRule = createRule;
    }

    public String getObtainPoint() {
        return obtainPoint;
    }

    public void setObtainPoint(String obtainPoint) {
        this.obtainPoint = obtainPoint;
    }

    public String getCreateWay() {
        return createWay;
    }

    public void setCreateWay(String createWay) {
        this.createWay = createWay;
    }

    public String getIsShowAnswer() {
        return isShowAnswer;
    }

    public void setIsShowAnswer(String isShowAnswer) {
        this.isShowAnswer = isShowAnswer;
    }

    public List<String> getDiffcs() {
        return diffcs;
    }

    public void setDiffcs(List<String> diffcs) {
        this.diffcs = diffcs;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionDifficulty() {
        return questionDifficulty;
    }

    public void setQuestionDifficulty(String questionDifficulty) {
        this.questionDifficulty = questionDifficulty;
    }

    public String getSpecialKnowledgeId() {
        return specialKnowledgeId;
    }

    public void setSpecialKnowledgeId(String specialKnowledgeId) {
        this.specialKnowledgeId = specialKnowledgeId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
