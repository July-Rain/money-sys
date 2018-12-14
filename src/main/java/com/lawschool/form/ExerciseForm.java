package com.lawschool.form;

import java.util.Date;

/**
 * @version V1.0
 * @Description: 练习中心随机练习Form（后期练习中心统一使用此Form）
 * @author: 中石电子科技 徐祥
 * @date: 2018-12-13 10:01
 */
public class ExerciseForm {
    private String id;// 主键
    private Integer answerNum;// 答题数
    private Integer rightNum;// 答题正确数
    private Date exerciseDate;// 练习日期

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getAnswerNum() {
        return answerNum;
    }

    public void setAnswerNum(Integer answerNum) {
        this.answerNum = answerNum;
    }

    public Integer getRightNum() {
        return rightNum;
    }

    public void setRightNum(Integer rightNum) {
        this.rightNum = rightNum;
    }

    public Date getExerciseDate() {
        return exerciseDate;
    }

    public void setExerciseDate(Date exerciseDate) {
        this.exerciseDate = exerciseDate;
    }
}
