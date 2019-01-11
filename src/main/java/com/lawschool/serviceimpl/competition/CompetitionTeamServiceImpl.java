package com.lawschool.serviceimpl.competition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.beans.User;
import com.lawschool.beans.competition.CompetitionTeam;
import com.lawschool.beans.competition.TeamUser;
import com.lawschool.dao.competition.CompetitionTeamDao;
import com.lawschool.service.competition.CompetitionTeamService;
import com.lawschool.service.competition.TeamUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CompetitionTeamServiceImpl extends ServiceImpl<CompetitionTeamDao, CompetitionTeam> implements CompetitionTeamService {

	@Autowired
	private CompetitionTeamDao competitionTeamDao;
	@Autowired
	private TeamUserService teamUserService;
	@Override
	public List<CompetitionTeam> findAll() {

	     List<CompetitionTeam> competitionTeamList =this.selectList(new EntityWrapper<CompetitionTeam>());

		return competitionTeamList;
	}

	@Override
	public CompetitionTeam info(String id) {

		CompetitionTeam competitionTeam=this.selectById(id);

		return competitionTeam;
	}

	@Override
	public void deleteId(String id) {
		this.deleteById(id);
	}

	@Override
	public void save() {
		CompetitionTeam competitionTeam=new CompetitionTeam();
		competitionTeam.setId(IdWorker.getIdStr());
		this.insert(competitionTeam);

		//加完了  还有成员表 也要加一个  默认加这个队长
		TeamUser teamUser=new TeamUser();
		teamUser.setId(IdWorker.getIdStr());
		teamUser.setTeamId(competitionTeam.getId());
		teamUser.setUserId(competitionTeam.getUserId());
		teamUserService.insert(teamUser);
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public CompetitionTeam insertNewTeam(User u, String peopleNum) {
		CompetitionTeam competitionTeam=new CompetitionTeam();
		competitionTeam.setId(IdWorker.getIdStr());
		competitionTeam.setTeamCode((((int)((Math.random()*9+1)*100000))+"").substring(0,6));
		competitionTeam.setCreateTime(new Date());
		competitionTeam.setNowScale("1");//默认有个人是 队长
		competitionTeam.setStatus("1");
		competitionTeam.setScale(peopleNum);
		competitionTeam.setUserId(u.getId());
		this.insert(competitionTeam);
		//战队成员表里面也要有他自己
		teamUserService.save(competitionTeam.getId(),u.getId());
		return competitionTeam;
	}
}
