package com.lawschool.dao.competition;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lawschool.beans.competition.BattleRecord;
import com.lawschool.beans.law.TaskDesicEntity;
import com.lawschool.util.PageUtils;

import java.util.List;
import java.util.Map;


public interface BattleRecordDao extends BaseMapper<BattleRecord> {
    public void updaterecord(String type);


    List<BattleRecord> selectListPage(Page page, Map<String, Object> params);

    int selectListPageCount(Map<String, Object> params);

    List<BattleRecord> selectListPageByLeitai(Page page, Map<String, Object> params);

    int selectListPageCountByLeitai(Map<String, Object> params);
}
