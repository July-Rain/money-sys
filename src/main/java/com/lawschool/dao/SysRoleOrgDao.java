package com.lawschool.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lawschool.beans.SysRoleOrg;

import java.util.List;

public interface SysRoleOrgDao extends BaseMapper< SysRoleOrg> {

    List<String> queryOrgIdList(String roleId);
}