package com.lawschool.service.impl;

import com.lawschool.beans.Role;
import com.lawschool.dao.RoleMapper;
import com.lawschool.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @Descriptin
 * @author      张奇
 * @version     v1.0
 * @Time        2018/11/28
 *
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    /**
     * 增加角色
     */
    @Override
    public void add(Role role) {
        roleMapper.insert(role);
    }
    /**
     * 删除角色
     */
    @Override
    public void deleteById(Integer roleId) {
        roleMapper.deleteRoleById(roleId);
    }
    /**
     * 修改角色
     */
    @Override
    public void updaterRole(Role role) {
        roleMapper.update(role);
    }
    /**
     * 查找角色
     */
    @Override
    public Role findByRoleId(Integer roleId) {
        return roleMapper.selectRoleById(roleId);
    }
}
