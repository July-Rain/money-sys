package com.lawschool.service.system;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.system.SysRoleEntity;

import java.util.List;

/**
 *
 * @Descriptin  用户service
 * @author      张奇
 * @version     v1.0
 * @Time        2018/11/28
 *
 */

public interface SysRoleService extends AbstractService<SysRoleEntity> {
    /**
     * 增加角色
     */
    void add(SysRoleEntity role);
    /**
     * 删除角色
     */
    void deleteById(String roleId);
    /**
     * 修改角色
     */
    void updaterRole(SysRoleEntity role);
    /**
     * 查找角色
     */
    SysRoleEntity findByRoleId(String roleId);



    List<SysRoleEntity> findAll();

}