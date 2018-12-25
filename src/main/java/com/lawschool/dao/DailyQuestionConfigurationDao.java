package com.lawschool.dao;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.DailyQuestionConfiguration;
import java.util.Map;

public interface DailyQuestionConfigurationDao extends AbstractDao<DailyQuestionConfiguration> {

    DailyQuestionConfiguration selectByDailyId(String id);//查询配置

    void deleteByDailyId(String id);//删除每日一题配置

    void insertDailyConfig(DailyQuestionConfiguration dailyQuestionConfiguration);

    DailyQuestionConfiguration updateByDailyConfig(DailyQuestionConfiguration dailyQuestionConfiguration);

    String dailyTest(Map<String,Object> map);

    DailyQuestionConfiguration findCurrentConfig();//查询当前每日一题配置
}