package com.lawschool.serviceimpl.competition.bak;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lawschool.beans.competition.BattleTopicSetting;
import com.lawschool.beans.competition.bak.BattleTopicSettingBak;
import com.lawschool.dao.competition.BattleTopicSettingDao;
import com.lawschool.dao.competition.bak.BattleTopicSettingBakDao;
import com.lawschool.service.competition.BattleTopicSettingService;
import com.lawschool.service.competition.bak.BattleTopicSettingBakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BattleTopicSettingServiceBakImpl extends ServiceImpl<BattleTopicSettingBakDao, BattleTopicSettingBak> implements BattleTopicSettingBakService {


}
