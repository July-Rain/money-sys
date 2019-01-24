package com.lawschool.beans.diagnosis;

import java.io.Serializable;

/**
 * ClassName: StuDiagnosisEntity
 * Description: TODO
 * date: 2019-1-24 14:40
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
public class StuDiagnosisEntity implements Serializable {
    private String userId;
    private String userName;
    private double videoTime;
    private double audioTime;
    private double taskTime;
    private double caseTime;
    private int videoNum;
    private int audioNum;
    private int picNum;
    private int lawNum;

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

    public double getVideoTime() {
        return videoTime;
    }

    public void setVideoTime(double videoTime) {
        this.videoTime = videoTime;
    }

    public double getAudioTime() {
        return audioTime;
    }

    public void setAudioTime(double audioTime) {
        this.audioTime = audioTime;
    }

    public double getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(double taskTime) {
        this.taskTime = taskTime;
    }

    public double getCaseTime() {
        return caseTime;
    }

    public void setCaseTime(double caseTime) {
        this.caseTime = caseTime;
    }

    public int getVideoNum() {
        return videoNum;
    }

    public void setVideoNum(int videoNum) {
        this.videoNum = videoNum;
    }

    public int getAudioNum() {
        return audioNum;
    }

    public void setAudioNum(int audioNum) {
        this.audioNum = audioNum;
    }

    public int getPicNum() {
        return picNum;
    }

    public void setPicNum(int picNum) {
        this.picNum = picNum;
    }

    public int getLawNum() {
        return lawNum;
    }

    public void setLawNum(int lawNum) {
        this.lawNum = lawNum;
    }
}
