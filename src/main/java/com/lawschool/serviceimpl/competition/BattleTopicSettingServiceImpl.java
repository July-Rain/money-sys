package com.lawschool.serviceimpl.competition;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lawschool.beans.competition.BattleTopicSetting;
import com.lawschool.dao.competition.BattleTopicSettingDao;
import com.lawschool.service.competition.BattleTopicSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BattleTopicSettingServiceImpl   extends ServiceImpl<BattleTopicSettingDao, BattleTopicSetting> implements BattleTopicSettingService {

	@Autowired
	private BattleTopicSettingDao battleTopicSettingDao;


}
