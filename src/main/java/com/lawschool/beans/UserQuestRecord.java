package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;

@TableName("LAW_USER_QUESTION_RECORD")
public class UserQuestRecord {
    @TableId
    private String id;

    private String userId;

    private String questId;

    private Date optTime;

    private String myAswerId;

    private String  questionDifficulty;

    private String questionType;

    private String specialKnowledgeId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getQuestId() {
        return questId;
    }

    public void setQuestId(String questId) {
        this.questId = questId == null ? null : questId.trim();
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
        this.myAswerId = myAswerId == null ? null : myAswerId.trim();
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
}