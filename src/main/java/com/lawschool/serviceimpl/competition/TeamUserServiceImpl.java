package com.lawschool.serviceimpl.competition;
import com.lawschool.dao.competition.TeamUserDao;
import com.lawschool.service.competition.TeamUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamUserServiceImpl implements TeamUserService {

	@Autowired
	private TeamUserDao teamUserDao;


}
