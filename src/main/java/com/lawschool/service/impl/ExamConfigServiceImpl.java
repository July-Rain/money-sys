package com.lawschool.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.beans.ExamConfig;
import com.lawschool.dao.ExamConfigDao;
import com.lawschool.service.ExamConfigService;

public class ExamConfigServiceImpl implements ExamConfigService{

	@Autowired
	private ExamConfigDao examConfigDao;
	
	public int insertConfig(ExamConfig config) {
		config.setId(IdWorker.getId()+"");
		return examConfigDao.insert(config);
	}

	public int updateConfig(ExamConfig config) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ExamConfig> listConfig(ExamConfig config) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteConfig(ExamConfig examConfig) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ExamConfig queryConfig(ExamConfig examConfig) {
		// TODO Auto-generated method stub
		return null;
	}

}
