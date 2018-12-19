package com.lawschool.service;

import com.baomidou.mybatisplus.service.IService;
import com.lawschool.beans.PracticeConfiguration02;

public interface PracticeConfigurationSonService extends IService<PracticeConfiguration02>{
    void insertConfig(PracticeConfiguration02 practiceConfiguration02);
}
