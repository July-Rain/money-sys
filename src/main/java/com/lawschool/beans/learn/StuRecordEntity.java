package com.lawschool.beans.learn;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * ClassName: StuRecordEntity
 * Description: 学习记录表
 * date: 2018-12-28 20:39
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
@TableName("law_stu_record")
public class StuRecordEntity implements Serializable {
    private String id;
    private String stuId;
    private String userId;
    private String userName;
    //学习类型(stu_video stu_audio stu_pic case_video case_audio case_pic law)
    private String stuType;
    private String createUser;
    private Date createTime;
    private Date stuTime;
    //学习来源学习来源 （视频中心videocen 音频中心 audiocen 学习任务 learntask 案例分析 caseana）
    private String stuFrom;

    private String taskId;//学习任务id

    private String deptId;//部门id

    @Version
    @TableField(update = "%s+1")
    private Integer stuCount;//学习次数

    private BigDecimal stuCountTime;//学习总时长

    public BigDecimal getStuCountTime() {
        return stuCountTime;
    }

    public void setStuCountTime(BigDecimal stuCountTime) {
        this.stuCountTime = stuCountTime;
    }

    public Integer getStuCount() {
        return stuCount;
    }

    public void setStuCount(Integer stuCount) {
        this.stuCount = stuCount;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getStuFrom() {
        return stuFrom;
    }

    public void setStuFrom(String stuFrom) {
        this.stuFrom = stuFrom;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStuType() {
        return stuType;
    }

    public void setStuType(String stuType) {
        this.stuType = stuType;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getStuTime() {
        return stuTime;
    }

    public void setStuTime(Date stuTime) {
        this.stuTime = stuTime;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }
}
