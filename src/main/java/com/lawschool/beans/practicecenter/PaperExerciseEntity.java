package com.lawschool.beans.practicecenter;

import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;

/**
 * @Auther: Moon
 * @Date: 2018/12/21 13:59
 * @Description: 练习任务
 */
@TableName("LAW_EXERCISE_PAPER")
public class PaperExerciseEntity extends DataEntity<PaperExerciseEntity> {

    public static final Integer STATUS_ON = 0;// 练习任务状态，进行中
    public static final Integer STATUS_OFF = 1;// 练习任务状态，已完结

    private String configureId;// 练习配置ID
    private String userId;// 用户ID
    private Integer status;// 练习状态，0进行中、1已完结

    private Integer answerNum;// 已答题数

    public String getConfigureId() {
        return configureId;
    }

    public void setConfigureId(String configureId) {
        this.configureId = configureId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAnswerNum() {
        return answerNum;
    }

    public void setAnswerNum(Integer answerNum) {
        this.answerNum = answerNum;
    }
}
