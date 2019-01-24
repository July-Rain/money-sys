package com.lawschool.beans.diagnosis;

import java.io.Serializable;

/**
 * ClassName: OrgDiagnosisEntity
 * Description: 部门统计数据
 * date: 2019-1-9 16:59
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
public class OrgDiagnosisEntity implements Serializable {

    private String orgId; //部门id
    private String orgName; //部门名称
    private String orgLevel; //部门级别
    private String parentId;//上级部门id
    private int lawAllCount; //全部法律法规
    private int videoAllCount;//全部视频数据
    private int picAllCount;//全部图文数据
    private int audioAllCount;//全部音频数据
    private int lawTaskCount;//学习任务法律法规
    private int videoTaskCount;//学习任务视频
    private int picTaskCount;//学习任务图文
    private int audioTaskCount;//学习任务音频数据
    private int videoCaseCount;//案例分析视频
    private int picCaseCount;//案例分析图文
    private int audioCaseCount;//案例分析音频数据
    private int normalExamCount; //普通考试
    private int enforceExamCount;//执法考试
    private int examScore1Count;//60分以下
    private int examScore2Count;//60-70
    private int examScore3Count;//70-80
    private int examScore4Count;//80-90
    private int examScore5Count;//90以上
    private String userId;
    private String userName;


    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgLevel() {
        return orgLevel;
    }

    public void setOrgLevel(String orgLevel) {
        this.orgLevel = orgLevel;
    }

    public int getLawAllCount() {
        return lawAllCount;
    }

    public void setLawAllCount(int lawAllCount) {
        this.lawAllCount = lawAllCount;
    }

    public int getVideoAllCount() {
        return videoAllCount;
    }

    public void setVideoAllCount(int videoAllCount) {
        this.videoAllCount = videoAllCount;
    }

    public int getPicAllCount() {
        return picAllCount;
    }

    public void setPicAllCount(int picAllCount) {
        this.picAllCount = picAllCount;
    }

    public int getAudioAllCount() {
        return audioAllCount;
    }

    public void setAudioAllCount(int audioAllCount) {
        this.audioAllCount = audioAllCount;
    }

    public int getLawTaskCount() {
        return lawTaskCount;
    }

    public void setLawTaskCount(int lawTaskCount) {
        this.lawTaskCount = lawTaskCount;
    }

    public int getVideoTaskCount() {
        return videoTaskCount;
    }

    public void setVideoTaskCount(int videoTaskCount) {
        this.videoTaskCount = videoTaskCount;
    }

    public int getPicTaskCount() {
        return picTaskCount;
    }

    public void setPicTaskCount(int picTaskCount) {
        this.picTaskCount = picTaskCount;
    }

    public int getAudioTaskCount() {
        return audioTaskCount;
    }

    public void setAudioTaskCount(int audioTaskCount) {
        this.audioTaskCount = audioTaskCount;
    }

    public int getVideoCaseCount() {
        return videoCaseCount;
    }

    public void setVideoCaseCount(int videoCaseCount) {
        this.videoCaseCount = videoCaseCount;
    }

    public int getPicCaseCount() {
        return picCaseCount;
    }

    public void setPicCaseCount(int picCaseCount) {
        this.picCaseCount = picCaseCount;
    }

    public int getAudioCaseCount() {
        return audioCaseCount;
    }

    public void setAudioCaseCount(int audioCaseCount) {
        this.audioCaseCount = audioCaseCount;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getNormalExamCount() {
        return normalExamCount;
    }

    public void setNormalExamCount(int normalExamCount) {
        this.normalExamCount = normalExamCount;
    }

    public int getEnforceExamCount() {
        return enforceExamCount;
    }

    public void setEnforceExamCount(int enforceExamCount) {
        this.enforceExamCount = enforceExamCount;
    }

    public int getExamScore1Count() {
        return examScore1Count;
    }

    public void setExamScore1Count(int examScore1Count) {
        this.examScore1Count = examScore1Count;
    }

    public int getExamScore2Count() {
        return examScore2Count;
    }

    public void setExamScore2Count(int examScore2Count) {
        this.examScore2Count = examScore2Count;
    }

    public int getExamScore3Count() {
        return examScore3Count;
    }

    public void setExamScore3Count(int examScore3Count) {
        this.examScore3Count = examScore3Count;
    }

    public int getExamScore4Count() {
        return examScore4Count;
    }

    public void setExamScore4Count(int examScore4Count) {
        this.examScore4Count = examScore4Count;
    }

    public int getExamScore5Count() {
        return examScore5Count;
    }

    public void setExamScore5Count(int examScore5Count) {
        this.examScore5Count = examScore5Count;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
