package com.lawschool.service;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.DailyQuestionConfiguration;
import com.lawschool.beans.TestQuestions;
import com.lawschool.form.QuestForm;

import java.util.Map;

public interface DailyQuestionConfigurationService extends AbstractService<DailyQuestionConfiguration> {

    DailyQuestionConfiguration selectByDailyId(String id);//查询配置

    void deleteByDailyId(String id);//删除每日一题配置

    void insertDailyConfig(DailyQuestionConfiguration dailyQuestionConfiguration);

    void updateByDailyConfig(DailyQuestionConfiguration dailyQuestionConfiguration);

    QuestForm dailyTest();//返回每日一题
}
