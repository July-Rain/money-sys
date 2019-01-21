package com.lawschool.service.competition;


import com.baomidou.mybatisplus.service.IService;
import com.lawschool.beans.competition.BattleRecord;
import com.lawschool.beans.competition.CompetitionRecord;
import com.lawschool.util.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 
 * @Descriptin  对战记录service接口
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
public interface BattleRecordService extends IService<BattleRecord> {


    public void updaterecord(String type);

    PageUtils queryPage(Map<String, Object> params, String uid);

    PageUtils queryPageByLeitai(Map<String, Object> params, String uid);

    int PkCountBydept(String deptcode);
    int leitaiCountBydept(String deptcode);
    int pkSorceBydept(String deptcode);
    int leitaiSorceBydept(String deptcode);

    List<Map> firstListByPk();
    List<Map> firstListByleitai();
    int leitaiWinCount();
    int pkSum();
}
