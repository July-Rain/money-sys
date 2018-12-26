package com.lawschool.serviceimpl.competition;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.beans.User;
import com.lawschool.beans.competition.BattlePlatform;
import com.lawschool.dao.competition.BattlePlatformDao;
import com.lawschool.service.competition.BattlePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class BattlePlatformServiceImpl extends ServiceImpl<BattlePlatformDao, BattlePlatform> implements BattlePlatformService {

	@Autowired
	private BattlePlatformDao battlePlatformDao;



	@Override
	public BattlePlatform save(User u) {
		    BattlePlatform battlePlatform=new BattlePlatform();
			battlePlatform.setId(IdWorker.getIdStr());
			battlePlatform.setPlay1(u.getId());
	//		battlePlatform.setType();
			battlePlatform.setBattleCode((((int)((Math.random()*9+1)*100000))+"").substring(0,6));
	//		battlePlatform.setForeignkeyId();
			this.insert(battlePlatform);

		return battlePlatform;
	}

	@Override
	public void updata(BattlePlatform battlePlatform,String play2Id) {
		battlePlatform.setPlay2(play2Id);
		this.updateById(battlePlatform);
	}



}
