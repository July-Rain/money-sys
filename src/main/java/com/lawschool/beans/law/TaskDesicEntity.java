package com.lawschool.beans.law;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * ClassName: TaskDesicEntity
 * Description: 学习任务节点数据
 * date: 2018-12-25 11:48
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
@TableName("law_tasks_desic")
public class TaskDesicEntity implements Serializable {
    private String id;//主键
    private String infoId;//节点code
    private String infoName;//父节点的id
    private String infoParentId;//节点名称
    private String taskId;//任务id
    private String infoCode;//节点code
    private Date createTime;//创建时间
    private String infoType;//类型标记
    @TableField(exist = false)
    private List<TaskDesicEntity> child;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public String getInfoName() {
        return infoName;
    }

    public void setInfoName(String infoName) {
        this.infoName = infoName;
    }

    public String getInfoParentId() {
        return infoParentId;
    }

    public void setInfoParentId(String infoParentId) {
        this.infoParentId = infoParentId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getInfoCode() {
        return infoCode;
    }

    public void setInfoCode(String infoCode) {
        this.infoCode = infoCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<TaskDesicEntity> getChild() {
        return child;
    }

    public void setChild(List<TaskDesicEntity> child) {
        this.child = child;
    }

    public String getInfoType() {
        return infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }
}
