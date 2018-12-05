package com.lawschool.serviceimpl.competition;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lawschool.beans.competition.BattleRecord;
import com.lawschool.dao.competition.BattleRecordDao;
import com.lawschool.service.competition.BattleRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BattleRecordServiceImpl extends ServiceImpl<BattleRecordDao, BattleRecord> implements BattleRecordService {

	@Autowired
	private BattleRecordDao battleRecordDao;


}
