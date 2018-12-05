package com.lawschool.serviceimpl.competition;
import com.lawschool.dao.competition.BattlePlatformDao;
import com.lawschool.service.competition.BattlePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BattlePlatformServiceImpl implements BattlePlatformService {

	@Autowired
	private BattlePlatformDao battlePlatformDao;


}
