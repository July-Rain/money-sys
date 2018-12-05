package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 *
 * @Descriptin  练习关联表
 * @author      zjw
 * @version     v1.0
 * @Time        2018/12/04
 *
 */
@TableName("LAW_PRACTICE_RELEVANCE")
public class PracticeRelevance {
    @TableId
    private String id;

    private String practiceId;

    private String questionId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPracticeId() {
        return practiceId;
    }

    public void setPracticeId(String practiceId) {
        this.practiceId = practiceId == null ? null : practiceId.trim();
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId == null ? null : questionId.trim();
    }
}