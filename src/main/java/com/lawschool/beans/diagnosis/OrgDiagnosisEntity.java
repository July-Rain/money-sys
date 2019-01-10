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
}
