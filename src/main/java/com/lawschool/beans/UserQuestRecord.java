package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;

@TableName("LAW_USER_QUESTION_RECORD")
public class UserQuestRecord {
    @TableId
    private String id;
//用户id
    private String userId;
//题目id
    private String questId;
//操作时间
    private Date optTime;
//我的答案
    private String myAswerId;

   //正确的的答案
    private String rightAnswerId;

    //难度  code
    private String  questionDifficulty;
    //题目类型 code
    private String   questionType;
    //知识点   id
    private String  specialKnowledgeId;


    //来源     闯关禁赛：Checkpoint   /  在线比武：OnlinPk /打擂台   leitai//teamOnline组队   OnlinPkByCode  code码      每日一题  everyDay  谁用到新的来源  更新下备注
    private String source;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQuestId() {
        return questId;
    }

    public void setQuestId(String questId) {
        this.questId = questId;
    }

    public Date getOptTime() {
        return optTime;
    }

    public void setOptTime(Date optTime) {
        this.optTime = optTime;
    }

    public String getMyAswerId() {
        return myAswerId;
    }

    public void setMyAswerId(String myAswerId) {
        this.myAswerId = myAswerId;
    }

    public String getRightAnswerId() {
        return rightAnswerId;
    }

    public void setRightAnswerId(String rightAnswerId) {
        this.rightAnswerId = rightAnswerId;
    }

    public String getQuestionDifficulty() {
        return questionDifficulty;
    }

    public void setQuestionDifficulty(String questionDifficulty) {
        this.questionDifficulty = questionDifficulty;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getSpecialKnowledgeId() {
        return specialKnowledgeId;
    }

    public void setSpecialKnowledgeId(String specialKnowledgeId) {
        this.specialKnowledgeId = specialKnowledgeId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}