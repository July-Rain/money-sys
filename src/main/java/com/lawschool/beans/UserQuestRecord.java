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
    private Date opttime;
//我的答案
    private String myAswerId;



   //正确的的答案
    private String rightAnswerId;

    //难度
    private String  questionDifficulty;
    //题目类型
    private String   questionType;
    //知识点
    private String  specialKnowledgeId;


    //来源     闯关禁赛：Checkpoint   /     谁用到新的来源  更新下备注
    private String source;




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

    public Date getOpttime() {
        return opttime;
    }

    public void setOpttime(Date opttime) {
        this.opttime = opttime;
    }

    public String getMyAswerId() {
        return myAswerId;
    }

    public void setMyAswerId(String myAswerId) {
        this.myAswerId = myAswerId == null ? null : myAswerId.trim();
    }
}