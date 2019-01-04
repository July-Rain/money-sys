package com.lawschool.beans.exam;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;

/**
 * 
 * @Title:ExamDetail.java
 * @Description: 试卷详情  试卷配置1对多
 * @author: 王帅奇
 * @date 2018年12月4日
 */
@TableName("LAW_EXAM_DETAIL")
public class ExamDetail extends DataEntity<ExamDetail> {
	 /**
	 * 
	 */
	private static final long serialVersionUID = -4659408534905593975L;


    /**
     * 考试配置主键
     */
    private String examConfigId;


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
}
