package com.lawschool.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import com.lawschool.beans.User;
import com.lawschool.beans.UserQuestRecord;

import com.lawschool.dao.UserQuestRecordDao;

import com.lawschool.service.UserQuestRecordService;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class UserQuestRecordServiceImpl extends ServiceImpl<UserQuestRecordDao, UserQuestRecord> implements UserQuestRecordService {
	
	@Autowired
	private UserQuestRecordDao serQuestRecordDao;

	@Override
	public int CheckpointByUser(String uid) {

		if( this.selectCount(new EntityWrapper<UserQuestRecord>().eq("SOURCE","Checkpoint").eq("USER_ID",uid))==0)
		{

			return 0;
		}
		return serQuestRecordDao.CheckpointByUser(uid);
	}


	@Override
	public int OnlinByUser(String uid) {
		if(serQuestRecordDao.OnlinByUserCount(uid)==0)
		{
			return 0;
		}
		return serQuestRecordDao.OnlinByUser(uid);
	}

	@Override
	public int leitaiByUser(String uid) {

		if( this.selectCount(new EntityWrapper<UserQuestRecord>().eq("SOURCE","leitai").eq("USER_ID",uid))==0)
		{
			return 0;
		}
		return serQuestRecordDao.leitaiByUser(uid);
	}


	@Override
	public int chuangguanCorrectBydept(String deptcode) {
		if(serQuestRecordDao.chuangguanCorrectBydeptCount(deptcode)==0)
		{
			return 0;
		}
		return serQuestRecordDao.chuangguanCorrectBydept(deptcode);
	}

	@Override
	public int OnlinCorrectBydept(String deptcode) {
		if(serQuestRecordDao.OnlinCorrectBydeptCount(deptcode)==0)
		{
			return 0;
		}
		return serQuestRecordDao.OnlinCorrectBydept(deptcode);
	}

	@Override
	public int leitaiCorrectBydept(String deptcode) {
		if(serQuestRecordDao.leitaiCorrectBydeptCount(deptcode)==0)
		{
			return 0;
		}
		return serQuestRecordDao.leitaiCorrectBydept(deptcode);
	}

	@Override
	public UserQuestRecord everyDayByUser() {
		String s=null;
		User u = (User) SecurityUtils.getSubject().getPrincipal();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try{
			s = sdf.format(new Date());
		}catch (Exception e)
		{
			System.out.println(e);
		}
		UserQuestRecord userQuestRecord=this.selectOne(new EntityWrapper<UserQuestRecord>().eq("TO_CHAR(OPT_TIME,'YYYY-MM-DD')",s).eq("USER_ID",u.getId()).eq("SOURCE","everyDay"));

		return userQuestRecord;
	}
}
