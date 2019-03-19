package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawschool.base.Page;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
@TableName("LAW_SYS_MSG_WITHIN_STATION")
public class Msg implements Serializable{

    /**
     * 消息ID
     */
    @TableId
    private String id;
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
     * 发布单位(ID)
     */
    private String releaseDept;


    private String deptName;

    private String deptIds;
    /**
     * 接收人名称(userName)
     */
    @TableField(exist = false)
    private String userName;

    /**
     * 接收单位（数组）
     */
    @TableField(exist = false)
    private String[] deptArr;

    /**
     * 接收人（数组）
     */
    @TableField(exist = false)
    private String[] userArr;

    /**
     * 发布人
     */
    private String releasePeople;
    /**
     * 发布人中文
     */
    @TableField(exist = false)
    private String releasePeopleName;
    /**
     * 发布时间
     */
    @TableField(value="RELEASE_DATE",strategy= FieldStrategy.IGNORED)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date releaseDate;
    /**
     * 接收单位
     */
    private String recieveDept;
    /**
     * 接收人
     */
    private String recievePeople;

    private String recievePeopleNmae;



    /**
     * 创建日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date recieveDate;
    /**
     * 消息发布状态 1：已发送，0：未发送
     */
    private String releaseState;
    /**
     *备用字段1
     */
    @TableField(exist = false)
    private String backup1;
    /**
     *备用字段2
     */
    @TableField(exist = false)
    private String backup2;
    /**
     *备用字段3
     */
    @TableField(exist = false)
    private String backup3;
    /**
     *备用字段4
     */
    @TableField(exist = false)
    private String backup4;
    /**
     *备用字段5
     */
    @TableField(exist = false)
    private String backup5;


    public String getDeptIds() {
        return deptIds;
    }

    public void setDeptIds(String deptIds) {
        this.deptIds = deptIds;
    }

    public String getRecievePeopleNmae() {
        return recievePeopleNmae;
    }

    public void setRecievePeopleNmae(String recievePeopleNmae) {
        this.recievePeopleNmae = recievePeopleNmae;
    }

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

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getReleasePeopleName() {
        return releasePeopleName;
    }

    public void setReleasePeopleName(String releasePeopleName) {
        this.releasePeopleName = releasePeopleName;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", noticeType='" + noticeType + '\'' +
                ", content='" + content + '\'' +
                ", releaseDept='" + releaseDept + '\'' +
                ", deptName='" + deptName + '\'' +
                ", userName='" + userName + '\'' +
                ", deptArr=" + Arrays.toString(deptArr) +
                ", userArr=" + Arrays.toString(userArr) +
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
}
