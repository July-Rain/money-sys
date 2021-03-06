package com.lawschool.form;

/**
 * @version V1.0
 * @Description: 题目答案Form
 * @author: 中石电子科技 徐祥
 * @date: 2018-12-11 10:46
 */
public class AnswerForm {
    private String id;// 答案 ID
    private String questionContent;// 答案内容
    private String questionId;// 题目ID

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    @Override
    public String toString() {
        return "AnswerForm{" +
                "id='" + id + '\'' +
                ", questionContent='" + questionContent + '\'' +
                ", questionId='" + questionId + '\'' +
                '}';
    }
}
