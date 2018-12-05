package com.lawschool.serviceimpl.competition;
import com.lawschool.dao.competition.CompetitionTeamDao;
import com.lawschool.service.competition.CompetitionTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompetitionTeamServiceImpl implements CompetitionTeamService {

	@Autowired
	private CompetitionTeamDao competitionTeamDao;


}
