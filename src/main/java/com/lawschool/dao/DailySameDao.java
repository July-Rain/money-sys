package com.lawschool.dao;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.DailySame;

public interface DailySameDao extends AbstractDao<DailySame> {
    DailySame findCurrentSameTest();
}
