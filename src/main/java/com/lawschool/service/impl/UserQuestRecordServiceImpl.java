package com.lawschool.service.impl;

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


}
