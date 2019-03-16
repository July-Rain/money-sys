package com.lawschool.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawschool.base.DataEntity;

import java.util.Date;
import java.util.List;

/**
 * ClassName: UserExamForm
 * Description: 获取考试列表Form
 * date: 2019/1/1810:06
 *
 * @author 王帅奇
 */
public class UserExamForm extends DataEntity<UserExamForm> {
    private String id; //考试配置ID
    private String userExamId; //用户考试试卷ID
    private String examName;    //试卷名称
    private String examType;    //试卷类型
    private String examTime;   //考试时长
    private String organizedOrgCode;    //组考单位
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;   //考试开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;   //考试结束时间
    private String examStatus;  //考试状态  0未考  1 已考
    private String examTypeName;    //考试类型中文名
    private String isFinMark; // 是否完成阅卷  0:完成 1：未完成
    private String checkStatus; //阅卷状态  0 未开始  1 未完成  2 已完成
    private List<String> list ; //ids
    private String checkExamId; //阅卷ID
    private String[] authArr;   //数据权限
    private String userId;
    private String source ; //来源
    private String isMustTest;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date commitTime;   //交卷时间
    private Float rewards;  //J奖励内容
    private Float score;  //J奖励内容
    /**
     * 考试分数
     */
    private Short examScore;

    /**
     * 及格线
     */
    private Short passPnt;

    /**
     * 达标奖励方式
     */
    private String reachRewardType;
    private String reachRewardTypeName;

    /**
     * 达标奖励
     */
    private String reachReward;

    private Date currentTime;   //当前时间


    public Date getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime) {
        this.currentTime = currentTime;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Float getRewards() {
        return rewards;
    }

    public void setRewards(Float rewards) {
        this.rewards = rewards;
    }

    public Date getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Date commitTime) {
        this.commitTime = commitTime;
    }

    public String getIsMustTest() {
        return isMustTest;
    }

    public void setIsMustTest(String isMustTest) {
        this.isMustTest = isMustTest;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(final String source) {
        this.source = source;
    }

    public Short getExamScore() {
        return this.examScore;
    }

    public void setExamScore(final Short examScore) {
        this.examScore = examScore;
    }

    public Short getPassPnt() {
        return this.passPnt;
    }

    public void setPassPnt(final Short passPnt) {
        this.passPnt = passPnt;
    }

    public String getReachRewardTypeName() {
        return this.reachRewardTypeName;
    }

    public void setReachRewardTypeName(final String reachRewardTypeName) {
        this.reachRewardTypeName = reachRewardTypeName;
    }

    public String getReachRewardType() {
        return this.reachRewardType;
    }

    public void setReachRewardType(final String reachRewardType) {
        this.reachRewardType = reachRewardType;
    }

    public String getReachReward() {
        return this.reachReward;
    }

    public void setReachReward(final String reachReward) {
        this.reachReward = reachReward;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public String[] getAuthArr() {
        return this.authArr;
    }

    public void setAuthArr(final String[] authArr) {
        this.authArr = authArr;
    }

    public String getCheckExamId() {
        return this.checkExamId;
    }

    public void setCheckExamId(final String checkExamId) {
        this.checkExamId = checkExamId;
    }

    public String getCheckStatus() {
        return this.checkStatus;
    }

    public void setCheckStatus(final String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public List<String> getList() {
        return this.list;
    }

    public void setList(final List<String> list) {
        this.list = list;
    }

    public String getIsFinMark() {
        return this.isFinMark;
    }

    public void setIsFinMark(final String isFinMark) {
        this.isFinMark = isFinMark;
    }

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getUserExamId() {
        return this.userExamId;
    }

    public void setUserExamId(final String userExamId) {
        this.userExamId = userExamId;
    }

    public String getExamName() {
        return this.examName;
    }

    public void setExamName(final String examName) {
        this.examName = examName;
    }

    public String getExamType() {
        return this.examType;
    }

    public void setExamType(final String examType) {
        this.examType = examType;
    }

    public String getExamTime() {
        return this.examTime;
    }

    public void setExamTime(final String examTime) {
        this.examTime = examTime;
    }

    public String getOrganizedOrgCode() {
        return this.organizedOrgCode;
    }

    public void setOrganizedOrgCode(final String organizedOrgCode) {
        this.organizedOrgCode = organizedOrgCode;
    }
    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(final Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(final Date endTime) {
        this.endTime = endTime;
    }

    public String getExamStatus() {
        return this.examStatus;
    }

    public void setExamStatus(final String examStatus) {
        this.examStatus = examStatus;
    }

    public String getExamTypeName() {
        return this.examTypeName;
    }

    public void setExamTypeName(final String examTypeName) {
        this.examTypeName = examTypeName;
    }
}
