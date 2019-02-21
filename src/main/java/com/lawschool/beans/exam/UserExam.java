package com.lawschool.beans.exam;

import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;
import org.springframework.context.annotation.Bean;

/**
 * ClassName: UserExam
 * Description: TODO
 * date: 2018/12/299:44
 *
 * @author 王帅奇
 */
@TableName("law_user_exam")
public class UserExam extends DataEntity<UserExam> {

    private String examConfigId;    //考试配置id

    private String examDetailId;    //考试详情id

    private String userId;          //用户ID

    private Long queNum;    //题目数量

    private String examStatus;  //考试状态  （已完成/未完成） 0：已考 1：未考 2：已完成

    private double score;   //考試分數

    private String isFinMark;   //是否完成阅卷  0:完成 1：未完成 2待审核

    private String orgId;  //部门ID

    private Double remainingExamTime; //剩余时间

    private String checkStatus; //阅卷状态  0 未开始  1 未完成  2 已完成

    public String getCheckStatus() {
        return this.checkStatus;
    }

    public void setCheckStatus(final String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getExamConfigId() {
        return this.examConfigId;
    }

    public void setExamConfigId(final String examConfigId) {
        this.examConfigId = examConfigId;
    }

    public String getExamDetailId() {
        return this.examDetailId;
    }

    public void setExamDetailId(final String examDetailId) {
        this.examDetailId = examDetailId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public Long getQueNum() {
        return this.queNum;
    }

    public void setQueNum(final Long queNum) {
        this.queNum = queNum;
    }

    public String getExamStatus() {
        return this.examStatus;
    }

    public void setExamStatus(final String examStatus) {
        this.examStatus = examStatus;
    }

    public double getScore() {
        return this.score;
    }

    public void setScore(final double score) {
        this.score = score;
    }

    public String getIsFinMark() {
        return this.isFinMark;
    }

    public void setIsFinMark(final String isFinMark) {
        this.isFinMark = isFinMark;
    }

    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(final String orgId) {
        this.orgId = orgId;
    }

    public Double getRemainingExamTime() {
        return this.remainingExamTime;
    }

    public void setRemainingExamTime(final Double remainingExamTime) {
        this.remainingExamTime = remainingExamTime;
    }
}
