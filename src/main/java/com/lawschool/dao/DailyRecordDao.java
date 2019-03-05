package com.lawschool.dao;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.DailyRecord;
import com.lawschool.form.QuestForm;
import com.lawschool.form.ThemeAnswerForm;
import org.apache.ibatis.annotations.Param;

public interface DailyRecordDao extends AbstractDao<DailyRecord> {

    DailyRecord findCurrentRecord(String userId);//查找当前记录

    void insertRecord(DailyRecord dailyRecord);//插入配置

    /**
     * 查询用户某日的答题记录
     * @param date
     * @param userId
     * @return
     */
    QuestForm getRecord(@Param("date") String date, @Param("userId") String userId);

    Integer mySave(ThemeAnswerForm form);

    boolean updateCollect(@Param("id") String id, @Param("type") Integer type);
}