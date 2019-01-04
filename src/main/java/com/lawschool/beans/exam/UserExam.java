package com.lawschool.beans.exam;

import com.lawschool.base.DataEntity;

/**
 * ClassName: UserExam
 * Description: TODO
 * date: 2018/12/299:44
 *
 * @author 王帅奇
 */
public class UserExam extends DataEntity<UserExam> {

    private String examConfigId;    //考试配置id

    private String examDetailId;    //考试详情id

    private String userId;          //用户ID

    private Long queNum;    //题目数量

    private String examStatus;  //考试状态  （已完成/未完成）

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
}
