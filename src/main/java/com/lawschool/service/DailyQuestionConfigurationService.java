package com.lawschool.service;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.DailyQuestionConfiguration;
import com.lawschool.beans.TestQuestions;
import com.lawschool.form.QuestForm;
import com.lawschool.util.Result;

import java.util.Map;

public interface DailyQuestionConfigurationService extends AbstractService<DailyQuestionConfiguration> {

    DailyQuestionConfiguration selectByDailyId(String id);//查询配置

    void deleteByDailyId(String id);//删除每日一题配置

    int insertDailyConfig(DailyQuestionConfiguration dailyQuestionConfiguration);

    void updateByDailyConfig(DailyQuestionConfiguration dailyQuestionConfiguration);

    Result dailyTestCreate();//返回每日一题 以及 当前每日一题配置
}
