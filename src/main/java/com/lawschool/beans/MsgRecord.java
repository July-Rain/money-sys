package com.lawschool.beans;

import com.lawschool.base.DataEntity;

import java.util.Date;

public class MsgRecord extends DataEntity<MsgRecord> {

    /**
     * 标题记录
     */
    private String title;

    /**
     * 消息类型记录
     */
    private String noticeType;

    /**
     * 消息内容记录
     */
    private String content;

    /**
     * 发布单位记录
     */
    private String releaseDept;

    /**
     * 发布人
     */
    private String releasePeople;

    /**
     * 接收人
     */
    private String recievePeople;

    /**
     * 接收日期
     */
    private Date recieveDate;

    /**
     * 发布状态
     */
    private String releaseState;

    /**
     * 读取状态 已读、未读
     */
    private String readState;

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

    public String getReadState() {
        return readState;
    }

    public void setReadState(String readState) {
        this.readState = readState;
    }

    @Override
    public String toString() {
        return "MsgRecord{" +
                "title='" + title + '\'' +
                ", noticeType='" + noticeType + '\'' +
                ", content='" + content + '\'' +
                ", releaseDept='" + releaseDept + '\'' +
                ", releasePeople='" + releasePeople + '\'' +
                ", recievePeople='" + recievePeople + '\'' +
                ", recieveDate=" + recieveDate +
                ", releaseState='" + releaseState + '\'' +
                ", readState='" + readState + '\'' +
                '}';
    }
}
