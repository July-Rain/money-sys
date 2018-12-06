package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.Date;

@TableName("LAW_PRACTICE_PAPER")

public class PracticePaper implements Serializable {
    /**
     * 练习卷ID
     */
    @TableId
    private String id;

    /**
     * 练习卷名称
     */
    private String practiceName;

    /**
     * 练习卷创建人
     */
    private String pracCreatUser;

    /**
     * 练习卷创建时间
     */
    private Date pracCreatTime;

    /**
     * 练习卷创建部门
     */
    private String pracCreatDepartment;

    /**
     * 操作人
     */
    private String optuser;

    /**
     * 操作时间
     */
    private Date opttime;

    /**
     *知识点
     */
    private String stuKnowledge;

    /**
     * 题目数量
     */
    private Integer count;

    /**
     * 练习卷类型
     * @return
     */
    private String pracPaperType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPracticeName() {
        return practiceName;
    }

    public void setPracticeName(String practiceName) {
        this.practiceName = practiceName;
    }

    public String getPracCreatUser() {
        return pracCreatUser;
    }

    public void setPracCreatUser(String pracCreatUser) {
        this.pracCreatUser = pracCreatUser;
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
        this.pracCreatDepartment = pracCreatDepartment;
    }

    public String getOptuser() {
        return optuser;
    }

    public void setOptuser(String optuser) {
        this.optuser = optuser;
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
        this.stuKnowledge = stuKnowledge;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getPracPaperType() {
        return pracPaperType;
    }

    public void setPracPaperType(String pracPaperType) {
        this.pracPaperType = pracPaperType;
    }

    @Override
    public String toString() {
        return "PracticePaper{" +
                "id='" + id + '\'' +
                ", practiceName='" + practiceName + '\'' +
                ", pracCreatUser='" + pracCreatUser + '\'' +
                ", pracCreatTime=" + pracCreatTime +
                ", pracCreatDepartment='" + pracCreatDepartment + '\'' +
                ", optuser='" + optuser + '\'' +
                ", opttime=" + opttime +
                ", stuKnowledge='" + stuKnowledge + '\'' +
                ", count=" + count +
                ", pracPaperType='" + pracPaperType + '\'' +
                '}';
    }
}
