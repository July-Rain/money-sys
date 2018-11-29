package com.lawschool.service.impl;
import com.lawschool.dao.BattlePlatformDao;
import com.lawschool.dao.BattleTopicSettingDao;
import com.lawschool.service.BattlePlatformService;
import com.lawschool.service.BattleTopicSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BattleTopicSettingServiceImpl implements BattleTopicSettingService {

	@Autowired
	private BattleTopicSettingDao battleTopicSettingDao;


}
