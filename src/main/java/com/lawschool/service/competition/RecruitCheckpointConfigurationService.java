package com.lawschool.service.competition;


import com.baomidou.mybatisplus.service.IService;
import com.lawschool.beans.competition.RecruitCheckpointConfiguration;

import java.util.List;

/**
 * 
 * @Descriptin  闯关关卡配置service接口
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
public interface RecruitCheckpointConfigurationService extends IService<RecruitCheckpointConfiguration> {

    public List<RecruitCheckpointConfiguration> findAll();

    public RecruitCheckpointConfiguration info(String id);

    public void save(RecruitCheckpointConfiguration recruitCheckpointConfiguration);

    public List<RecruitCheckpointConfiguration> selectListByBaBaId(String id);
}
