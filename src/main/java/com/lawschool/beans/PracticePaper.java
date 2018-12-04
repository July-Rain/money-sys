package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;
import java.util.Date;
/**
 *
 * @Descriptin  练习表
 * @author      zjw
 * @version     v1.0
 * @Time        2018/12/04
 *
 */
@TableName("LAW_PRACTICE_PAPER")
public class PracticePaper {
    private String id;

    private String practiceName;

    private String pracCreatUser;

    private Date pracCreatTime;

    private String pracCreatDepartment;

    private String optuser;

    private Date opttime;

    private String stuKnowledge;

    private BigDecimal count;

    private String pracPaperType;

    private String lawPracConfigId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPracticeName() {
        return practiceName;
    }

    public void setPracticeName(String practiceName) {
        this.practiceName = practiceName == null ? null : practiceName.trim();
    }

    public String getPracCreatUser() {
        return pracCreatUser;
    }

    public void setPracCreatUser(String pracCreatUser) {
        this.pracCreatUser = pracCreatUser == null ? null : pracCreatUser.trim();
    }

    public Date getPracCreatTime() {
        return pracCreatTime;
    }

    public void setPracCreatTime(Date pracCreatTime) {
        this.pracCreatTime = pracCreatTime;
    }

    public String getPracCreatDepartment() {
        return pracCreatDepartment;
    }

    public void setPracCreatDepartment(String pracCreatDepartment) {
        this.pracCreatDepartment = pracCreatDepartment == null ? null : pracCreatDepartment.trim();
    }

    public String getOptuser() {
        return optuser;
    }

    public void setOptuser(String optuser) {
        this.optuser = optuser == null ? null : optuser.trim();
    }

    public Date getOpttime() {
        return opttime;
    }

    public void setOpttime(Date opttime) {
        this.opttime = opttime;
    }

    public String getStuKnowledge() {
        return stuKnowledge;
    }

    public void setStuKnowledge(String stuKnowledge) {
        this.stuKnowledge = stuKnowledge == null ? null : stuKnowledge.trim();
    }

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }

    public String getPracPaperType() {
        return pracPaperType;
    }

    public void setPracPaperType(String pracPaperType) {
        this.pracPaperType = pracPaperType == null ? null : pracPaperType.trim();
    }

    public String getLawPracConfigId() {
        return lawPracConfigId;
    }

    public void setLawPracConfigId(String lawPracConfigId) {
        this.lawPracConfigId = lawPracConfigId == null ? null : lawPracConfigId.trim();
    }
}