package com.lawschool.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lawschool.beans.PracticeConfiguration02;
import com.lawschool.dao.PracticeConfigurationSonDao;
import com.lawschool.service.PracticeConfigurationSonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PracticeConfigurationSonImpl extends ServiceImpl<PracticeConfigurationSonDao, PracticeConfiguration02> implements PracticeConfigurationSonService {

    @Autowired
    PracticeConfigurationSonDao practiceConfigurationSonDao;

    @Override
    public void insertConfig(PracticeConfiguration02 practiceConfiguration02) {
        practiceConfigurationSonDao.insertConfig(practiceConfiguration02);
    }
}
