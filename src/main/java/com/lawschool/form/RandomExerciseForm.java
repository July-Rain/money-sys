package com.lawschool.form;

import java.util.Date;

/**
 * @version V1.0
 * @Description: 随机练习提交Form
 * @author: 中石电子科技 徐祥
 * @date: 2018-12-07 15:42
 */
public class RandomExerciseForm {
    private String difficulty;// 试题难度
    private String classify;// 试题分类
    private String type;// 试题类型
    private String topic;// 专项知识库ID

    private String userId;// 用户

    private String id;
    private Date date;

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
