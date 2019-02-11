package com.lawschool.form;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
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
    private Integer answerChoiceNumber;// 选项数量

    private String questionId;//题目Id
    private String userAnswer;// 用户答案
    private Integer right;// 用户回答是否正确
    private String themeName;// 主題类型

    private Integer score;  //题目分值

    private List<String> checkList = new ArrayList<>();

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

    public Integer getAnswerChoiceNumber() {
        return answerChoiceNumber;
    }

    public void setAnswerChoiceNumber(Integer answerChoiceNumber) {
        this.answerChoiceNumber = answerChoiceNumber;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public Integer getRight() {
        return right;
    }

    public void setRight(Integer right) {
        this.right = right;
    }

    public List<String> getCheckList() {

        if(StringUtils.isNotBlank(this.userAnswer)){
            checkList = Arrays.asList(this.userAnswer.split(","));
        }
        return checkList;
    }

    public void setCheckList(List<String> checkList) {
        this.checkList = checkList;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public Integer getScore() {
        return this.score;
    }

    public void setScore(final Integer score) {
        this.score = score;
    }

    public String getQuestionId() {
        return this.questionId;
    }

    public void setQuestionId(final String questionId) {
        this.questionId = questionId;
    }
}
