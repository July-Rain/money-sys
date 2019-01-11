package com.lawschool.beans.practicecenter;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;

/**
 * @Auther: Moon
 * @Date: 2019/1/2 16:44
 * @Description: 联系任务个人任务
 */
@TableName("LAW_EXERCISE_TASK")
public class TaskExerciseEntity extends DataEntity<TaskExerciseEntity> {

    private static final long serialVersionUID = 5551711550838895820L;

    public static final Integer STATUS_ON = 0;// 进行中
    public static final Integer STATUS_OFF = 1;// 已完结

    private String taskId;// 任务配置ID
    private Integer answerNum;// 已答题数
    private Integer rightNum;// 回答正缺数
    private Integer status;// 任务状态，0进行中、1已完结
    private Integer total;// 总数

    @TableField(exist = false)
    private Integer delFlag = 0;
    @TableField(exist = false)
    private String users;
    @TableField(exist = false)
    private String depts;
    @TableField(exist = false)
    private String name;
    @TableField(exist = false)
    private String themeName;
    @TableField(exist = false)
    private String createName;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Integer getAnswerNum() {
        return answerNum;
    }

    public void setAnswerNum(Integer answerNum) {
        this.answerNum = answerNum;
    }

    public Integer getRightNum() {
        return rightNum;
    }

    public void setRightNum(Integer rightNum) {
        this.rightNum = rightNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public String getDepts() {
        return depts;
    }

    public void setDepts(String depts) {
        this.depts = depts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }
}
