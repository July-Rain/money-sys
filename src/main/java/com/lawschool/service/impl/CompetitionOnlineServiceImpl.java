package com.lawschool.service.impl;
import com.lawschool.dao.CompetitionOnlineDao;
import com.lawschool.dao.CompetitionRecordDao;
import com.lawschool.service.CompetitionOnlineService;
import com.lawschool.service.CompetitionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompetitionOnlineServiceImpl implements CompetitionOnlineService {

	@Autowired
	private CompetitionOnlineDao competitionOnlineDao;


}
