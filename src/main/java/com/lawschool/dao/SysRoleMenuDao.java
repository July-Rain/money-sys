package com.lawschool.dao;


import com.lawschool.base.AbstractDao;
import com.lawschool.beans.SysRoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleMenuDao extends AbstractDao<SysRoleMenu> {

//    public int insertBatch(List<SysRoleMenu> sysRoleMenuList);

    /**
     * 根据角色ID，获取菜单ID列表
     */
    List<String> queryMenuIdList(@Param("roleId") String roleId, @Param("parentId") String parentId);

    int deleteByRoleId(String roleId);

    int deleteByMenuId(List<String> menuIds);

    int deleteBatchByRole(List<String> ids);
}