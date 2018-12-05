package com.lawschool.serviceimpl.competition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lawschool.beans.competition.RecruitCheckpointConfiguration;
import com.lawschool.dao.competition.RecruitCheckpointConfigurationDao;
import com.lawschool.service.competition.RecruitCheckpointConfigurationService;
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
