package com.lawschool.service.impl;
import com.lawschool.dao.CompetitionOnlineDao;
import com.lawschool.dao.CompetitionTeamDao;
import com.lawschool.service.CompetitionOnlineService;
import com.lawschool.service.CompetitionTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompetitionTeamServiceImpl implements CompetitionTeamService {

	@Autowired
	private CompetitionTeamDao competitionTeamDao;


}
