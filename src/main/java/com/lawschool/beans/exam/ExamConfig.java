package com.lawschool.beans.exam;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawschool.base.BaseEntity;
import com.lawschool.base.DataEntity;
import com.lawschool.beans.TestQuestions;
import com.lawschool.form.CommonForm;
import com.lawschool.form.QuestForm;

/**
 * @Title:ExamConfig.java
 * @Description: 考试配置主表
 * @author: 王帅奇
 * @date 2018年12月7日
 */
@TableName("LAW_EXAM_CONFIG")
public class ExamConfig extends DataEntity<ExamConfig> {
    /**
     *
     */
    private static final long serialVersionUID = -4298322751587715208L;

    /**
     * 考试名称
     */
    private String examName;

    /**
     * 专项知识ID
     */
    private String specialKnowledgeId;

    /**
     * 组卷形式
     */
    private String groupForm;

    /**
     * 试卷类型
     */
    private String examType;

    /**
     * 出题方式
     */
    private String questionWay;

    /**
     * 题目顺序
     */
    private String topicOrderType;

    /**
     * 选项顺序
     */
    private String optionOrderType;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    /**
     * 考试时长
     */
    private Float examTime;

    /**
     * 答案显示规则
     */
    private String answerShowRule;

    /**
     * 考试分数
     */
    private Float examScore;

    /**
     * 及格线
     */
    private Float passPnt;

    /**
     * 达标奖励方式
     */
    private String reachRewardType;

    /**
     * 达标奖励
     */
    private String reachReward;

    /**
     * 组织机构代码
     */
    private String organizedOrgCode;

    /**
     * 是否显示法律依据
     */
    private String isShowLaw;

    /**
     * 是否显示法律比例
     */
    private String isShowLawProrortion;

    /**
     * 是否必考
     */
    private String isMustTest;

    /**
     * 题目数量
     */
    private Integer examCount;

    /**
     * 阅卷类型
     */
    private String checkType;

    /**
     * 配置状态
     */
    private String configState;

    /**
     * 阅卷人数
     */
    private Integer checkNum;

    /**
     * 阅卷口令
     */
    private String checkPassword;

    /**
     * 阅卷分差值
     */
    private Float checkScoreDifference;

    /**
     * 是否仲裁
     */
    private String isAibitration;

    /**
     * 仲裁人code
     */
    private String aibitrationUserCode;

    /**
     * 每套试卷阅卷人数（1,或者2）
     */
    private Integer paperPerSetNum;

    /**
     * 审核人员口令
     */
    private String auditCheckPass;

    private String userName;//适用人员名称

    private String deptName;//适用部门名称

    @TableField(exist = false)
    private String[] deptArr;//适用部门

    @TableField(exist = false)
    private String[] userArr;//适用人员

    @TableField(exist = false)
    private String examTypeName;

    @TableField(exist = false)
    private List<ExamQueConfig> examQueConfigList;  //试题配置

    @TableField(exist = false)
    private List<String> idList;

    @TableField(exist = false)
    private List<CommonForm> commonForms; //使用此form key为题目ID value 为题目分值

    //预览试题列表
    @TableField(exist = false)
    private List<QuestForm> qfList;

    private String status; //组卷完成状态  0未完成  1已完成

    private String enabled; //是否启用 0 未启用  1已启用

    @TableField(exist = false)
    private String[] specialKnowledgeArr;

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getSpecialKnowledgeId() {
        return specialKnowledgeId;
    }

    public void setSpecialKnowledgeId(String specialKnowledgeId) {
        this.specialKnowledgeId = specialKnowledgeId;
    }

    public String getGroupForm() {
        return groupForm;
    }

    public void setGroupForm(String groupForm) {
        this.groupForm = groupForm;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public String getQuestionWay() {
        return questionWay;
    }

    public void setQuestionWay(String questionWay) {
        this.questionWay = questionWay;
    }

    public String getTopicOrderType() {
        return topicOrderType;
    }

    public void setTopicOrderType(String topicOrderType) {
        this.topicOrderType = topicOrderType;
    }

    public String getOptionOrderType() {
        return optionOrderType;
    }

    public void setOptionOrderType(String optionOrderType) {
        this.optionOrderType = optionOrderType;
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

    public Float getExamTime() {
        return examTime;
    }

    public void setExamTime(Float examTime) {
        this.examTime = examTime;
    }

    public String getAnswerShowRule() {
        return answerShowRule;
    }

    public void setAnswerShowRule(String answerShowRule) {
        this.answerShowRule = answerShowRule;
    }

    public Float getExamScore() {
        return examScore;
    }

    public void setExamScore(Float examScore) {
        this.examScore = examScore;
    }

    public Float getPassPnt() {
        return passPnt;
    }

    public void setPassPnt(Float passPnt) {
        this.passPnt = passPnt;
    }

    public String getReachRewardType() {
        return reachRewardType;
    }

    public void setReachRewardType(String reachRewardType) {
        this.reachRewardType = reachRewardType;
    }

    public String getReachReward() {
        return reachReward;
    }

    public void setReachReward(String reachReward) {
        this.reachReward = reachReward;
    }

    public String getOrganizedOrgCode() {
        return organizedOrgCode;
    }

    public void setOrganizedOrgCode(String organizedOrgCode) {
        this.organizedOrgCode = organizedOrgCode;
    }

    public String getIsShowLaw() {
        return isShowLaw;
    }

    public void setIsShowLaw(String isShowLaw) {
        this.isShowLaw = isShowLaw;
    }

    public String getIsShowLawProrortion() {
        return isShowLawProrortion;
    }

    public void setIsShowLawProrortion(String isShowLawProrortion) {
        this.isShowLawProrortion = isShowLawProrortion;
    }

    public String getIsMustTest() {
        return isMustTest;
    }

    public void setIsMustTest(String isMustTest) {
        this.isMustTest = isMustTest;
    }

    public Integer getExamCount() {
        return examCount;
    }

    public void setExamCount(Integer examCount) {
        this.examCount = examCount;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getConfigState() {
        return configState;
    }

    public void setConfigState(String configState) {
        this.configState = configState;
    }

    public Integer getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(Integer checkNum) {
        this.checkNum = checkNum;
    }

    public String getCheckPassword() {
        return checkPassword;
    }

    public void setCheckPassword(String checkPassword) {
        this.checkPassword = checkPassword;
    }

    public Float getCheckScoreDifference() {
        return checkScoreDifference;
    }

    public void setCheckScoreDifference(Float checkScoreDifference) {
        this.checkScoreDifference = checkScoreDifference;
    }

    public String getIsAibitration() {
        return isAibitration;
    }

    public void setIsAibitration(String isAibitration) {
        this.isAibitration = isAibitration;
    }

    public String getAibitrationUserCode() {
        return aibitrationUserCode;
    }

    public void setAibitrationUserCode(String aibitrationUserCode) {
        this.aibitrationUserCode = aibitrationUserCode;
    }

    public Integer getPaperPerSetNum() {
        return paperPerSetNum;
    }

    public void setPaperPerSetNum(Integer paperPerSetNum) {
        this.paperPerSetNum = paperPerSetNum;
    }

    public String getAuditCheckPass() {
        return auditCheckPass;
    }

    public void setAuditCheckPass(String auditCheckPass) {
        this.auditCheckPass = auditCheckPass;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String[] getDeptArr() {
        return deptArr;
    }

    public void setDeptArr(String[] deptArr) {
        this.deptArr = deptArr;
    }

    public String[] getUserArr() {
        return userArr;
    }

    public void setUserArr(String[] userArr) {
        this.userArr = userArr;
    }

    public String getExamTypeName() {
        return examTypeName;
    }

    public void setExamTypeName(String examTypeName) {
        this.examTypeName = examTypeName;
    }

    public List<ExamQueConfig> getExamQueConfigList() {
        return examQueConfigList;
    }

    public void setExamQueConfigList(List<ExamQueConfig> examQueConfigList) {
        this.examQueConfigList = examQueConfigList;
    }

    public List<String> getIdList() {
        return idList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }

    public List<CommonForm> getCommonForms() {
        return commonForms;
    }

    public void setCommonForms(List<CommonForm> commonForms) {
        this.commonForms = commonForms;
    }

    public List<QuestForm> getQfList() {
        return qfList;
    }

    public void setQfList(List<QuestForm> qfList) {
        this.qfList = qfList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String[] getSpecialKnowledgeArr() {
        return specialKnowledgeArr;
    }

    public void setSpecialKnowledgeArr(String[] specialKnowledgeArr) {
        this.specialKnowledgeArr = specialKnowledgeArr;
    }
}