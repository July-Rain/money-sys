package com.lawschool.service.impl.bbs.sensitivefilter;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lawschool.beans.User;
import com.lawschool.beans.bbs.sensitivefilter.SensitivefilterEntity;
import com.lawschool.beans.competition.BattleRecord;
import com.lawschool.dao.bbs.sensitivefilter.SensitivefilterDao;
import com.lawschool.dao.competition.BattleRecordDao;
import com.lawschool.service.bbs.sensitivefilter.SensitivefilterService;
import com.lawschool.service.competition.BattleRecordService;
import com.lawschool.util.PageUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class SensitivefilterServiceImpl extends ServiceImpl<SensitivefilterDao, SensitivefilterEntity> implements SensitivefilterService {


	@Autowired
	private SensitivefilterDao sensitivefilterDao;



	@Override
	public Set<String> getAll() {
		Set<String> keyWordSet=new HashSet<String>();
	    keyWordSet= sensitivefilterDao.getAll();
		return keyWordSet;
	}
}
