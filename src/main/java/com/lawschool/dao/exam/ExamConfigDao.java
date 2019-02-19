package com.lawschool.dao.exam;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lawschool.base.AbstractDao;
import com.lawschool.beans.TestQuestions;
import com.lawschool.beans.exam.ExamConfig;
import com.lawschool.form.CheckSetForm;
import com.lawschool.form.QuestForm;
import com.lawschool.util.Query;
import org.apache.ibatis.annotations.Param;

public interface ExamConfigDao extends AbstractDao<ExamConfig>{

	 	int deleteByPrimaryKey(String id);

	    int insertSelective(ExamConfig record);

	    ExamConfig selectByPrimaryKey(String id);

	    int updateByPrimaryKeySelective(ExamConfig record);

	    int updateByPrimaryKey(ExamConfig record);

	    ExamConfig findByCheckPassword(@Param("checkPassword") String checkPassword,
									   @Param("checkUserType") String checkUserType);

	    List<ExamConfig> listConfig(ExamConfig record);
	    
	    List<ExamConfig> findByUser(Map<String, Object> param);

	    List<QuestForm> findByQueAndEdId(@Param("idList")List<String> idList,@Param("examDetailId") String examDetailId);

	    void checkset(CheckSetForm checkSetForm);

		CheckSetForm getCheckSetting(String id);
}