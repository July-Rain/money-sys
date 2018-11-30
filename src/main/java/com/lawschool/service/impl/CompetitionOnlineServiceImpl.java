package com.lawschool.service.impl;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lawschool.beans.CompetitionOnline;
import com.lawschool.dao.CompetitionOnlineDao;
import com.lawschool.service.CompetitionOnlineService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CompetitionOnlineServiceImpl extends ServiceImpl<CompetitionOnlineDao, CompetitionOnline> implements CompetitionOnlineService {


	@Override
	public List<CompetitionOnline> list() {
		return this.selectList(new EntityWrapper<CompetitionOnline>());
	}

	@Override
	public CompetitionOnline info(String id) {
		CompetitionOnline competitiononline=  this.selectOne(new EntityWrapper<CompetitionOnline>().eq("ID",id));
		return competitiononline;
	}

	@Override
	public void save() {
		CompetitionOnline competitionOnline=new CompetitionOnline();
		competitionOnline.setId("3");
		competitionOnline.setTopicNum("32");
		System.out.println(competitionOnline);

		this.insert(competitionOnline);
	}

	@Override
	public void deleteComOnline(String id) {
		this.deleteById(id);
	}


	@Override
	public void updateComOnline() {
		CompetitionOnline competitionOnline=new CompetitionOnline();
		competitionOnline.setId("1");
		competitionOnline.setTopicNum("32");
		competitionOnline.setUniformRules("555");
		this.updateById(competitionOnline);
	}
}
