package com.lawschool.beans.learn;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawschool.base.DataEntity;
import com.lawschool.beans.law.TaskDesicEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ClassName: LearnTasksEntity
 * Description: 学习任务
 * date: 2018-12-18 15:46
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
@TableName("law_learn_tasks")
public class LearnTasksEntity extends DataEntity<LearnTasksEntity> {
    private static final long serialVersionUID = 1L;

    private String taskName;//任务名称

    private String taskContent;//任务内容

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startTime;//开始时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endTime;//结束时间

    private String taskExecute;//任务执行者

    private String deptName;//适用部门名称

    private String userName;//适用人员名称

    @TableField(exist = false)
    private String[] deptArr;//适用部门

    @TableField(exist = false)
    private String[] userArr;//适用人员


    @TableField(exist = false)
    private String deptIds;//适用部门


    @TableField(exist = false)
    private String userIds;//适用人员
    @TableField(exist = false)
    private int allCount;//任务中所有课程数量
    @TableField(exist = false)
    private int finishCount;//完成课程数量

    private String policeclass;//所属警种
    @TableField(exist = false)
    private String policeclassName;//所属警种的名称

    private String taskClass;//所属任务分类
    @TableField(exist = false)
    private String taskClassName;//所属任务分类名称

    private String deptCode;//所属部门code

    private int countUser; //任务下人员的个数

    private int countData;//任务节点下数据的个数

    private String isUse;//是否使用

    @TableField(exist = false)
    private List<TaskDesicEntity> taskContentList=new ArrayList<TaskDesicEntity>();//法律法规内容arr

    public List<TaskDesicEntity> getTaskContentList() {
        return taskContentList;
    }

    public void setTaskContentList(List<TaskDesicEntity> taskContentList) {
        this.taskContentList = taskContentList;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getTaskExecute() {
        return taskExecute;
    }

    public void setTaskExecute(String taskExecute) {
        this.taskExecute = taskExecute;
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
    public int getAllCount() {
        return allCount;
    }

    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }

    public int getFinishCount() {
        return finishCount;
    }

    public void setFinishCount(int finishCount) {
        this.finishCount = finishCount;
    }

    public String getPoliceclass() {
        return policeclass;
    }

    public void setPoliceclass(String policeclass) {
        this.policeclass = policeclass;
    }

    public String getPoliceclassName() {
        return policeclassName;
    }

    public void setPoliceclassName(String policeclassName) {
        this.policeclassName = policeclassName;
    }

    public String getTaskClass() {
        return taskClass;
    }

    public void setTaskClass(String taskClass) {
        this.taskClass = taskClass;
    }

    public String getTaskClassName() {
        return taskClassName;
    }

    public void setTaskClassName(String taskClassName) {
        this.taskClassName = taskClassName;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public int getCountUser() {
        return countUser;
    }

    public void setCountUser(int countUser) {
        this.countUser = countUser;
    }

    public int getCountData() {
        return countData;
    }

    public void setCountData(int countData) {
        this.countData = countData;
    }

    public String getIsUse() {
        return isUse;
    }

    public void setIsUse(String isUse) {
        this.isUse = isUse;
    }
}
