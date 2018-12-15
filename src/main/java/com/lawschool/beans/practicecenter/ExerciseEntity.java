package com.lawschool.beans.practicecenter;

import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;

/**
 * @version V1.0
 * @Description: 练习任务Entity
 * @author: 中石电子科技 徐祥
 * @date: 2018-12-07 15:24
 */
@TableName("law_exercise_random")
public class ExerciseEntity extends DataEntity<ExerciseEntity>{
    private static final long serialVersionUID = 1L;

    private Integer answerNum;// 总答题数
    private Integer rightNum;// 正确答题数

    private String difficulty;// 试题难度
    private String classify;// 试题分类
    private String type;// 试题类型
    private String topicType;// 主题类型

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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTopicType() {
        return topicType;
    }

    public void setTopicType(String topicType) {
        this.topicType = topicType;
    }
}
