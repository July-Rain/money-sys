package com.lawschool.beans.practicecenter;

import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;

import java.util.Date;

/**
 * 主题练习答题记录
 * @author xuxiang
 * @date 2018/12/4 9:51
 */
@TableName("law_theme_record")
public class ThemeAnswerRecordEntity extends DataEntity<ThemeAnswerRecordEntity> {
    private static final long serialVersionUID = 1L;

    /** 主题练习ID */
    private String themeId;

    /** 题库ID */
    private String questionId;

    /** 答案 */
    private String answer;

    /** 是否正确 0错误，1正确*/
    private Integer right;

    /** 答题时间 */
    private Date answerTime;

    /** 题目类型 */
    private String typeName;

    public String getThemeId() {
        return themeId;
    }

    public void setThemeId(String themeId) {
        this.themeId = themeId;
    }

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

    public Integer getRight() {
        return right;
    }

    public void setRight(Integer right) {
        this.right = right;
    }

    public Date getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(Date answerTime) {
        this.answerTime = answerTime;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "ThemeAnswerRecordEntity{" +
                "themeId='" + themeId + '\'' +
                ", questionId='" + questionId + '\'' +
                ", answer='" + answer + '\'' +
                ", right=" + right +
                ", answerTime=" + answerTime +
                ", typeName='" + typeName + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
