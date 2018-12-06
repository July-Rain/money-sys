package com.lawschool.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lawschool.beans.SysMenu;
import com.lawschool.beans.SysRoleMenu;

import java.util.List;

public interface SysRoleMenuDao extends BaseMapper<SysRoleMenu> {

    public int insertBatch(List<SysRoleMenu> sysRoleMenuList);
}