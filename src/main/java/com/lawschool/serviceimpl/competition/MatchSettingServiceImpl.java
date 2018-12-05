package com.lawschool.serviceimpl.competition;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lawschool.beans.competition.MatchSetting;
import com.lawschool.dao.competition.MatchSettingDao;
import com.lawschool.service.competition.MatchSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchSettingServiceImpl  extends ServiceImpl<MatchSettingDao, MatchSetting> implements MatchSettingService {

	@Autowired
	private MatchSettingDao matchSettingDao;


}
