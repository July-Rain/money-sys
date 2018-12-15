package com.lawschool.dao.competition;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lawschool.beans.competition.RecruitCheckpointConfiguration;

import java.util.List;


public interface RecruitCheckpointConfigurationDao extends BaseMapper<RecruitCheckpointConfiguration> {

     List<RecruitCheckpointConfiguration> selectListByBaBaId(String id);
}
