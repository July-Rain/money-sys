package com.lawschool.serviceimpl.competition;
import com.lawschool.dao.competition.BattleRecordDao;
import com.lawschool.service.competition.BattleRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BattleRecordServiceImpl implements BattleRecordService {

	@Autowired
	private BattleRecordDao battleRecordDao;


}
