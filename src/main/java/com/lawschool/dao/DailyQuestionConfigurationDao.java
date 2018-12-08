package com.lawschool.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lawschool.beans.DailyQuestionConfiguration;

import java.util.Map;

public interface DailyQuestionConfigurationDao extends BaseMapper<DailyQuestionConfiguration> {

    /**
     * 随机每日一题
     * @param  map
     * @return
     */
    public DailyQuestionConfiguration random(Map<String,Object> map);

}