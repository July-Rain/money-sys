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
     * 增加或修改角色
     */
    void saveOrUpdate(SysRoleEntity role);
    /**
     * 删除角色
     */
    void deleteById(String roleId);

    List<SysRoleEntity> findAll();

}
