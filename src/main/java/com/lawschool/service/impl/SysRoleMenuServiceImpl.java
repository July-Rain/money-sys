package com.lawschool.service.impl;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.SysRoleMenu;
import com.lawschool.dao.SysRoleMenuDao;
import com.lawschool.service.SysRoleMenuService;
import com.lawschool.util.GeneralRuntimeException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: SysRoleMenuServiceImpl
 * Description: TODO
 * date: 2018-11-29 15:33
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
@Service
public class SysRoleMenuServiceImpl extends AbstractServiceImpl<SysRoleMenuDao, SysRoleMenu> implements SysRoleMenuService {

    /**
     * 保存角色与菜单关系
     * @param roleId
     * @param menuIdList
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(String roleId, List<String> menuIdList) {
        // 先删除角色与菜单关系
        try {
            dao.deleteByRoleId(roleId);
        }catch (Exception e){
            throw new GeneralRuntimeException("角色与菜单关系删除失败");
        }

        if(menuIdList.size() == 0){
            return ;
        }

        // 保存角色与菜单关系
        for(String menuId : menuIdList){
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenu.setRoleId(roleId);

            dao.insert(sysRoleMenu);
        }

    }

    /**
     * 根据角色查询所有菜单ID
     * @param roleId
     * @return
     */
    public List<String> queryMenuIdList(String roleId) {
        return dao.queryMenuIdList(roleId);
    }

    /**
     * 根据菜单删除角色菜单
     * @param menuIds
     */
    public void deleteByMenuId(List<String> menuIds){
        try {
            dao.deleteByMenuId(menuIds);
        }catch (Exception e){
            throw new GeneralRuntimeException("角色与菜单关系删除失败");
        }
    }

    /**
     * 批量删除角色菜单（根据角色IDs）
     * @param ids
     */
    public void deleteBatchByRole(List<String> ids){
        try {
            dao.deleteBatchByRole(ids);
        } catch (Exception e){
            throw new GeneralRuntimeException("角色与菜单关系删除失败");
        }
    }
}
