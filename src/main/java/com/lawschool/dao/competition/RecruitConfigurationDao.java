package com.lawschool.dao.competition;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lawschool.beans.Msg;
import com.lawschool.beans.competition.RecruitConfiguration;

import java.util.List;
import java.util.Map;


public interface RecruitConfigurationDao extends BaseMapper<RecruitConfiguration> {

    List<RecruitConfiguration> selectAll(Map<String,Object> param, Page page);
    int selectAllCont(Map<String,Object> param);


}
