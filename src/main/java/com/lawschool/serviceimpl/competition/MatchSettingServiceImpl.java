package com.lawschool.serviceimpl.competition;
import com.lawschool.dao.competition.MatchSettingDao;
import com.lawschool.service.competition.MatchSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchSettingServiceImpl implements MatchSettingService {

	@Autowired
	private MatchSettingDao matchSettingDao;


}
