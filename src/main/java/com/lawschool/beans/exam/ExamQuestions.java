package com.lawschool.beans.exam;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;

/**
 * 
 * @Title:ExamQuestions.java
 * @Description: 考试试卷试题表    考试试题详情一对多
 * @author: 王帅奇
 * @date 2018年12月4日
 */
@TableName("LAW_EXAM_QUESTIONS")
public class ExamQuestions extends DataEntity<ExamQuestions> {

	   /**
	 * 
	 */
	private static final long serialVersionUID = -6346181933842540350L;


    /**
     * 考试配置主键
     */
    private String examConfigId;

    /**
     * 考试详情ID
     */
    private String examDetailId;

    /**
     * 题目ID
     */
    private String examLibraryId;

    /**
     * 是否必考
     */
    private String isMustTest;
    
    /**
     * 题目分值
     */
    private int score;

    /**
     * 考试配置主键
     * @return EXAM_CONFIG_ID 考试配置主键
     */
    public String getExamConfigId() {
        return examConfigId;
    }

    /**
     * 考试配置主键
     * @param examConfigId 考试配置主键
     */
    public void setExamConfigId(String examConfigId) {
        this.examConfigId = examConfigId == null ? null : examConfigId.trim();
    }

    /**
     * 考试详情ID
     * @return EXAM_DETAIL_ID 考试详情ID
     */
    public String getExamDetailId() {
        return examDetailId;
    }

    /**
     * 考试详情ID
     * @param examDetailId 考试详情ID
     */
    public void setExamDetailId(String examDetailId) {
        this.examDetailId = examDetailId == null ? null : examDetailId.trim();
    }

    /**
     * 题目ID
     * @return EXAM_LIBRARY_ID 题目ID
     */
    public String getExamLibraryId() {
        return examLibraryId;
    }

    /**
     * 题目ID
     * @param examLibraryId 题目ID
     */
    public void setExamLibraryId(String examLibraryId) {
        this.examLibraryId = examLibraryId == null ? null : examLibraryId.trim();
    }

    /**
     * 是否必考
     * @return IS_MUST_TEST 是否必考
     */
    public String getIsMustTest() {
        return isMustTest;
    }

    /**
     * 是否必考
     * @param isMustTest 是否必考
     */
    public void setIsMustTest(String isMustTest) {
        this.isMustTest = isMustTest == null ? null : isMustTest.trim();
    }

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}  
}
