package com.lawschool.serviceimpl.competition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.beans.User;
import com.lawschool.beans.competition.BattlePlatform;
import com.lawschool.beans.competition.CompetitionOnline;
import com.lawschool.beans.competition.MatchSetting;
import com.lawschool.dao.competition.BattlePlatformDao;
import com.lawschool.service.competition.BattlePlatformService;
import com.lawschool.service.competition.CompetitionOnlineService;
import com.lawschool.service.competition.MatchSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class BattlePlatformServiceImpl extends ServiceImpl<BattlePlatformDao, BattlePlatform> implements BattlePlatformService {

	@Autowired
	private BattlePlatformDao battlePlatformDao;
	@Autowired
	private CompetitionOnlineService ompetitionOnlineService;
	@Autowired
	private MatchSettingService matchSettingService;
	@Override
	@Transactional(rollbackFor = Exception.class)
	public BattlePlatform save(String play1Id,String type) {
		    BattlePlatform battlePlatform=new BattlePlatform();
			battlePlatform.setId(IdWorker.getIdStr());
			battlePlatform.setPlay1(play1Id);
			battlePlatform.setType(type);
			battlePlatform.setBattleCode((((int)((Math.random()*9+1)*100000))+"").substring(0,6));
			if(type.equals("PKOnline") || type.equals("teamOnline") || type.equals("PKOnlineBycode"))
			{
				//从在线pk加载过来的
				CompetitionOnline competitionOnline=  ompetitionOnlineService.selectOne(new EntityWrapper<CompetitionOnline>());
				battlePlatform.setForeignKeyId(competitionOnline.getId());
			}
//			else if(type.equals("teamOnline"))
//			{
//				//从组队比武加载过来的
//				MatchSetting matchSetting=  matchSettingService.selectOne(new EntityWrapper<MatchSetting>());
//				battlePlatform.setForeignKeyId(matchSetting.getId());
//			}
			else if(type.equals("leitai"))
			{
				//从打擂台加载过来的
				MatchSetting matchSetting=  matchSettingService.selectOne(new EntityWrapper<MatchSetting>());
				battlePlatform.setForeignKeyId(matchSetting.getId());
			}
			this.insert(battlePlatform);

		return battlePlatform;
	}

	@Override
	public void updata(BattlePlatform battlePlatform,String play2Id) {
		battlePlatform.setPlay2(play2Id);
		this.updateById(battlePlatform);
	}


	//计算在线pk游戏次数根据人
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int PkCountByUser(String uid) {
		int i= battlePlatformDao.PkCountByUser(uid);
		return i;
	}

	//计算玩打擂台游戏次数根据人
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int leitaiCountByUser(String uid) {
		int i= battlePlatformDao.leitaiCountByUser(uid);
		return i;
	}
}
