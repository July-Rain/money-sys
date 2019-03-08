package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * @Auther: Moon
 * @Date: 2018/12/20 15:07
 * @Description: 练习配置Entity
 */
@TableName("LAW_EXERCISE_CONFIGURE")
public class ExerciseConfigureEntity extends DataEntity<ExerciseConfigureEntity> {

    public static final String FROM_PER_TASK="0";
    public static final String FROM_MANGE_TASK="1";
    public static final String FROM_KEY_QUE="2";
    public static final String FROM_WRONG_QUE="3";

    private String prefix;// 前缀
    private String name;// 练习卷名称,自动生成
    private String rangeType;// 使用范围
    private Integer total;// 总题目数
    private String depts;// 使用部门

    private String users;// 使用人员
    private String type;// 练习卷类型
    private String questions;// 生成题目IDs
    private String userName;// 创建人员，冗余便于展示
    private Integer delFlag;// 0未删除、1删除

    private String userNames;
    private String deptNames;

    private String sourceFrom;  //来源 默认 ‘CONFIG’还有‘KEYQUE’--重点试题组卷  ‘WRONGQUE’--错题组卷

    @TableField(exist = false)
    private List<String> deptIds;
    @TableField(exist = false)
    private List<String> userIds;

    @TableField(exist = false)
    private List<ExerciseConditionEntity> list;
    @TableField(exist = false)
    private Integer status;// 练习任务状态
    @TableField(exist = false)
    private String taskId;// 用户练习任务Id
    @TableField(exist = false)
    private Integer answerNum;// 答题数

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRangeType() {
        return rangeType;
    }

    public void setRangeType(String rangeType) {
        this.rangeType = rangeType;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getDepts() {
        return depts;
    }

    public void setDepts(String depts) {
        this.depts = depts;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<ExerciseConditionEntity> getList() {
        return list;
    }

    public void setList(List<ExerciseConditionEntity> list) {
        this.list = list;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

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

    public List<String> getDeptIds() {
        return deptIds;
    }

    public void setDeptIds(List<String> deptIds) {
        this.deptIds = deptIds;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public String getUserNames() {
        return userNames;
    }

    public void setUserNames(String userNames) {
        this.userNames = userNames;
    }

    public String getDeptNames() {
        return deptNames;
    }

    public void setDeptNames(String deptNames) {
        this.deptNames = deptNames;
    }

    public String getSourceFrom() {
        return sourceFrom;
    }

    public void setSourceFrom(String sourceFrom) {
        this.sourceFrom = sourceFrom;
    }
}
