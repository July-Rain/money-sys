package com.lawschool.form;

/**
 * @version V1.0
 * @Description: 随机练习提交Form
 * @author: 中石电子科技 徐祥
 * @date: 2018-12-07 15:42
 */
public class RandomExerciseForm {
    private String questionDifficulty;// 试题难度
    private String typeId;// 试题分类
    private String questionType;// 试题类型
    private String specialKnowledgeId;// 专项知识库ID

    private String userId;// 用户ID

    public String getQuestionDifficulty() {
        return questionDifficulty;
    }

    public void setQuestionDifficulty(String questionDifficulty) {
        this.questionDifficulty = questionDifficulty;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getSpecialKnowledgeId() {
        return specialKnowledgeId;
    }

    public void setSpecialKnowledgeId(String specialKnowledgeId) {
        this.specialKnowledgeId = specialKnowledgeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
