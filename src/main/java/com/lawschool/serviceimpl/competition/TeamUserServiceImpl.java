package com.lawschool.serviceimpl.competition;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lawschool.beans.competition.TeamUser;
import com.lawschool.dao.competition.TeamUserDao;
import com.lawschool.service.competition.TeamUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamUserServiceImpl extends ServiceImpl<TeamUserDao, TeamUser> implements TeamUserService {

	@Autowired
	private TeamUserDao teamUserDao;


	@Override
	public void save(String id) {

		TeamUser teamUser=new TeamUser();

		teamUser.setUserId("这里放useid");
		teamUser.setTeamId(id);//这是战队id  这些后面都要改的
		this.insert(teamUser);

	}

	@Override
	public void deleteId(String id) {
		this.deleteById(id);
	}
}
