package com.lawschool.form;

import java.util.List;

/**
 * @Auther: Moon
 * @Date: 2019/3/12 19:46
 * @Description: 题目信息，如需扩展，请继承此类，请勿在此类增减属性
 */
public class QuestionForm {

    private String recordId;// 记录ID
    private String themeName;// 主题类型

    private String questId;// 题目ID
    private String content;// 试题秒速
    private String difficulty;// 试题难度
    private String type;// 题型
    private String answer;// 正确答案

    private String describe;// 答案提示
    private String userAnswerStr;// 用户答案（单选 OR 判断）
    private List<String> userAnswerList;// 用户答案（多选）
    private Integer isCollect;// 是否收藏
    private List<AnswerForm> options;// 选项list

    private String userAnswer;

    public String getQuestId() {
        return questId;
    }

    public void setQuestId(String questId) {
        this.questId = questId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getUserAnswerStr() {
        return userAnswerStr;
    }

    public void setUserAnswerStr(String userAnswerStr) {
        this.userAnswerStr = userAnswerStr;
    }

    public List<String> getUserAnswerList() {
        return userAnswerList;
    }

    public void setUserAnswerList(List<String> userAnswerList) {
        this.userAnswerList = userAnswerList;
    }

    public Integer getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(Integer isCollect) {
        this.isCollect = isCollect;
    }

    public List<AnswerForm> getOptions() {
        return options;
    }

    public void setOptions(List<AnswerForm> options) {
        this.options = options;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }
}
