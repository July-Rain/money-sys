package com.lawschool.serviceimpl.competition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.IdWorker;
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
	public void save(String teamId,String uid) {

		TeamUser teamUser=new TeamUser();
		teamUser.setId(IdWorker.getIdStr());
		teamUser.setUserId(uid);
		teamUser.setTeamId(teamId);//这是战队id  这些后面都要改的
		this.insert(teamUser);

	}

	@Override
	public void deleteTeamUser(String teamId,String uid) {
		this.delete(new EntityWrapper<TeamUser>().eq("TEAM_ID",teamId).eq("USER_ID",uid));
	}
}
