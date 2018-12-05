package com.lawschool.serviceimpl.competition;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lawschool.beans.competition.BattlePlatform;
import com.lawschool.dao.competition.BattlePlatformDao;
import com.lawschool.service.competition.BattlePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BattlePlatformServiceImpl extends ServiceImpl<BattlePlatformDao, BattlePlatform> implements BattlePlatformService {

	@Autowired
	private BattlePlatformDao battlePlatformDao;


}
