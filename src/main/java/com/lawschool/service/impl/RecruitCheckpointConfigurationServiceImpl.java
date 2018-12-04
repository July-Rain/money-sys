package com.lawschool.service.impl;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lawschool.beans.RecruitCheckpointConfiguration;
import com.lawschool.beans.RecruitConfiguration;
import com.lawschool.dao.RecruitCheckpointConfigurationDao;
import com.lawschool.dao.RecruitConfigurationDao;
import com.lawschool.service.RecruitCheckpointConfigurationService;
import com.lawschool.service.RecruitConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecruitCheckpointConfigurationServiceImpl extends ServiceImpl<RecruitCheckpointConfigurationDao, RecruitCheckpointConfiguration> implements RecruitCheckpointConfigurationService {

	@Autowired
	private RecruitCheckpointConfigurationDao recruitcheckpointconfigurationDao;


	@Override
	public List<RecruitCheckpointConfiguration> findAll() {
		return this.selectList(new EntityWrapper<RecruitCheckpointConfiguration>());
	}

	@Override
	public RecruitCheckpointConfiguration info(String id) {
		RecruitCheckpointConfiguration recruitCheckpointConfiguration=  this.selectOne(new EntityWrapper<RecruitCheckpointConfiguration>().eq("ID",id));
		return recruitCheckpointConfiguration;
	}

	@Override
	public void save(RecruitCheckpointConfiguration recruitCheckpointConfiguration) {

	}
}
