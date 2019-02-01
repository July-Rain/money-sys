package com.lawschool.beans.exam;

import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;

/**
 * ClassName: CheckExam
 * Description: TODO
 * date: 2019/1/2814:56
 *
 * @author 王帅奇
 */
@TableName("LAW_CHECK_EXAM")
public class CheckExam extends DataEntity<CheckExam> {

    private String examConfigId;

    private String userExamId;

    private String checkPassword;

    private String checkUserCode;

    private String checkStatus; //阅卷状态  0 未开始  1 未完成  2 已完成

    private String checkExamUserId; //阅卷用户ID

    public String getCheckExamUserId() {
        return this.checkExamUserId;
    }

    public void setCheckExamUserId(final String checkExamUserId) {
        this.checkExamUserId = checkExamUserId;
    }

    public String getCheckStatus() {
        return this.checkStatus;
    }

    public void setCheckStatus(final String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getCheckUserCode() {
        return this.checkUserCode;
    }

    public void setCheckUserCode(final String checkUserCode) {
        this.checkUserCode = checkUserCode;
    }

    public String getExamConfigId() {
        return this.examConfigId;
    }

    public void setExamConfigId(final String examConfigId) {
        this.examConfigId = examConfigId;
    }

    public String getUserExamId() {
        return this.userExamId;
    }

    public void setUserExamId(final String userExamId) {
        this.userExamId = userExamId;
    }

    public String getCheckPassword() {
        return this.checkPassword;
    }

    public void setCheckPassword(final String checkPassword) {
        this.checkPassword = checkPassword;
    }
}
