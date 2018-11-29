package com.lawschool.beans;

import java.io.Serializable;
import java.util.Date;

public class Msg implements Serializable{

    /**
     * 消息ID
     */
    private String id;

    @Override
    public String toString() {
        return "Msg{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", noticeType='" + noticeType + '\'' +
                ", content='" + content + '\'' +
                ", releaseDept='" + releaseDept + '\'' +
                ", releasePeople='" + releasePeople + '\'' +
                ", releaseDate=" + releaseDate +
                ", recieveDept='" + recieveDept + '\'' +
                ", recievePeople='" + recievePeople + '\'' +
                ", recieveDate=" + recieveDate +
                ", releaseState='" + releaseState + '\'' +
                ", backup1='" + backup1 + '\'' +
                ", backup2='" + backup2 + '\'' +
                ", backup3='" + backup3 + '\'' +
                ", backup4='" + backup4 + '\'' +
                ", backup5='" + backup5 + '\'' +
                '}';
    }

    /**
     * 消息标题
     */
    private String title;
    /**
     * 消息类型10008:系统消息，10009管理员消息
     */
    private String noticeType;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 发布单位
     */
    private String releaseDept;
    /**
     * 发布人
     */
    private String releasePeople;
    /**
     * 发布时间
     */
    private Date releaseDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReleaseDept() {
        return releaseDept;
    }

    public void setReleaseDept(String releaseDept) {
        this.releaseDept = releaseDept;
    }

    public String getReleasePeople() {
        return releasePeople;
    }

    public void setReleasePeople(String releasePeople) {
        this.releasePeople = releasePeople;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRecieveDept() {
        return recieveDept;
    }

    public void setRecieveDept(String recieveDept) {
        this.recieveDept = recieveDept;
    }

    public String getRecievePeople() {
        return recievePeople;
    }

    public void setRecievePeople(String recievePeople) {
        this.recievePeople = recievePeople;
    }

    public Date getRecieveDate() {
        return recieveDate;
    }

    public void setRecieveDate(Date recieveDate) {
        this.recieveDate = recieveDate;
    }

    public String getReleaseState() {
        return releaseState;
    }

    public void setReleaseState(String releaseState) {
        this.releaseState = releaseState;
    }

    public String getBackup1() {
        return backup1;
    }

    public void setBackup1(String backup1) {
        this.backup1 = backup1;
    }

    public String getBackup2() {
        return backup2;
    }

    public void setBackup2(String backup2) {
        this.backup2 = backup2;
    }

    public String getBackup3() {
        return backup3;
    }

    public void setBackup3(String backup3) {
        this.backup3 = backup3;
    }

    public String getBackup4() {
        return backup4;
    }

    public void setBackup4(String backup4) {
        this.backup4 = backup4;
    }

    public String getBackup5() {
        return backup5;
    }

    public void setBackup5(String backup5) {
        this.backup5 = backup5;
    }

    /**
     * 接收单位
     */
    private String recieveDept;
    /**
     * 接收人
     */
    private String recievePeople;
    /**
     * 接收日期
     */
    private Date recieveDate;
    /**
     * 消息发布状态 1：已发送，0：未发送
     */
    private String releaseState;
    /**
     *备用字段1
     */
    private String backup1;
    /**
     *备用字段2
     */
    private String backup2;
    /**
     *备用字段3
     */
    private String backup3;
    /**
     *备用字段4
     */
    private String backup4;
    /**
     *备用字段5
     */
    private String backup5;

}
