package com.lawschool.dao.practicecenter;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.practicecenter.AnalysisEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Moon
 * @Date: 2019/1/10 18:02
 * @Description:
 */
public interface AnalysisDao extends AbstractDao<AnalysisEntity> {

    /**
     * 统计分析
     * @param month
     * @param userId
     * @return
     */
    List<Map<String, Object>> analysis(@Param("month") String month, @Param("userId") String userId);
}
