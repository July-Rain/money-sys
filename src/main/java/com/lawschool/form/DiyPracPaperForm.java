package com.lawschool.form;

import com.lawschool.beans.Answer;

import java.util.List;

/**
 * 接受自定义组卷中每一题的返回值
 */
public class DiyPracPaperForm {
    private String id;//题目ID
    private String comContent;//题目内容
    private String questionDifficulty;//难度
    private String questionType;//题目类型
    private String answerId;//答案
    private String legalBasis;//法律依据
    private String answerDescrible; //答案描述
    private List<Answer> answer;//选项
    private String answerChoiceNumber;//选项数量

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

    public List<Answer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Answer> answer) {
        this.answer = answer;
    }

    public String getAnswerChoiceNumber() {
        return answerChoiceNumber;
    }

    public void setAnswerChoiceNumber(String answerChoiceNumber) {
        this.answerChoiceNumber = answerChoiceNumber;
    }
}
