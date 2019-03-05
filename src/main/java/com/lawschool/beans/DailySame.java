package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.annotation.SysLog;
import com.lawschool.base.DataEntity;
import com.lawschool.form.AnswerForm;

import java.util.Date;
import java.util.List;

/**
 * 统一试题记录表
 */
@TableName("LAW_DAILY_SAME")
public class DailySame extends DataEntity<DailySame> {

    private String questionId;// 试题ID
    private String configureId;// 每日一题设置ID

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getConfigureId() {
        return configureId;
    }

    public void setConfigureId(String configureId) {
        this.configureId = configureId;
    }
}