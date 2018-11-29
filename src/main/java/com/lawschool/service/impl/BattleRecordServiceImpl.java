package com.lawschool.service.impl;
import com.lawschool.dao.BattlePlatformDao;
import com.lawschool.dao.BattleRecordDao;
import com.lawschool.service.BattlePlatformService;
import com.lawschool.service.BattleRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BattleRecordServiceImpl implements BattleRecordService {

	@Autowired
	private BattleRecordDao battleRecordDao;


}
