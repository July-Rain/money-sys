package com.lawschool.dao;

import java.math.BigDecimal;
import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lawschool.beans.SysRoleMenu;
import com.lawschool.beans.SysRoleOrg;
import org.apache.ibatis.annotations.Param;

public interface SysRoleOrgDao extends BaseMapper< SysRoleOrg> {
    public int insertBatch(List<SysRoleOrg> sysRoleOrgList);
}