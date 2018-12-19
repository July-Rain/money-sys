package com.lawschool.beans.learn;

import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawschool.base.DataEntity;

import java.util.Date;

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
}
