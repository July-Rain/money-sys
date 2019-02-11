package com.lawschool.form;

import java.math.BigDecimal;

/**
 * ClassName: CheckSetForm
 * Description: 阅卷设置Form
 * date: 2019/1/239:34
 *
 * @author 王帅奇
 */
public class CheckSetForm {

    private String id; //考试配置ID

    /**
     * 阅卷人数
     */
    private BigDecimal checkNum;

    /**
     * 阅卷口令
     */
    private String checkPassword;

    /**
     * 阅卷分差值
     */
    private Short checkScoreDifference;

    /**
     * 是否仲裁 0是 1否
     */
    private String isAibitration;

    /**
     * 仲裁人code
     */
    private String aibitrationUserCode;

    /**
     * 每套试卷阅卷人数（1,或者2）
     */
    private Integer paperPerSetNum;

    /**
     * 审核人员口令
     */
    private String auditCheckPass;

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public BigDecimal getCheckNum() {
        return this.checkNum;
    }

    public void setCheckNum(final BigDecimal checkNum) {
        this.checkNum = checkNum;
    }

    public String getCheckPassword() {
        return this.checkPassword;
    }

    public void setCheckPassword(final String checkPassword) {
        this.checkPassword = checkPassword;
    }

    public Short getCheckScoreDifference() {
        return this.checkScoreDifference;
    }

    public void setCheckScoreDifference(final Short checkScoreDifference) {
        this.checkScoreDifference = checkScoreDifference;
    }

    public String getIsAibitration() {
        return this.isAibitration;
    }

    public void setIsAibitration(final String isAibitration) {
        this.isAibitration = isAibitration;
    }

    public String getAibitrationUserCode() {
        return this.aibitrationUserCode;
    }

    public void setAibitrationUserCode(final String aibitrationUserCode) {
        this.aibitrationUserCode = aibitrationUserCode;
    }

    public Integer getPaperPerSetNum() {
        return this.paperPerSetNum;
    }

    public void setPaperPerSetNum(final Integer paperPerSetNum) {
        this.paperPerSetNum = paperPerSetNum;
    }

    public String getAuditCheckPass() {
        return this.auditCheckPass;
    }

    public void setAuditCheckPass(final String auditCheckPass) {
        this.auditCheckPass = auditCheckPass;
    }
}
