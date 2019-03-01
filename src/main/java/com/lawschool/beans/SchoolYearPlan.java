package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
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


    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
