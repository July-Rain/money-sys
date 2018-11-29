package com.lawschool.service.impl;
import com.lawschool.dao.RecruitCheckpointConfigurationDao;
import com.lawschool.dao.RecruitConfigurationDao;
import com.lawschool.service.RecruitCheckpointConfigurationService;
import com.lawschool.service.RecruitConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecruitCheckpointConfigurationServiceImpl implements RecruitCheckpointConfigurationService {

	@Autowired
	private RecruitCheckpointConfigurationDao recruitcheckpointconfigurationDao;


}
