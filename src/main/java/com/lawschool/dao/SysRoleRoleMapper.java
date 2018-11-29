package com.lawschool.dao;

import java.math.BigDecimal;
import java.util.List;

import com.lawschool.beans.SysRoleRole;
import com.lawschool.beans.SysRoleRoleExample;
import org.apache.ibatis.annotations.Param;

public interface SysRoleRoleMapper {
    long countByExample(SysRoleRoleExample example);

    int deleteByExample(SysRoleRoleExample example);

    int deleteByPrimaryKey(BigDecimal id);

    int insert(SysRoleRole record);

    int insertSelective(SysRoleRole record);

    List<SysRoleRole> selectByExample(SysRoleRoleExample example);

    SysRoleRole selectByPrimaryKey(BigDecimal id);

    int updateByExampleSelective(@Param("record") SysRoleRole record, @Param("example") SysRoleRoleExample example);

    int updateByExample(@Param("record") SysRoleRole record, @Param("example") SysRoleRoleExample example);

    int updateByPrimaryKeySelective(SysRoleRole record);

    int updateByPrimaryKey(SysRoleRole record);
}