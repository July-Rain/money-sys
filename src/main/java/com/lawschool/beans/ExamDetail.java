package com.lawschool.beans;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableName;
/**
 * 
 * @Title:ExamDetail.java
 * @Description: 试卷详情  试卷配置1对多
 * @author: 王帅奇
 * @date 2018年12月4日
 */
@TableName("LAW_EXAM_DETAIL")
public class ExamDetail {
	 /**
     * null
     */
    private String id;

    /**
     * 考试配置主键
     */
    private String examConfigId;

    /**
     * 添加人
     */
    private String addUser;

    /**
     * 添加时间
     */
    private Date addTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * null
     * @return id null
     */
    public String getId() {
        return id;
    }

    /**
     * null
     * @param id null
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 考试配置主键
     * @return EXAM_CONFIG_ID 考试配置主键
     */
    public String getExamConfigId() {
        return examConfigId;
    }

    /**
     * 考试配置主键
     * @param examConfigId 考试配置主键
     */
    public void setExamConfigId(String examConfigId) {
        this.examConfigId = examConfigId == null ? null : examConfigId.trim();
    }

    /**
     * 添加人
     * @return ADD_USER 添加人
     */
    public String getAddUser() {
        return addUser;
    }

    /**
     * 添加人
     * @param addUser 添加人
     */
    public void setAddUser(String addUser) {
        this.addUser = addUser == null ? null : addUser.trim();
    }

    /**
     * 添加时间
     * @return ADD_TIME 添加时间
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * 添加时间
     * @param addTime 添加时间
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * 修改人
     * @return UPDATE_USER 修改人
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 修改人
     * @param updateUser 修改人
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    /**
     * 修改时间
     * @return UPDATE_TIME 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 修改时间
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
