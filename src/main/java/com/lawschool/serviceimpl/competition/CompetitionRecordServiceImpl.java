package com.lawschool.serviceimpl.competition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.beans.Integral;
import com.lawschool.beans.User;
import com.lawschool.beans.competition.CompetitionRecord;
import com.lawschool.dao.competition.CompetitionRecordDao;
import com.lawschool.service.IntegralService;
import com.lawschool.service.competition.CompetitionRecordService;
import com.lawschool.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CompetitionRecordServiceImpl extends ServiceImpl<CompetitionRecordDao, CompetitionRecord> implements CompetitionRecordService {

	@Autowired
	private CompetitionRecordDao competitionrecordDao;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private IntegralService integralService;
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

	//保存分数记录
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void recordScore(String foreignKeyId, String nowbig, String nowlit,User u,String sorce) {
		CompetitionRecord competitionRecord=new CompetitionRecord();
		competitionRecord.setId(IdWorker.getIdStr());
		competitionRecord.setUserId(u.getId());
		competitionRecord.setForeignKeyId(foreignKeyId);
		competitionRecord.setStatus("1");
		competitionRecord.setHowBig(nowbig);
		competitionRecord.setHowLit(nowlit);
		competitionRecord.setCreateTime(new Date());
		competitionRecord.setScore(sorce);
		this.insert(competitionRecord);

		//加完后在添加 另外的积分表
		Integral integral=new Integral();
		integral.setType("1");
		integral.setPoint(Integer.parseInt(sorce));
		integralService.addIntegralRecord(integral,u);
	}
}
