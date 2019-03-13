package com.lawschool.serviceimpl.competition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.beans.Integral;
import com.lawschool.beans.StuMedia;
import com.lawschool.beans.User;
import com.lawschool.beans.competition.CompetitionRecord;
import com.lawschool.beans.system.Fraction;
import com.lawschool.dao.competition.CompetitionRecordDao;
import com.lawschool.enums.Source;
import com.lawschool.service.IntegralService;
import com.lawschool.service.competition.CompetitionRecordService;
import com.lawschool.service.system.FractionService;
import com.lawschool.util.*;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CompetitionRecordServiceImpl extends ServiceImpl<CompetitionRecordDao, CompetitionRecord> implements CompetitionRecordService {

	@Autowired
	private CompetitionRecordDao competitionrecordDao;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private IntegralService integralService;

	@Autowired
	private FractionService fractionService;


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
	public String recordScore(String foreignKeyId, String nowbig, String nowlit,User u,String sorce) {

		String s="";
		//去己领表中 找当天的 这个人这个 闯关 所有数据  积分看看有没有 超过了上限
		//得到 上限

		Result result=fractionService.getFractionByType("1", Source.RECRUIT);
		Fraction fraction=(Fraction)result.get("fraction");

		Float f=  fraction.getDailyLimit();
		Float f2=Float.parseFloat(sorce);
		Float f3=0f;
		List<CompetitionRecord> list=this.selectList(new EntityWrapper<CompetitionRecord>().eq("USER_ID",u.getId()).eq("STATUS",1).addFilter( "to_char(CREATE_TIME,'dd')=to_char(sysdate,'dd')"));
		for(CompetitionRecord competitionrecord:list)
		{
			f3=f3+Float.parseFloat(competitionrecord.getScore());
		}



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
		//得到了我记录表里 有多少分了   与  上限 对比

		//如果 记录里的分数 大于了上限   ，，保存 记录 但是不添加积分
		if(f3>=f){
			s="已达到今日上限";
		}
		//如果记录里的分数 不大于上限
		else if(f3<f){
			//(f-f3)//只能再加上这么多分
			//如果这次的分数小于 还能加的分数  ，那太好了 直接加
			if(f2<=(f-f3)){

				//加完后在添加 另外的积分表
				Integral integral=new Integral();
				integral.setType("1");
				integral.setPoint(Float.parseFloat(sorce));
				integral.setSrc("Checkpoint");
				integralService.addIntegralRecord(integral,u);
				s="获得"+sorce+"积分";
			}
			//如果这次的分数大于还能加的分数  ，那就只能 加 能加的分数  多了 加不了，但是记录 还是 没关系的 只是积分表
			else if(f2>(f-f3)){

				//加完后在添加 另外的积分表
				Integral integral=new Integral();
				integral.setType("1");
				integral.setPoint((f-f3));
				integral.setSrc("Checkpoint");
				integralService.addIntegralRecord(integral,u);
				s="超过上限，获得"+(f-f3)+"积分";
			}
		}
			return s;
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateRecordStatus() {
		competitionrecordDao.updateRecordStatus();
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public int chuangguanCountByUser(String uid) {
		int i= this.selectCount(new EntityWrapper<CompetitionRecord>().eq("USER_ID",uid));
		return i;
	}


//根据人员来分页查找数据
	@Override
	public PageUtils queryPage(Map<String, Object> params,String uid) {
//		String stuTitle = (String)params.get("stuTitle");
		EntityWrapper<CompetitionRecord> ew = new EntityWrapper<>();
	    ew.eq("USER_ID",uid);
		ew.orderBy("CREATE_TIME",false);
		Page<CompetitionRecord> page = this.selectPage(
				new Query<CompetitionRecord>(params).getPage(),ew);
		return new PageUtils(page);
	}


	@Override
	public int chuangguanCountBydept(String deptcode) {

	   int i=competitionrecordDao.chuangguanCountBydept(deptcode);
		return i;
	}

	@Override
	public int chuangguanSorceBydept(String deptcode) {
		int i=competitionrecordDao.chuangguanSorceBydept(deptcode);
		return i;
	}

	@Override
	public List<CompetitionRecord> chuangGuanRanking() {
		return competitionrecordDao.chuangGuanRanking();
	}

	@Override
	public CompetitionRecord chuangGuanRankingByUser() {

		User u = (User) SecurityUtils.getSubject().getPrincipal();
		return this.selectOne(new EntityWrapper<CompetitionRecord>().eq("USER_ID",u.getId()).eq("STATUS","1").ne("SCORE","0").orderBy("SCORE",true));
	}
}
