package com.lawschool.dao;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.DailyRecord;

public interface DailyRecordDao extends AbstractDao<DailyRecord> {

    DailyRecord findCurrentRecord();//查找当前记录

    void insertRecord(DailyRecord dailyRecord);//插入配置
}