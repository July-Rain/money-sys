package com.lawschool.dao;

import com.lawschool.beans.SchoolYearPlan;
import com.lawschool.base.AbstractDao;

import java.util.Date;

public interface SchoolYearPlanDao extends AbstractDao<SchoolYearPlan> {

    SchoolYearPlan findYearPlanByNow(String userId);
}
