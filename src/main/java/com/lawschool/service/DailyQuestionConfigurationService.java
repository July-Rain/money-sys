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

    boolean mySave(DailyForm form);

    boolean doCheckDate(String id, Date date);

    /**
     * 获取使用中的设置的ID
     *  没有返回null
     * @return
     */
    String getSetIdInUse();

    /**
     * 获取使用中的设置
     *  没有返回null
     * @return
     */
    DailyQuestionConfiguration getSetInUse();

    /**
     * 获取题目
     * @param date
     * @param userId
     * @return
     */
    QuestForm getQuestion(String date, String userId);
}
