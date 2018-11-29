package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;
import java.util.Date;

@TableName("LAW_TESTQUESTIONS")
public class TestQuestions {
    @TableId
    private String id;

    private String typeId;

    private String comContent;

    private String questionDifficulty;

    private String questionType;

    private Short answerChoiceNumber;

    private String answerId;

    private String specialKnowledgeId;

    private String policeClassification;

    private String legalBasis;

    private String answerDescrible;

    private BigDecimal releaseStatus;

    private BigDecimal disableStatus;

    private String stuIssuer;

    private String stuIssdepartment;

    private Date stuIsstim;

    private String optuser;

    private Date opttime;

    private String stuOptdepartment;

    private BigDecimal useNumber;

    private BigDecimal rigthNumber;

    private String collectId;

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
}