package com.lawschool.dao;

import java.math.BigDecimal;
import java.util.List;

import com.lawschool.beans.SysRoleOrg;
import com.lawschool.beans.SysRoleOrgExample;
import org.apache.ibatis.annotations.Param;

public interface SysRoleOrgMapper {
    long countByExample(SysRoleOrgExample example);

    int deleteByExample(SysRoleOrgExample example);

    int deleteByPrimaryKey(BigDecimal id);

    int insert(SysRoleOrg record);

    int insertSelective(SysRoleOrg record);

    List<SysRoleOrg> selectByExample(SysRoleOrgExample example);

    SysRoleOrg selectByPrimaryKey(BigDecimal id);

    int updateByExampleSelective(@Param("record") SysRoleOrg record, @Param("example") SysRoleOrgExample example);

    int updateByExample(@Param("record") SysRoleOrg record, @Param("example") SysRoleOrgExample example);

    int updateByPrimaryKeySelective(SysRoleOrg record);

    int updateByPrimaryKey(SysRoleOrg record);
}