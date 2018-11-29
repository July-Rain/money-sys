package com.lawschool.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.beans.*;
import com.lawschool.dao.*;
import com.lawschool.service.SysAuthService;
import com.lawschool.util.UtilMisc;
import com.lawschool.util.UtilValidate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: SysAuthServiceImpl
 * Description: TODO
 * date: 2018-11-29 11:09
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
@Service
public class SysAuthServiceImpl implements SysAuthService {
    @Resource
    private SysRoleMenuMapper roleMenuMapper;

    @Resource
    private SysRoleOrgMapper roleOrgMapper;

    @Resource
    private SysUserRoleMapper userRoleMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private SysMenuDao sysMenuDao;

    @Resource
    private UserMapper userMapper;
    @Override
    public User getAllAuthList(String userId) {
        List<SysUserRole> userRoleList=userRoleMapper.selectList(new EntityWrapper<SysUserRole>().eq("user_id",userId));
        List<String> orgDataAuth = new ArrayList<>();
        List<SysMenu> menuAuth =new ArrayList<>();
        List<Role> roleList=listAllRole(userId);
        for(SysUserRole userRole: userRoleList){
            orgDataAuth.addAll(listAllOrgAuth(userRole.getRoleId()));
            menuAuth.addAll(listAllMenuAuth(userRole.getRoleId()));
        }
        if(UtilValidate.isNotEmpty(orgDataAuth)){
            orgDataAuth= UtilMisc.removeDuplicate(orgDataAuth);
        }
        if(UtilValidate.isNotEmpty(menuAuth)){
            menuAuth= UtilMisc.removeDuplicate(menuAuth);
        }
        User user =userMapper.selectById(userId);
        user.setOrgDataAuth(orgDataAuth);
        user.setRoleList(roleList);
        user.setMenuAuth(menuAuth);
        return user;
    }

    @Override
    public List<String> listAllOrgAuth(String roleId) {
        List<SysRoleOrg> roleOrgList=roleOrgMapper.selectList(new EntityWrapper<SysRoleOrg>().eq("role_id",roleId));
        List<String> orgList=new ArrayList<>();
        for(SysRoleOrg roleOrg:roleOrgList){
            orgList.add(roleOrg.getOrgId());
        }
        return orgList;
    }

    @Override
    public List<SysMenu> listAllMenuAuth(String roleId) {
        List<SysMenu> roleMenuList=sysMenuDao.listMenuAuth(roleId);
        return roleMenuList;
    }

    @Override
    public List<Role> listAllRole(String userId) {
        List<Role> roleList=roleMapper.listRoleByUserId(userId);
        return roleList;
    }

    @Override
    public int deleteAuth(String roleId) {
        //根据角色的id删除相关数据
        //删除数据权限
        roleOrgMapper.delete(new EntityWrapper<SysRoleOrg>().eq("role_id",roleId));
        //删除菜单权限
        roleMenuMapper.delete(new EntityWrapper<SysRoleMenu>().eq("role_id",roleId));
        return 0;
    }
}
