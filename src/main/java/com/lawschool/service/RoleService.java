package com.lawschool.service;

import com.lawschool.beans.Role;

/**
 *
 * @Descriptin  用户service
 * @author      张奇
 * @version     v1.0
 * @Time        2018/11/28
 *
 */

public interface RoleService {
    /**
     * 增加角色
     */
    public void add(Role role);
    /**
     * 删除角色
     */
    public void deleteById(Integer roleId);
    /**
     * 修改角色
     */
    public void updaterRole(Role role);
    /**
     * 查找角色
     */
    public Role findByRoleId(Integer roleId);



}
