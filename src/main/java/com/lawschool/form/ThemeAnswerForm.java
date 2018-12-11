package com.lawschool.form;

/**
 * 主题练习提交From
 */
public class ThemeAnswerForm {
    private String qId;// 题目ID
    private String answer;// 回答
    private Integer right;// 回答是否正确
    private String typeName;// 题目类型

    // ↓结果分析时用
    private Integer errorNum;// 回答错误数
    private Integer rightNum;// 回答正确数

    public String getqId() {
        return qId;
    }

    public void setqId(String qId) {
        this.qId = qId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getRight() {
        return right;
    }

    public void setRight(Integer right) {
        this.right = right;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getErrorNum() {
        return errorNum;
    }

    public void setErrorNum(Integer errorNum) {
        this.errorNum = errorNum;
    }

    public Integer getRightNum() {
        return rightNum;
    }

    public void setRightNum(Integer rightNum) {
        this.rightNum = rightNum;
    }
}
