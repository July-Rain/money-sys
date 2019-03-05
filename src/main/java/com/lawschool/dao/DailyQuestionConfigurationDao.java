package com.lawschool.dao;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.DailyQuestionConfiguration;
import com.lawschool.form.DailyForm;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.Map;

public interface DailyQuestionConfigurationDao extends AbstractDao<DailyQuestionConfiguration> {

    DailyQuestionConfiguration selectByDailyId(String id);//查询配置

    void deleteByDailyId(String id);//删除每日一题配置

    String dailyTest(Map<String,Object> map);

    DailyQuestionConfiguration findCurrentConfig();//查询当前每日一题配置

    /**
     * 判断生效日期是否重复
     * @param id 主键，有值为编辑、无值为新增
     * @param date 格式为：yyyy-MM-dd
     * @return
     */
    Integer doCheckDate(@Param("id") String id, @Param("date") String date);

    Integer mySave(DailyForm form);

    Integer myUpdate(DailyForm form);

    String getSettingsInUse();

    DailyQuestionConfiguration getSetInUse();
}