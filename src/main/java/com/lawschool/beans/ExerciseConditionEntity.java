package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;

/**
 * @Auther: Moon
 * @Date: 2018/12/20 15:37
 * @Description: 练习配置条件表
 */
@TableName("LAW_EXERCISE_CONDITION")
public class ExerciseConditionEntity extends DataEntity<ExerciseConditionEntity> {

    private String topicId;// 主题ID
    private String topicName;// 主题名称
    private Integer primaryNum;// 初级题量
    private Integer middleNum;// 中级题量
    private Integer seniorNum;// 高级题量

    private String conId;// 练习的配置Id

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public Integer getPrimaryNum() {
        return primaryNum;
    }

    public void setPrimaryNum(Integer primaryNum) {
        this.primaryNum = primaryNum;
    }

    public Integer getMiddleNum() {
        return middleNum;
    }

    public void setMiddleNum(Integer middleNum) {
        this.middleNum = middleNum;
    }

    public Integer getSeniorNum() {
        return seniorNum;
    }

    public void setSeniorNum(Integer seniorNum) {
        this.seniorNum = seniorNum;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }
}
