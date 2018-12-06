package com.lawschool.dao.exam;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lawschool.beans.exam.ExamConfig;

public interface ExamConfigDao extends BaseMapper<ExamConfig>{

	 	int deleteByPrimaryKey(String id);

	    int insertSelective(ExamConfig record);

	    ExamConfig selectByPrimaryKey(String id);

	    int updateByPrimaryKeySelective(ExamConfig record);

	    int updateByPrimaryKey(ExamConfig record);

	    ExamConfig selectByCode(String code);

	    List<ExamConfig> listConfig(ExamConfig record);

}