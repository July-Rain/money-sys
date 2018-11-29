package com.lawschool.service.impl;
import com.lawschool.dao.CompetitionRecordDao;
import com.lawschool.dao.RecruitCheckpointConfigurationDao;
import com.lawschool.service.CompetitionRecordService;
import com.lawschool.service.RecruitCheckpointConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompetitionRecordServiceImpl implements CompetitionRecordService {

	@Autowired
	private CompetitionRecordDao competitionrecordDao;


}
