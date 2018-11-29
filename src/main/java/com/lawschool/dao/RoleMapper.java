package com.lawschool.dao;

import java.math.BigDecimal;
import com.lawschool.beans.Role;
import org.springframework.stereotype.Repository;

/**
 *
 * @Descriptin  用户dao
 * @author      张奇
 * @version     v1.0
 * @Time        2018/11/28
 *
 */
@Repository
public interface RoleMapper {
    /**
     * 增加角色
     */
    public void insert(Role role);

    /**
     * 删除角色
     */
    public void deleteRoleById(Integer roleId);
    /**
     * 修改角色
     */
    public void update(Role role);

    /**
     * 查找角色
     */
    public Role selectRoleById(Integer roleId);


}
