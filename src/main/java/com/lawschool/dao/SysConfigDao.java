package com.lawschool.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lawschool.beans.SysConfig;

import java.math.BigDecimal;
import java.util.List;

public interface SysConfigDao extends BaseMapper<SysConfig> {
    int deleteByPrimaryKey(String id);

    //int insert(SysConfig record);

    int insertSelective(SysConfig record);

    SysConfig selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysConfig record);

    int updateByPrimaryKey(SysConfig record);

    SysConfig selectByCode(String code);

    List<SysConfig> listConfig(SysConfig record);
}