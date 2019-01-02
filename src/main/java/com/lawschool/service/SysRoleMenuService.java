package com.lawschool.service;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.SysRoleMenu;
import com.lawschool.util.GeneralRuntimeException;

import java.util.List;

/**
 * InterfaceName: SysRoleMenuService
 * Description: TODO
 * date: 2018-11-29 15:33
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
public interface SysRoleMenuService extends AbstractService<SysRoleMenu> {

    /**
     * 保存角色与菜单关系
     * @param roleId
     * @param menuIdList
     */
    void save(String roleId, List<String> menuIdList);

    /**
     * 根据角色查询所有菜单ID
     * @param roleId
     * @return
     */
    List<String> queryMenuIdList(String roleId);

    /**
     * 根据菜单删除角色菜单
     * @param menuIds
     */
    void deleteByMenuId(List<String> menuIds);

    /**
     * 批量删除角色菜单（根据角色IDs）
     * @param ids
     */
    void deleteBatchByRole(List<String> ids);
}
