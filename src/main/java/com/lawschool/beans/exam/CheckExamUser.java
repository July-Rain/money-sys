package com.lawschool.beans.exam;

import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;

/**
 * ClassName: CheckExamUser
 * Description: 阅卷人员信息表
 * date: 2019/1/2315:42
 *
 * @author 王帅奇
 */
@TableName("LAW_CHECK_EXAM_USER")
public class CheckExamUser extends DataEntity<CheckExamUser> {

    private String examUserCode; //用户证件号

    private String examUserName; //阅卷人姓名

    private String examConfigId; //考试编号

    private String checkPassword;   //阅卷口令

    private String examUserSex;     //阅卷人性别

    private String checkUserType;   //阅卷人类别 0 普通阅卷人、1 审核人员

    public String getExamUserCode() {
        return this.examUserCode;
    }

    public void setExamUserCode(final String examUserCode) {
        this.examUserCode = examUserCode;
    }

    public String getExamConfigId() {
        return this.examConfigId;
    }

    public void setExamConfigId(final String examConfigId) {
        this.examConfigId = examConfigId;
    }

    public String getCheckPassword() {
        return this.checkPassword;
    }

    public void setCheckPassword(final String checkPassword) {
        this.checkPassword = checkPassword;
    }

    public String getExamUserSex() {
        return this.examUserSex;
    }

    public void setExamUserSex(final String examUserSex) {
        this.examUserSex = examUserSex;
    }

    public String getCheckUserType() {
        return this.checkUserType;
    }

    public void setCheckUserType(final String checkUserType) {
        this.checkUserType = checkUserType;
    }

    public String getExamUserName() {
        return this.examUserName;
    }

    public void setExamUserName(final String examUserName) {
        this.examUserName = examUserName;
    }
}
