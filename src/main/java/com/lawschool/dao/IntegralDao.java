package com.lawschool.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lawschool.base.AbstractDao;
import com.lawschool.beans.Integral;

public interface IntegralDao  extends AbstractDao<Integral> {

    int chuangguanCountByUser(String uid);
    int pkByUser(String uid);
    int leitaiByUser(String uid);
}