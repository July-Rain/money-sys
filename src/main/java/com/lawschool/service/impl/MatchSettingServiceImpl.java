package com.lawschool.service.impl;
import com.lawschool.dao.CompetitionOnlineDao;
import com.lawschool.dao.MatchSettingDao;
import com.lawschool.service.CompetitionOnlineService;
import com.lawschool.service.MatchSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchSettingServiceImpl implements MatchSettingService {

	@Autowired
	private MatchSettingDao matchSettingDao;


}
