package com.lawschool.dao.competition;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lawschool.beans.competition.CompetitionRecord;


public interface CompetitionRecordDao extends BaseMapper<CompetitionRecord> {


    public void updateRecordStatus();


    public int chuangguanCountBydept(String deptcode);
    public int chuangguanSorceBydept(String deptcode);
}
