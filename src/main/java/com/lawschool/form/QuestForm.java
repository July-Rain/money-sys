package com.lawschool.form;

import java.util.List;

/**
 * @version V1.0
 * @Description: 获取题目信息接口
 * @author: 中石电子科技 徐祥
 * @date: 2018-12-11 10:33
 */
public class QuestForm {
    private String id;// 题目ID
    private String comContent;// 内容
    private String questionDifficulty;// 难度ID
    private String questionType;// 题型
    private String answerId;// 答案
    private String legalBasis;// 法律依据
    private String answerDescrible;// 答案描述
    private List<AnswerForm> answer;//选项

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComContent() {
        return comContent;
    }

    public void setComContent(String comContent) {
        this.comContent = comContent;
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

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getLegalBasis() {
        return legalBasis;
    }

    public void setLegalBasis(String legalBasis) {
        this.legalBasis = legalBasis;
    }

    public String getAnswerDescrible() {
        return answerDescrible;
    }

    public void setAnswerDescrible(String answerDescrible) {
        this.answerDescrible = answerDescrible;
    }

    public List<AnswerForm> getAnswer() {
        return answer;
    }

    public void setAnswer(List<AnswerForm> answer) {
        this.answer = answer;
    }
}
