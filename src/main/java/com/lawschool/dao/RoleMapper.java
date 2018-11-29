package com.lawschool.dao;

import java.math.BigDecimal;
import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lawschool.beans.Role;
import com.lawschool.beans.SysRoleMenu;
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
public interface RoleMapper  extends BaseMapper<Role> {
    /**
     * 增加角色
     */
    //public void insert(Role role);

    /**
     * 删除角色
     */
    public void deleteRoleById(String roleId);
    /**
     * 修改角色
     */
    public void update(Role role);

    /**
     * 查找角色
     */
    public Role selectRoleById(String roleId);

    /**
     * @Author MengyuWu
     * @Description 根据用户的id查询用户的角色
     * @Date 14:33 2018-11-29
     * @Param [userId]
     * @return java.util.List<com.lawschool.beans.Role>
     **/
    
    public List<Role> listRoleByUserId(String userId);

}
