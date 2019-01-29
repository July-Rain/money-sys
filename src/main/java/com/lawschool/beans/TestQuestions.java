package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @Descriptin  试题库
 * @author      zjw
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
@TableName("LAW_TESTQUESTIONS")
public class TestQuestions extends DataEntity<TestQuestions> {

    private String typeId;//类型  对应字典表
    private String comContent;//内容
    private String questionDifficulty;//难度   id  应该存code
    private String questionType;//题型  选择题，判断   应该存code
    private String answerChoiceNumber;//答案个数
    private String answerId;//正确答案的id
    private String specialKnowledgeId;//专项知识id   对应专项知识库
    private String policeClassification;//警种
    private String legalBasis;//法律依据
    private String answerDescrible;//答案描述

    private String releaseStatus;//发布状态
    private String stuIssuer;//发布人
    private String stuIssdepartment;//发布单位
    private Date stuIsstim;//发布时间
    private String stuOptdepartment;//使用单位

    private String useNumber;//使用次数
    private String rigthNumber;//正确次数
    private String isEnble;//启用禁用
    private String video;// 视频

    @TableField(exist = false)
    private String collectionId;//收藏id
    @TableField(exist = false)
    private String questiontypeValue;
    @TableField(exist = false)
    private String answerValue;
    @TableField(exist = false)
    private String questiondifficultyValue;
    @TableField(exist = false)
    private String specialknowledgeValue;

    @TableField(exist = false)
    private String typeValue;
    @TableField(exist = false)
    private List<Answer> answerList=new ArrayList<Answer>();

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
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

    public String getAnswerChoiceNumber() {
        return answerChoiceNumber;
    }

    public void setAnswerChoiceNumber(String answerChoiceNumber) {
        this.answerChoiceNumber = answerChoiceNumber;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getSpecialKnowledgeId() {
        return specialKnowledgeId;
    }

    public void setSpecialKnowledgeId(String specialKnowledgeId) {
        this.specialKnowledgeId = specialKnowledgeId;
    }

    public String getPoliceClassification() {
        return policeClassification;
    }

    public void setPoliceClassification(String policeClassification) {
        this.policeClassification = policeClassification;
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

    public String getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(String releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    public String getStuIssuer() {
        return stuIssuer;
    }

    public void setStuIssuer(String stuIssuer) {
        this.stuIssuer = stuIssuer;
    }

    public String getStuIssdepartment() {
        return stuIssdepartment;
    }

    public void setStuIssdepartment(String stuIssdepartment) {
        this.stuIssdepartment = stuIssdepartment;
    }

    public Date getStuIsstim() {
        return stuIsstim;
    }

    public void setStuIsstim(Date stuIsstim) {
        this.stuIsstim = stuIsstim;
    }

    public String getStuOptdepartment() {
        return stuOptdepartment;
    }

    public void setStuOptdepartment(String stuOptdepartment) {
        this.stuOptdepartment = stuOptdepartment;
    }

    public String getUseNumber() {
        return useNumber;
    }

    public void setUseNumber(String useNumber) {
        this.useNumber = useNumber;
    }

    public String getRigthNumber() {
        return rigthNumber;
    }

    public void setRigthNumber(String rigthNumber) {
        this.rigthNumber = rigthNumber;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getQuestiontypeValue() {
        return questiontypeValue;
    }

    public void setQuestiontypeValue(String questiontypeValue) {
        this.questiontypeValue = questiontypeValue;
    }

    public String getAnswerValue() {
        return answerValue;
    }

    public void setAnswerValue(String answerValue) {
        this.answerValue = answerValue;
    }

    public String getQuestiondifficultyValue() {
        return questiondifficultyValue;
    }

    public void setQuestiondifficultyValue(String questiondifficultyValue) {
        this.questiondifficultyValue = questiondifficultyValue;
    }

    public String getSpecialknowledgeValue() {
        return specialknowledgeValue;
    }

    public void setSpecialknowledgeValue(String specialknowledgeValue) {
        this.specialknowledgeValue = specialknowledgeValue;
    }

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }

    public String getIsEnble() {
        return isEnble;
    }

    public void setIsEnble(String isEnble) {
        this.isEnble = isEnble;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}