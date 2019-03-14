package com.lawschool.form;

/**
 * @Auther: Moon
 * @Date: 2019/3/8 15:44
 * @Description: 收藏展示Form
 */
public class CollectionForm {
    private String id;// 收藏ID
    private String contant;// 题目描述
    private String type;// 题目类型
    private String diff;// 题目难度
    private String topic;// 题目主题

    private String sourceId;// 资源ID
    private String classify;// 题目分类
    private String answers;// 用户答案

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContant() {
        return contant;
    }

    public void setContant(String contant) {
        this.contant = contant;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }
}
