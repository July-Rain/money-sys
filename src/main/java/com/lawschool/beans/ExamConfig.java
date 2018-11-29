package com.lawschool.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableName;

@TableName("LAW_EXAM_CONFIG")
public class ExamConfig {
    private String id;

    private String examName;

    private String specialKnowledgeId;

    private String groupForm;

    private String examType;

    private String questionWay;

    private String topicOrderType;

    private String optionOrderType;

    private Date startTime;

    private Date endTime;

    private Short examTime;

    private String answerShowRule;

    private Short examScore;

    private Short passPnt;

    private String reachRewardType;

    private String reachReward;

    private String organizedOrgCode;

    private String isShowLaw;

    private String isShowLawProrortion;

    private String isMustTest;

    private BigDecimal examCount;

    private String checkType;

    private BigDecimal checkNum;

    private String checkPassword;

    private Short checkScoreDifference;

    private String isAibitration;

    private String aibitrationUserCode;

    private String configState;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName == null ? null : examName.trim();
    }

    public String getSpecialKnowledgeId() {
        return specialKnowledgeId;
    }

    public void setSpecialKnowledgeId(String specialKnowledgeId) {
        this.specialKnowledgeId = specialKnowledgeId == null ? null : specialKnowledgeId.trim();
    }

    public String getGroupForm() {
        return groupForm;
    }

    public void setGroupForm(String groupForm) {
        this.groupForm = groupForm == null ? null : groupForm.trim();
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType == null ? null : examType.trim();
    }

    public String getQuestionWay() {
        return questionWay;
    }

    public void setQuestionWay(String questionWay) {
        this.questionWay = questionWay == null ? null : questionWay.trim();
    }

    public String getTopicOrderType() {
        return topicOrderType;
    }

    public void setTopicOrderType(String topicOrderType) {
        this.topicOrderType = topicOrderType == null ? null : topicOrderType.trim();
    }

    public String getOptionOrderType() {
        return optionOrderType;
    }

    public void setOptionOrderType(String optionOrderType) {
        this.optionOrderType = optionOrderType == null ? null : optionOrderType.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Short getExamTime() {
        return examTime;
    }

    public void setExamTime(Short examTime) {
        this.examTime = examTime;
    }

    public String getAnswerShowRule() {
        return answerShowRule;
    }

    public void setAnswerShowRule(String answerShowRule) {
        this.answerShowRule = answerShowRule == null ? null : answerShowRule.trim();
    }

    public Short getExamScore() {
        return examScore;
    }

    public void setExamScore(Short examScore) {
        this.examScore = examScore;
    }

    public Short getPassPnt() {
        return passPnt;
    }

    public void setPassPnt(Short passPnt) {
        this.passPnt = passPnt;
    }

    public String getReachRewardType() {
        return reachRewardType;
    }

    public void setReachRewardType(String reachRewardType) {
        this.reachRewardType = reachRewardType == null ? null : reachRewardType.trim();
    }

    public String getReachReward() {
        return reachReward;
    }

    public void setReachReward(String reachReward) {
        this.reachReward = reachReward == null ? null : reachReward.trim();
    }

    public String getOrganizedOrgCode() {
        return organizedOrgCode;
    }

    public void setOrganizedOrgCode(String organizedOrgCode) {
        this.organizedOrgCode = organizedOrgCode == null ? null : organizedOrgCode.trim();
    }

    public String getIsShowLaw() {
        return isShowLaw;
    }

    public void setIsShowLaw(String isShowLaw) {
        this.isShowLaw = isShowLaw == null ? null : isShowLaw.trim();
    }

    public String getIsShowLawProrortion() {
        return isShowLawProrortion;
    }

    public void setIsShowLawProrortion(String isShowLawProrortion) {
        this.isShowLawProrortion = isShowLawProrortion == null ? null : isShowLawProrortion.trim();
    }

    public String getIsMustTest() {
        return isMustTest;
    }

    public void setIsMustTest(String isMustTest) {
        this.isMustTest = isMustTest == null ? null : isMustTest.trim();
    }

    public BigDecimal getExamCount() {
        return examCount;
    }

    public void setExamCount(BigDecimal examCount) {
        this.examCount = examCount;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType == null ? null : checkType.trim();
    }

    public BigDecimal getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(BigDecimal checkNum) {
        this.checkNum = checkNum;
    }

    public String getCheckPassword() {
        return checkPassword;
    }

    public void setCheckPassword(String checkPassword) {
        this.checkPassword = checkPassword == null ? null : checkPassword.trim();
    }

    public Short getCheckScoreDifference() {
        return checkScoreDifference;
    }

    public void setCheckScoreDifference(Short checkScoreDifference) {
        this.checkScoreDifference = checkScoreDifference;
    }

    public String getIsAibitration() {
        return isAibitration;
    }

    public void setIsAibitration(String isAibitration) {
        this.isAibitration = isAibitration == null ? null : isAibitration.trim();
    }

    public String getAibitrationUserCode() {
        return aibitrationUserCode;
    }

    public void setAibitrationUserCode(String aibitrationUserCode) {
        this.aibitrationUserCode = aibitrationUserCode == null ? null : aibitrationUserCode.trim();
    }

    public String getConfigState() {
        return configState;
    }

    public void setConfigState(String configState) {
        this.configState = configState == null ? null : configState.trim();
    }
}