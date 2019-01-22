package com.lawschool.service.system;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.system.SysMenuEntity;
import com.lawschool.form.TreeForm;

import java.util.List;

/**
 * @Auther: Moon
 * @Date: 2019/1/16 19:30
 * @Description:
 */
public interface SysMenuService extends AbstractService<SysMenuEntity> {

    /**
     * 根据父级Id获取list
     * @param parentId
     * @return
     */
    List<SysMenuEntity> findList(String parentId);

    List<SysMenuEntity> userMenu(String userId, List<String> parentIds);

    /**
     * 获取所有目录或菜单
     * @param typeList 类型 0目录、1菜单
     * @return
     */
    List<TreeForm> getAllCatalog(List<Integer> typeList);

    /**
     * 获取菜单的所有上级菜单
     * @param id
     * @return
     */
    List<SysMenuEntity> queryParentById(String id);

    /**
     * 获取角色对应的菜单（除按钮）
     * @param roleId
     * @return
     */
    List<SysMenuEntity> findAllByRole(String roleId);
}
