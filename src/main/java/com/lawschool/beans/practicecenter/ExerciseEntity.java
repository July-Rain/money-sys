package com.lawschool.beans.practicecenter;

import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;

/**
 * @version V1.0
 * @Description: 练习任务Entity
 * @author: 中石电子科技 徐祥
 * @date: 2018-12-07 15:24
 */
@TableName("law_exercise")
public class ExerciseEntity extends DataEntity<ExerciseEntity>{
    private static final long serialVersionUID = 1L;

    private Integer answerNum;// 总答题数
    private Integer rightNum;// 正确答题数

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
}
