package com.lawschool.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import com.lawschool.beans.UserQuestRecord;

import com.lawschool.dao.UserQuestRecordDao;

import com.lawschool.service.UserQuestRecordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



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
}
