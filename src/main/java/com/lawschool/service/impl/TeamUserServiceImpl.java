package com.lawschool.service.impl;
import com.lawschool.dao.CompetitionOnlineDao;
import com.lawschool.dao.TeamUserDao;
import com.lawschool.service.CompetitionOnlineService;
import com.lawschool.service.TeamUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamUserServiceImpl implements TeamUserService {

	@Autowired
	private TeamUserDao teamUserDao;


}
