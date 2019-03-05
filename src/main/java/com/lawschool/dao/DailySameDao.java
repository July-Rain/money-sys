package com.lawschool.dao;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.DailySame;
import com.lawschool.form.QuestForm;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface DailySameDao extends AbstractDao<DailySame> {

    QuestForm getDailyQuestion(@Param("configureId") String configureId);

    /**
     * 保存每日一题题目信息（统一试题）
     * @param params
     */
    void mySave(Map<String, String> params);
}
