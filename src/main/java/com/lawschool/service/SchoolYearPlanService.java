package com.lawschool.service;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.SchoolYearPlan;

/**
 * @ClassName SchoolYearPlanService
 * @Author wangtongye
 * @Date 2018/12/26 14:50
 * @Versiom 1.0
 **/
public interface SchoolYearPlanService extends AbstractService<SchoolYearPlan> {

    /**
     * 查询当前学年目标
     * @return
     */
    SchoolYearPlan findYearPlanByNow(String userId);

     int  insertsave (SchoolYearPlan schoolYearPlan);
}
