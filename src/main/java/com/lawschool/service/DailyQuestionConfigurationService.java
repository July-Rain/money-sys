package com.lawschool.service;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.DailyQuestionConfiguration;
import com.lawschool.beans.TestQuestions;
import com.lawschool.beans.User;
import com.lawschool.form.DailyForm;
import com.lawschool.form.QuestForm;
import com.lawschool.util.Result;

import java.util.Date;
import java.util.Map;

public interface DailyQuestionConfigurationService extends AbstractService<DailyQuestionConfiguration> {

    DailyQuestionConfiguration selectByDailyId(String id);//查询配置

    void deleteByDailyId(String id);//删除每日一题配置

    int insertDailyConfig(DailyQuestionConfiguration dailyQuestionConfiguration);

    int updateByDailyConfig(DailyQuestionConfiguration dailyQuestionConfiguration);

    Result dailyTestCreate();//返回每日一题 以及 当前每日一题配置
    Result newDailyTestCreate();// （  新的   ）返回每日一题 以及 当前每日一题配置
    void saveQuestion(TestQuestions testQuestions,String myanswer);

    void recordScore( User u, String sorce);

    boolean mySave(DailyForm form);

    boolean doCheckDate(String id, Date date);

    /**
     * 获取使用中的设置的ID
     *  没有返回null
     * @return
     */
    String getSettingsInUse();
}
