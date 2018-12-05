package com.lawschool.serviceimpl.competition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lawschool.beans.competition.CompetitionRecord;
import com.lawschool.dao.competition.CompetitionRecordDao;
import com.lawschool.service.competition.CompetitionRecordService;
import com.lawschool.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompetitionRecordServiceImpl extends ServiceImpl<CompetitionRecordDao, CompetitionRecord> implements CompetitionRecordService {

	@Autowired
	private CompetitionRecordDao competitionrecordDao;
	@Autowired
	private RedisUtil redisUtil;
	@Override
	public void save() {
		CompetitionRecord ww=new CompetitionRecord();
		this.insert(ww);
	}

	@Override
	public List<CompetitionRecord> findAll() {
		List<CompetitionRecord>  list=this.selectList(new EntityWrapper<CompetitionRecord>());


		redisUtil.get("idd");
		System.out.println(redisUtil.get("idd"));
		return  list;
	}

	@Override
	public void deleteId(String id) {

		this.delete(new EntityWrapper<CompetitionRecord>().eq("ID",id));
	}

	@Override
	public void updatedata() {
		CompetitionRecord ww=new CompetitionRecord();
		this.updateById(ww);
	}

	@Override
	public CompetitionRecord info(String id) {
		CompetitionRecord competitionRecord=this.selectById(id);
		return  competitionRecord;
	}
}
