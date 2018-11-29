package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@TableName("LAW_STU_MEDIA")
public class StuMedia {
    private String id;//id

    private String stuCode;//编码

    private String stuTitle;

    private String comContent;

    private String stuDescribe;

    private String stuTime;

    private BigDecimal stuCount;

    private Date stuCreat;

    private String stuIssuer;

    private String stuIssdepartment;

    private Date stuIsstime;

    private String optuser;//操作人

    private Date opttime;//操作时间

    private String stuOptdepartment;//操作部门

    private String stuPoliceclass;

    private String stuFrom;

    private String stuType;//课件类型

    private String stuKnowledge;

    private String stuLawid;

    @TableField(exist = false)
    private String collectId;//收藏id

    @TableField(exist = false)
    private Date collecttime;//收藏时间

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

    public BigDecimal getStuCount() {
        return stuCount;
    }

    public void setStuCount(BigDecimal stuCount) {
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
}