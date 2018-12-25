package com.lawschool.service.impl;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.DailyRecord;
import com.lawschool.dao.DailyRecordDao;
import com.lawschool.service.DailyRecordService;
import org.springframework.stereotype.Service;

@Service
public class DailyRecordServiceImpl extends AbstractServiceImpl<DailyRecordDao, DailyRecord> implements DailyRecordService {
}
