package com.lawschool.beans.personalCenter;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;

/**
 * @Auther: Moon
 * @Date: 2019/3/8 14:28
 * @Description: 收藏
 */
@TableName("LAW_PERSONAL_COLLECTION")
public class CollectionEntity extends DataEntity<CollectionEntity> {

    private static final long serialVersionUID = -2097206667363438609L;

    public static final Integer VITAL_QUESTION = 0;// 重点试题
    public static final Integer VITAL_COURSE = 1;// 重点课程
    public static final Integer ERROR_QUESTION =  2;// 我的错题

    private Integer type;// 类型，对应上面的静态常量
    private String sourceId;// 资源ID
    private String answers;// 错题答案

    @TableField(exist = false)
    private String specialKnowledgeId;// 主题
    @TableField(exist = false)
    private String questionDifficulty;// 难度
    @TableField(exist = false)
    private String questionType;// 类型

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSpecialKnowledgeId() {
        return specialKnowledgeId;
    }

    public void setSpecialKnowledgeId(String specialKnowledgeId) {
        this.specialKnowledgeId = specialKnowledgeId;
    }

    public String getQuestionDifficulty() {
        return questionDifficulty;
    }

    public void setQuestionDifficulty(String questionDifficulty) {
        this.questionDifficulty = questionDifficulty;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }
}
