package com.lawschool.service.impl;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.beans.CompetitionOnline;
import com.lawschool.dao.CompetitionOnlineDao;
import com.lawschool.dao.CompetitionRecordDao;
import com.lawschool.service.CompetitionOnlineService;
import com.lawschool.service.CompetitionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompetitionOnlineServiceImpl implements CompetitionOnlineService {

	@Autowired
	private CompetitionOnlineDao competitionOnlineDao;

	@Override
	public List<CompetitionOnline> list() {
		return competitionOnlineDao.selectList(new EntityWrapper<CompetitionOnline>());
	}
}
