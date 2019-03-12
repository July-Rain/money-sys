package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawschool.base.DataEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author liuhuan
 */
@TableName("LAW_DAILY_QUESTION")
public class DailyQuestionConfiguration extends DataEntity<DailyQuestionConfiguration>{

    private String ruleName;// 每日一题规则名称
    private String specialKnowledgeId;// 专项知识点
    private String createRule;// 出题规则 1:统一试题.0:随机不同
    private String obtainPoint;// 获得积分

    private String createWay;// 出题方式（2: 随机、1: 自定义）
    private String isShowAnswer;// 是否显示答案（是：1，否：0）
    private String questionDifficulty;// 试题难度（初级：10001，中级：10002，高级：10003）
    private Date beginTime;// 每日一题规则生效时间（包含）
    private String questionType;// 试题类型

    private String creatUserName;//创建人中文姓名
    private Integer delFlag;// 删除标志，0未删除、1删除

    @TableField(exist = false)
    private String status;// 状态

    @TableField(exist = false)
    private String[] diffcs; // 试题难度，多选

    @TableField(exist = false)
    private String[] topics;// 主题，多选

    public String[] getTopics() {
        return topics;
    }

    public void setTopics(String[] topics) {
        this.topics = topics;
    }

    public String[] getDiffcs() {
        return diffcs;
    }

    public void setDiffcs(String[] diffcs) {
        this.diffcs = diffcs;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getCreatUserName() {
        return creatUserName;
    }

    public void setCreatUserName(String creatUserName) {
        this.creatUserName = creatUserName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpecialKnowledgeId() {
        return specialKnowledgeId;
    }

    public void setSpecialKnowledgeId(String specialKnowledgeId) {
        this.specialKnowledgeId = specialKnowledgeId;
    }

    public String getQuestionDifficulty() {
        return questionDifficulty;
    }

    public void setQuestionDifficulty(String questionDifficulty) {
        this.questionDifficulty = questionDifficulty;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getCreateRule() {
        return createRule;
    }

    public void setCreateRule(String createRule) {
        this.createRule = createRule;
    }

    public String getObtainPoint() {
        return obtainPoint;
    }

    public void setObtainPoint(String obtainPoint) {
        this.obtainPoint = obtainPoint;
    }

    public String getCreateWay() {
        return createWay;
    }

    public void setCreateWay(String createWay) {
        this.createWay = createWay;
    }

    public String getIsShowAnswer() {
        return isShowAnswer;
    }

    public void setIsShowAnswer(String isShowAnswer) {
        this.isShowAnswer = isShowAnswer;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
