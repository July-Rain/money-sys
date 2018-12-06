package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;

import java.math.BigDecimal;
import java.util.Date;

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

    private String questionDifficulty;//难度   id

    private String questionType;//提醒  选择题，判断

    private Short answerChoiceNumber;//答案个数

    private String answerId;//正确答案的id

    private String specialKnowledgeId;//专项知识id   对应专项知识库

    private String policeClassification;//警种

    private String legalBasis;//法律依据

    private String answerDescrible;//答案描述

    private BigDecimal releaseStatus;//发布状态

    private BigDecimal disableStatus;//删除状态

    private String stuIssuer;//发布人

    private String stuIssdepartment;//发布单位

    private Date stuIsstim;//发布时间

    private String optuser;//操作人员

    private Date opttime;//操作时间

    private String stuOptdepartment;//使用单位

    private BigDecimal useNumber;//使用次数

    private BigDecimal rigthNumber;//正确次数

    @TableField(exist = false)
    private String collectId;//收藏id

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId == null ? null : typeId.trim();
    }

    public String getComContent() {
        return comContent;
    }

    public void setComContent(String comContent) {
        this.comContent = comContent == null ? null : comContent.trim();
    }

    public String getQuestionDifficulty() {
        return questionDifficulty;
    }

    public void setQuestionDifficulty(String questionDifficulty) {
        this.questionDifficulty = questionDifficulty == null ? null : questionDifficulty.trim();
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType == null ? null : questionType.trim();
    }

    public Short getAnswerChoiceNumber() {
        return answerChoiceNumber;
    }

    public void setAnswerChoiceNumber(Short answerChoiceNumber) {
        this.answerChoiceNumber = answerChoiceNumber;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId == null ? null : answerId.trim();
    }

    public String getSpecialKnowledgeId() {
        return specialKnowledgeId;
    }

    public void setSpecialKnowledgeId(String specialKnowledgeId) {
        this.specialKnowledgeId = specialKnowledgeId == null ? null : specialKnowledgeId.trim();
    }

    public String getPoliceClassification() {
        return policeClassification;
    }

    public void setPoliceClassification(String policeClassification) {
        this.policeClassification = policeClassification == null ? null : policeClassification.trim();
    }

    public String getLegalBasis() {
        return legalBasis;
    }

    public void setLegalBasis(String legalBasis) {
        this.legalBasis = legalBasis == null ? null : legalBasis.trim();
    }

    public String getAnswerDescrible() {
        return answerDescrible;
    }

    public void setAnswerDescrible(String answerDescrible) {
        this.answerDescrible = answerDescrible == null ? null : answerDescrible.trim();
    }

    public BigDecimal getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(BigDecimal releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    public BigDecimal getDisableStatus() {
        return disableStatus;
    }

    public void setDisableStatus(BigDecimal disableStatus) {
        this.disableStatus = disableStatus;
    }

    public String getStuIssuer() {
        return stuIssuer;
    }

    public void setStuIssuer(String stuIssuer) {
        this.stuIssuer = stuIssuer == null ? null : stuIssuer.trim();
    }

    public String getStuIssdepartment() {
        return stuIssdepartment;
    }

    public void setStuIssdepartment(String stuIssdepartment) {
        this.stuIssdepartment = stuIssdepartment == null ? null : stuIssdepartment.trim();
    }

    public Date getStuIsstim() {
        return stuIsstim;
    }

    public void setStuIsstim(Date stuIsstim) {
        this.stuIsstim = stuIsstim;
    }

    public String getOptuser() {
        return optuser;
    }

    public void setOptuser(String optuser) {
        this.optuser = optuser == null ? null : optuser.trim();
    }

    public Date getOpttime() {
        return opttime;
    }

    public void setOpttime(Date opttime) {
        this.opttime = opttime;
    }

    public String getStuOptdepartment() {
        return stuOptdepartment;
    }

    public void setStuOptdepartment(String stuOptdepartment) {
        this.stuOptdepartment = stuOptdepartment == null ? null : stuOptdepartment.trim();
    }

    public BigDecimal getUseNumber() {
        return useNumber;
    }

    public void setUseNumber(BigDecimal useNumber) {
        this.useNumber = useNumber;
    }

    public BigDecimal getRigthNumber() {
        return rigthNumber;
    }

    public void setRigthNumber(BigDecimal rigthNumber) {
        this.rigthNumber = rigthNumber;
    }

    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
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

    @Override
    public String toString() {
        return "TestQuestions{" +
                "id='" + id + '\'' +
                ", typeId='" + typeId + '\'' +
                ", comContent='" + comContent + '\'' +
                ", questionDifficulty='" + questionDifficulty + '\'' +
                ", questionType='" + questionType + '\'' +
                ", answerChoiceNumber=" + answerChoiceNumber +
                ", answerId='" + answerId + '\'' +
                ", specialKnowledgeId='" + specialKnowledgeId + '\'' +
                ", policeClassification='" + policeClassification + '\'' +
                ", legalBasis='" + legalBasis + '\'' +
                ", answerDescrible='" + answerDescrible + '\'' +
                ", releaseStatus=" + releaseStatus +
                ", disableStatus=" + disableStatus +
                ", stuIssuer='" + stuIssuer + '\'' +
                ", stuIssdepartment='" + stuIssdepartment + '\'' +
                ", stuIsstim=" + stuIsstim +
                ", optuser='" + optuser + '\'' +
                ", opttime=" + opttime +
                ", stuOptdepartment='" + stuOptdepartment + '\'' +
                ", useNumber=" + useNumber +
                ", rigthNumber=" + rigthNumber +
                ", collectId='" + collectId + '\'' +
                ", questiontypeValue='" + questiontypeValue + '\'' +
                ", answerValue='" + answerValue + '\'' +
                ", questiondifficultyValue='" + questiondifficultyValue + '\'' +
                ", specialknowledgeValue='" + specialknowledgeValue + '\'' +
                ", typeValue='" + typeValue + '\'' +
                '}';
    }
}