package com.lawschool.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lawschool.beans.PracticeConfiguration02;

public interface PracticeConfigurationSonDao extends BaseMapper<PracticeConfiguration02> {
    void insertConfig(PracticeConfiguration02 practiceConfiguration02);
}
