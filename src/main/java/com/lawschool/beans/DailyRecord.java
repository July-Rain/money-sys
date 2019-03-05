package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;
import com.lawschool.form.AnswerForm;

import java.util.List;

/**
 * @Auther: Moon
 * @Date: 2019/1/16 19:04 重写
 * @Description: 每日一题答题记录
 */
@TableName("LAW_DAILY_RECORD")
public class DailyRecord extends DataEntity<DailyRecord> {

    private static final long serialVersionUID = 5993512128918943902L;

    private String questionId;// 题目ID
    private String answer;// 用户选项ID，对选逗号分隔
    private String userId;// 用户ID
    private Integer right;// 是否正确,0错误、1正确
    private Integer isCollect;// 是否收藏，0为收藏、1收藏

    private String themeName;// 主题名称，用于统计

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getRight() {
        return right;
    }

    public void setRight(Integer right) {
        this.right = right;
    }

    public Integer getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(Integer isCollect) {
        this.isCollect = isCollect;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }
}
