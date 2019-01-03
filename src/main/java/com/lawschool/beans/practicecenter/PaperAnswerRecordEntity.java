package com.lawschool.beans.practicecenter;

import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;

/**
 * @Auther: Moon
 * @Date: 2018/12/21 14:09
 * @Description: 练习任务答题记录
 */
@TableName("LAW_TASK_RECORD")
public class PaperAnswerRecordEntity extends DataEntity<PaperAnswerRecordEntity> {

    private String configureId;// 练习任务配置ID
    private String questionId;// 题目ID
    private String answerId;// 回答ID
    private Integer right;// 是否正确，0错误、1正确

    public String getConfigureId() {
        return configureId;
    }

    public void setConfigureId(String configureId) {
        this.configureId = configureId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public Integer getRight() {
        return right;
    }

    public void setRight(Integer right) {
        this.right = right;
    }

}
