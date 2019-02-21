package com.lawschool.form;

import com.lawschool.beans.TestQuestions;
import com.lawschool.beans.exam.ExamQueConfig;

import java.util.List;

/**
 * ClassName: ExamConfigForm
 * Description: TODO
 * date: 2019/2/2014:51
 *
 * @author 王帅奇
 */
public class ExamConfigForm {
    private String id; // 主键
    private float sinMultScore; //自主出题 单选每题分值
    private float mulMultScore; //多选每题分值
    private float judgeMultScore;   //判断每题分值
    private List<TestQuestions> sinMultipleSelection;   //单选
    private List<TestQuestions> mulMultipleSelection;   //多选
    private List<TestQuestions> judgeMultipleSelection;   //判断
    private List<TestQuestions> subMultipleSelection;   //自主
    private List<ExamQueConfig> examQueConfigList;

    public List<ExamQueConfig> getExamQueConfigList() {
        return this.examQueConfigList;
    }

    public void setExamQueConfigList(final List<ExamQueConfig> examQueConfigList) {
        this.examQueConfigList = examQueConfigList;
    }

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public float getSinMultScore() {
        return this.sinMultScore;
    }

    public void setSinMultScore(final float sinMultScore) {
        this.sinMultScore = sinMultScore;
    }

    public float getMulMultScore() {
        return this.mulMultScore;
    }

    public void setMulMultScore(final float mulMultScore) {
        this.mulMultScore = mulMultScore;
    }

    public float getJudgeMultScore() {
        return this.judgeMultScore;
    }

    public void setJudgeMultScore(final float judgeMultScore) {
        this.judgeMultScore = judgeMultScore;
    }

    public List<TestQuestions> getSinMultipleSelection() {
        return this.sinMultipleSelection;
    }

    public void setSinMultipleSelection(final List<TestQuestions> sinMultipleSelection) {
        this.sinMultipleSelection = sinMultipleSelection;
    }

    public List<TestQuestions> getMulMultipleSelection() {
        return this.mulMultipleSelection;
    }

    public void setMulMultipleSelection(final List<TestQuestions> mulMultipleSelection) {
        this.mulMultipleSelection = mulMultipleSelection;
    }

    public List<TestQuestions> getJudgeMultipleSelection() {
        return this.judgeMultipleSelection;
    }

    public void setJudgeMultipleSelection(final List<TestQuestions> judgeMultipleSelection) {
        this.judgeMultipleSelection = judgeMultipleSelection;
    }

    public List<TestQuestions> getSubMultipleSelection() {
        return this.subMultipleSelection;
    }

    public void setSubMultipleSelection(final List<TestQuestions> subMultipleSelection) {
        this.subMultipleSelection = subMultipleSelection;
    }
}
