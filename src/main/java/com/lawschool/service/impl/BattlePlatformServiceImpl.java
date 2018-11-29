package com.lawschool.service.impl;
import com.lawschool.dao.BattlePlatformDao;
import com.lawschool.dao.CompetitionOnlineDao;
import com.lawschool.service.BattlePlatformService;
import com.lawschool.service.CompetitionOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BattlePlatformServiceImpl implements BattlePlatformService {

	@Autowired
	private BattlePlatformDao battlePlatformDao;


}
