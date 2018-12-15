package com.lawschool.dao;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.SysConfig;

import java.util.List;

public interface SysConfigDao extends AbstractDao<SysConfig> {
    int deleteByPrimaryKey(String id);


    int insertSelective(SysConfig record);

    SysConfig selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysConfig record);

    int updateByPrimaryKey(SysConfig record);

    SysConfig selectByCode(String code);

    List<SysConfig> listConfig(SysConfig record);
}