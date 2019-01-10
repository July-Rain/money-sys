package com.lawschool.beans.exam;

import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;

/**
 * ClassName: UserExamAnswer
 * Description:用户考试答题表
 * date: 2019/1/1011:58
 *
 * @author 王帅奇
 */
@TableName("LAW_USER_EXAM_ANSWER")
public class UserExamAnswer extends DataEntity<UserExamAnswer> {

    private String userExamId;//已考试卷ID

    private String examDetailId;//考试详情ID

    private String testQueId;//试题ID

    private String rightAnsId;//正确答案ID

    private String userAnsId;//用户ID

    private String testQueType;//题目类型

    private String answerIdOrder; //答案ID顺序拼接

    private int orderNum;//排序

    public String getUserExamId() {
        return this.userExamId;
    }

    public void setUserExamId(final String userExamId) {
        this.userExamId = userExamId;
    }

    public String getExamDetailId() {
        return this.examDetailId;
    }

    public void setExamDetailId(final String examDetailId) {
        this.examDetailId = examDetailId;
    }

    public String getTestQueId() {
        return this.testQueId;
    }

    public void setTestQueId(final String testQueId) {
        this.testQueId = testQueId;
    }

    public String getRightAnsId() {
        return this.rightAnsId;
    }

    public void setRightAnsId(final String rightAnsId) {
        this.rightAnsId = rightAnsId;
    }

    public String getUserAnsId() {
        return this.userAnsId;
    }

    public void setUserAnsId(final String userAnsId) {
        this.userAnsId = userAnsId;
    }

    public String getTestQueType() {
        return this.testQueType;
    }

    public void setTestQueType(final String testQueType) {
        this.testQueType = testQueType;
    }

    public int getOrderNum() {
        return this.orderNum;
    }

    public void setOrderNum(final int orderNum) {
        this.orderNum = orderNum;
    }

    public String getAnswerIdOrder() {
        return this.answerIdOrder;
    }

    public void setAnswerIdOrder(final String answerIdOrder) {
        this.answerIdOrder = answerIdOrder;
    }
}
