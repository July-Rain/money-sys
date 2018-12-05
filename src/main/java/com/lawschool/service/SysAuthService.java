package com.lawschool.service;

import com.baomidou.mybatisplus.service.IService;
import com.lawschool.beans.Org;
import com.lawschool.beans.Role;
import com.lawschool.beans.SysMenu;
import com.lawschool.beans.User;
import com.lawschool.beans.auth.AuthRelationBean;

import java.util.List;

/**
 * @Author MengyuWu
 * @Description TODO 
 * @Date 10:46 2018-11-29
 * @Param 
 * @return 
 **/

public interface SysAuthService  {

    /**
     * @Author MengyuWu
     * @Description 获取用户的权限
     * @Date 10:46 2018-11-29
     * @Param [userId]
     * @return com.lawschool.beans.User
     **/
    
    public User getAllAuthList(String userId);
    
    /**
     * @Author MengyuWu
     * @Description 获取用户的数据权限
     * @Date 11:02 2018-11-29
     * @Param [roleId]
     * @return java.util.List<com.lawschool.beans.Org>
     **/
    

    public List<String> listAllOrgAuth(String roleId);

    /**
     * @Author MengyuWu
     * @Description 获取用户的菜单权限
     * @Date 11:07 2018-11-29
     * @Param [roleId]
     * @return java.util.List<com.lawschool.beans.SysMenu>
     **/
    
    public List<SysMenu> listAllMenuAuth(String roleId);

    /**
     * @Author MengyuWu
     * @Description 获取用户的角色列表
     * @Date 11:07 2018-11-29
     * @Param [userId]
     * @return java.util.List<com.lawschool.beans.Role>
     **/
    
    public List<Role> listAllRole(String userId);

    /**
     * @Author MengyuWu
     * @Description 角色的权限
     * @Date 11:04 2018-11-29
     * @Param [roleId]
     * @return int
     **/
    
    public int deleteAuth(String roleId);

    /**
     * @Author MengyuWu
     * @Description 新增权限
     * @Date 15:03 2018-11-29
     * @Param [role]
     * @return int
     **/
    
    //public int insertAuth(Role role);

}
