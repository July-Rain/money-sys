package com.lawschool.beans.law;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.ejb.TransactionAttribute;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author MengyuWu
 * @Description 案例分析的实体
 * @Date 15:16 2018-12-22
 * @Param 
 * @return 
 **/
@TableName("law_case_analysis")
public class CaseAnalysisEntity implements Serializable {
    private String id;

    private String caseCode;

    private String caseTitle;

    private String caseContent;

    private String caseDescribe;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date caseTime;

    private String optUser;

    private Date optTime;

    private String caseArea;

    private String caseProcess;

    private String caseType;

    private String lawLevel;

    private String caseLawid;

    private String delStatus;

    private String createUser;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createTime;

    private String createUserName; //创建人名称

    private String videoPicAcc;

    private String userName;

    private String deptName;

    private String contentType;//资料类型

    @Version
    @TableField(update = "%s+1")
    private Integer contentCount;//查看量统计

    @TableField(exist = false)
    private String[] deptArr;//适用部门

    @TableField(exist = false)
    private String[] userArr;//适用人员

    @TableField(exist = false)
    private String caseContentUrl;//内容路径

    @TableField(exist = false)
    private String videoPicAccUrl;//内容路径

    @TableField(exist = false)
    private String recordId;//记录id

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getVideoPicAccUrl() {
        return videoPicAccUrl;
    }

    public void setVideoPicAccUrl(String videoPicAccUrl) {
        this.videoPicAccUrl = videoPicAccUrl;
    }

    public String getCaseContentUrl() {
        return caseContentUrl;
    }

    public void setCaseContentUrl(String caseContentUrl) {
        this.caseContentUrl = caseContentUrl;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode == null ? null : caseCode.trim();
    }

    public String getCaseTitle() {
        return caseTitle;
    }

    public void setCaseTitle(String caseTitle) {
        this.caseTitle = caseTitle == null ? null : caseTitle.trim();
    }

    public String getCaseContent() {
        return caseContent;
    }

    public void setCaseContent(String caseContent) {
        this.caseContent = caseContent == null ? null : caseContent.trim();
    }

    public String getCaseDescribe() {
        return caseDescribe;
    }

    public void setCaseDescribe(String caseDescribe) {
        this.caseDescribe = caseDescribe == null ? null : caseDescribe.trim();
    }

    public Date getCaseTime() {
        return caseTime;
    }

    public void setCaseTime(Date caseTime) {
        this.caseTime = caseTime;
    }

    public String getOptUser() {
        return optUser;
    }

    public void setOptUser(String optUser) {
        this.optUser = optUser == null ? null : optUser.trim();
    }

    public Date getOptTime() {
        return optTime;
    }

    public void setOptTime(Date optTime) {
        this.optTime = optTime;
    }

    public String getCaseArea() {
        return caseArea;
    }

    public void setCaseArea(String caseArea) {
        this.caseArea = caseArea == null ? null : caseArea.trim();
    }

    public String getCaseProcess() {
        return caseProcess;
    }

    public void setCaseProcess(String caseProcess) {
        this.caseProcess = caseProcess == null ? null : caseProcess.trim();
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType == null ? null : caseType.trim();
    }

    public String getLawLevel() {
        return lawLevel;
    }

    public void setLawLevel(String lawLevel) {
        this.lawLevel = lawLevel == null ? null : lawLevel.trim();
    }

    public String getCaseLawid() {
        return caseLawid;
    }

    public void setCaseLawid(String caseLawid) {
        this.caseLawid = caseLawid == null ? null : caseLawid.trim();
    }

    public String getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(String delStatus) {
        this.delStatus = delStatus == null ? null : delStatus.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getVideoPicAcc() {
        return videoPicAcc;
    }

    public void setVideoPicAcc(String videoPicAcc) {
        this.videoPicAcc = videoPicAcc == null ? null : videoPicAcc.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }

    public Integer getContentCount() {
        return contentCount;
    }

    public void setContentCount(Integer contentCount) {
        this.contentCount = contentCount;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }
}