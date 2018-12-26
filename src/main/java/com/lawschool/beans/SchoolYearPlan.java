package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;

import java.util.Date;

/**
 * @ClassName SchoolYearPlanDao
 * @Author wangtongye
 * @Date 2018/12/26 11:34
 * @Versiom 1.0
 **/
@TableName("law_school_year_plan")
public class SchoolYearPlan extends DataEntity<SchoolYearPlan> {
    private String planName;//计划名称
    private String planContent;//计划内容
    private Date startDate;//开始时间
    private Date endDate;//结束时间
    private String credit;//计划学分
    private String integral;//计划积分
    private String participantUser;//计划参与人员
    private String participantDept;//计划参与部门
    private String remarks;//备注

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanContent() {
        return planContent;
    }

    public void setPlanContent(String planContent) {
        this.planContent = planContent;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getParticipantUser() {
        return participantUser;
    }

    public void setParticipantUser(String participantUser) {
        this.participantUser = participantUser;
    }

    public String getParticipantDept() {
        return participantDept;
    }

    public void setParticipantDept(String participantDept) {
        this.participantDept = participantDept;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
