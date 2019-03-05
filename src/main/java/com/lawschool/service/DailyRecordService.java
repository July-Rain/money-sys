package com.lawschool.service;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.DailyRecord;
import com.lawschool.form.QuestForm;
import com.lawschool.form.ThemeAnswerForm;

/**
 * 每日一题记录表
 */
public interface DailyRecordService extends AbstractService<DailyRecord> {

    /**
     * 获取用户每日答题记录
     * @param date 日期
     * @param userId 用户Id
     * @return
     */
    QuestForm getRecord(String date, String userId);

    /**
     * 保存答题记录
     * @param form
     * @return
     */
    Integer mySave(ThemeAnswerForm form);

    /**
     * 收藏功能
     * @param id 题目ID
     * @param recordId 记录ID
     * @param type 收藏/取消收藏
     * @return
     */
    boolean doCollect(String id, String recordId, Integer type);
}
