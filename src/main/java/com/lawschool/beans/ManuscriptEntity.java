package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;

/**
 * @ClassName ManuscriptDao
 * @Author wangtongye
 * @Date 2018/12/26 19:07
 * @Versiom 1.0
 **/
@TableName("LAW_MANUSCRIPT")
public class ManuscriptEntity extends DataEntity<ManuscriptEntity> {

    private static final long serialVersionUID = 3559417694371003546L;

    public static final Integer FILE_TYPE_QUE = 0;// 题库
    public static final Integer FILE_TYPE_STU = 1;// 学习
    public static final Integer STATUS_WAIT = 0;// 待审核
    public static final Integer STATUS_PASS = 1;// 审核通过
    public static final Integer STATUS_FAIL = 2;// 审核失败

    private Integer type;// 投稿类型，0题库、1学习
    private String sourceId;// 来源ID
    private Integer status;// 审核状态
    private String auditor;// 审核人
    private String opinion;// 审核意见
    private String author;// 作者
    private String orgCode;// 作者部门

    @TableField(exist = false)
    private TestQuestions test;
    @TableField(exist = false)
    private StuMedia stu;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public TestQuestions getTest() {
        return test;
    }

    public void setTest(TestQuestions test) {
        this.test = test;
    }

    public StuMedia getStu() {
        return stu;
    }

    public void setStu(StuMedia stu) {
        this.stu = stu;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
}
