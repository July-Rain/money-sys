package com.lawschool.beans.teacher;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawschool.base.DataEntity;
import com.lawschool.beans.accessory.AccessoryEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author MengyuWu
 * @Description 教官课程表
 * @Date 10:31 2019-2-28
 * @Param 
 * @return 
 **/

@TableName("LAW_STU_MEDIA_TCH")
public class StuMediaTch extends DataEntity<StuMediaTch> implements Serializable {
    private String id;//id

    private String stuCode;//课件编码

    private String stuTitle;//标题

    private String comContent;//内容

    @TableField(exist = false)
    private String contentUrl;//内容的url

    private String videoPicAcc;//视频主页图片展示

    @TableField(exist = false)
    private String videoPicAccUrl;//视频主页图片展示地址

    private String stuDescribe;//描述

    private String stuTime;//时长

    @Version
    @TableField(update = "%s+1")
    private Integer stuCount;//观看次数

    private Date stuCreat;//制作时间

    private String stuIssuer;//发布人

    private String stuIssdepartment;//发布单位

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date stuIsstime;//发布时间

    private String stuOptdepartment;//使用单位

    private String stuPoliceclass;//所属警种

    @TableField(exist = false)
    private String stuPoliceclassName;//所属警种

    private String stuFrom;//来源

    private String stuType;//课件类型

    private String stuKnowledge;//专项知识

    private String stuLawid;//专项知识ID

    @TableField(exist = false)
    private String collectionId;//收藏id

    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date collecttime;//收藏时间

    @TableField(exist = false)
    private String stuTypeValue;//课件类型

    @TableField(exist = false)
    private String[] deptArr;//适用部门

    @TableField(exist = false)
    private String[] userArr;//适用人员

    private String userName;//适用人员名称

    @TableField(exist = false)
    private String deptIds;//适用部门

    private String deptName;//适用部门名称

    @TableField(exist = false)
    private String userIds;//适用人员
    private String addsrc;//添加来源  0-默认-用户  1-教官中心

    private Integer delStatus;

    @TableField(exist = false)
    private String recordId;//记录id

    @TableField(exist = false)
    private boolean isColl;//是否被收藏过

    private String accessoryId;//附件id

    @TableField(exist = false)
    private List<AccessoryEntity> accessoryInfoList;//附件列表



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

    public String getDeptIds() {
        return deptIds;
    }

    public void setDeptIds(String deptIds) {
        this.deptIds = deptIds;
    }

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getStuCode() {
        return stuCode;
    }

    public void setStuCode(String stuCode) {
        this.stuCode = stuCode == null ? null : stuCode.trim();
    }

    public String getStuTitle() {
        return stuTitle;
    }

    public void setStuTitle(String stuTitle) {
        this.stuTitle = stuTitle == null ? null : stuTitle.trim();
    }

    public String getComContent() {
        return comContent;
    }

    public void setComContent(String comContent) {
        this.comContent = comContent == null ? null : comContent.trim();
    }

    public String getStuDescribe() {
        return stuDescribe;
    }

    public void setStuDescribe(String stuDescribe) {
        this.stuDescribe = stuDescribe == null ? null : stuDescribe.trim();
    }

    public String getStuTime() {
        return stuTime;
    }

    public void setStuTime(String stuTime) {
        this.stuTime = stuTime == null ? null : stuTime.trim();
    }

    public Integer getStuCount() {
        return stuCount;
    }

    public void setStuCount(Integer stuCount) {
        this.stuCount = stuCount;
    }

    public Date getStuCreat() {
        return stuCreat;
    }

    public void setStuCreat(Date stuCreat) {
        this.stuCreat = stuCreat;
    }

    public String getStuIssuer() {
        return stuIssuer;
    }

    public void setStuIssuer(String stuIssuer) {
        this.stuIssuer = stuIssuer == null ? null : stuIssuer.trim();
    }

    public String getStuIssdepartment() {
        return stuIssdepartment;
    }

    public void setStuIssdepartment(String stuIssdepartment) {
        this.stuIssdepartment = stuIssdepartment == null ? null : stuIssdepartment.trim();
    }

    public Date getStuIsstime() {
        return stuIsstime;
    }

    public void setStuIsstime(Date stuIsstime) {
        this.stuIsstime = stuIsstime;
    }


    public String getStuOptdepartment() {
        return stuOptdepartment;
    }

    public void setStuOptdepartment(String stuOptdepartment) {
        this.stuOptdepartment = stuOptdepartment == null ? null : stuOptdepartment.trim();
    }

    public String getStuPoliceclass() {
        return stuPoliceclass;
    }

    public void setStuPoliceclass(String stuPoliceclass) {
        this.stuPoliceclass = stuPoliceclass == null ? null : stuPoliceclass.trim();
    }

    public String getStuFrom() {
        return stuFrom;
    }

    public void setStuFrom(String stuFrom) {
        this.stuFrom = stuFrom == null ? null : stuFrom.trim();
    }

    public String getStuType() {
        return stuType;
    }

    public void setStuType(String stuType) {
        this.stuType = stuType == null ? null : stuType.trim();
    }

    public String getStuKnowledge() {
        return stuKnowledge;
    }

    public void setStuKnowledge(String stuKnowledge) {
        this.stuKnowledge = stuKnowledge == null ? null : stuKnowledge.trim();
    }

    public String getStuLawid() {
        return stuLawid;
    }

    public void setStuLawid(String stuLawid) {
        this.stuLawid = stuLawid == null ? null : stuLawid.trim();
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public Date getCollecttime() {
        return collecttime;
    }

    public void setCollecttime(Date collecttime) {
        this.collecttime = collecttime;
    }

    public String getStuTypeValue() {
        return stuTypeValue;
    }

    public void setStuTypeValue(String stuTypeValue) {
        this.stuTypeValue = stuTypeValue;
    }

    public String getAddsrc() {
        return addsrc;
    }

    public void setAddsrc(String addsrc) {
        this.addsrc = addsrc;
    }

    public Integer getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
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

    public String getStuPoliceclassName() {
        return stuPoliceclassName;
    }

    public void setStuPoliceclassName(String stuPoliceclassName) {
        this.stuPoliceclassName = stuPoliceclassName;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getVideoPicAcc() {
        return videoPicAcc;
    }

    public void setVideoPicAcc(String videoPicAcc) {
        this.videoPicAcc = videoPicAcc;
    }

    public String getVideoPicAccUrl() {
        return videoPicAccUrl;
    }

    public void setVideoPicAccUrl(String videoPicAccUrl) {
        this.videoPicAccUrl = videoPicAccUrl;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public boolean getIsColl() {
        return isColl;
    }

    public void setIsColl(boolean isColl) {
        this.isColl = isColl;
    }

    public String getAccessoryId() {
        return accessoryId;
    }

    public void setAccessoryId(String accessoryId) {
        this.accessoryId = accessoryId;
    }

    public List<AccessoryEntity> getAccessoryInfoList() {
        return accessoryInfoList;
    }

    public void setAccessoryInfoList(List<AccessoryEntity> accessoryInfoList) {
        this.accessoryInfoList = accessoryInfoList;
    }
}

