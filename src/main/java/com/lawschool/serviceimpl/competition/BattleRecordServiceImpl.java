package com.lawschool.serviceimpl.competition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lawschool.beans.StuMedia;
import com.lawschool.beans.competition.BattleRecord;
import com.lawschool.dao.competition.BattleRecordDao;
import com.lawschool.service.competition.BattleRecordService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Query;
import com.lawschool.util.UtilValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BattleRecordServiceImpl extends ServiceImpl<BattleRecordDao, BattleRecord> implements BattleRecordService {

	@Autowired
	private BattleRecordDao battleRecordDao;

	@Override
	public void updaterecord(String type) {
		battleRecordDao.updaterecord(type);
	}


	@Override
	public PageUtils queryPage(Map<String, Object> params,String uid) {


		params.put("uid",uid);
        Page<BattleRecord> page = new Page<BattleRecord>(Integer.parseInt(params.get("currPage").toString()),Integer.parseInt(params.get("pageSize").toString()));

        page.setRecords(battleRecordDao.selectListPage(page,params));
        page.setTotal(battleRecordDao.selectListPageCount(params));

		return new PageUtils(page);
	}


	@Override
	public PageUtils queryPageByLeitai(Map<String, Object> params,String uid) {

		params.put("uid",uid);
		Page<BattleRecord> page = new Page<BattleRecord>(Integer.parseInt(params.get("currPage").toString()),Integer.parseInt(params.get("pageSize").toString()));

		page.setRecords(battleRecordDao.selectListPageByLeitai(page,params));
		page.setTotal(battleRecordDao.selectListPageCountByLeitai(params));

		return new PageUtils(page);
	}


	@Override
	public int PkCountBydept(String deptcode) {
	   int i=battleRecordDao.PkCountBydept(deptcode);
		return i;
	}

	@Override
	public int leitaiCountBydept(String deptcode) {
		int i=battleRecordDao.leitaiCountBydept(deptcode);
		return i;
	}

	@Override
	public int pkSorceBydept(String deptcode) {
		int i=battleRecordDao.pkSorceBydept(deptcode);
		return i;
	}

	@Override
	public int leitaiSorceBydept(String deptcode) {
		int i=battleRecordDao.leitaiSorceBydept(deptcode);
		return i;
	}


	@Override
	public List<Map> firstListByPk() {
		return battleRecordDao.firstListByPk();
	}

	@Override
	public List<Map> firstListByleitai() {
		return battleRecordDao.firstListByleitai();
	}
}
