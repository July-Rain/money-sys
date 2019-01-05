package com.lawschool.service.impl;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.SchoolYearPlan;
import com.lawschool.dao.SchoolYearPlanDao;
import com.lawschool.service.SchoolYearPlanService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @ClassName SchoolYearPlanServiceImpl
 * @Author wangtongye
 * @Date 2018/12/26 14:52
 * @Versiom 1.0
 **/
@Service
public class SchoolYearPlanServiceImpl extends AbstractServiceImpl<SchoolYearPlanDao, SchoolYearPlan> implements SchoolYearPlanService {

    public SchoolYearPlan findYearPlanByNow(String userId){
        return dao.findYearPlanByNow(userId);
    }
}
