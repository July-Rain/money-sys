package com.lawschool.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.beans.*;
import com.lawschool.beans.system.SysMenuEntity;
import com.lawschool.beans.system.SysRoleEntity;
import com.lawschool.dao.*;
import com.lawschool.dao.system.SysRoleDao;
import com.lawschool.service.SysAuthService;
import com.lawschool.service.system.SysMenuService;
import com.lawschool.util.UtilMisc;
import com.lawschool.util.UtilValidate;
import org.springframework.beans.factory.annotation.Autowired;
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
    private SysRoleMenuDao roleMenuMapper;

    @Resource
    private SysRoleOrgDao roleOrgMapper;

    @Resource
    private SysUserRoleDao userRoleMapper;

    @Resource
    private SysRoleDao sysRoleDao;

    @Resource
    private UserMapper userMapper;

    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public User getAllAuthList(String userId) {
        List<SysUserRole> userRoleList=userRoleMapper.selectList(new EntityWrapper<SysUserRole>().eq("user_id",userId));
        List<String> orgDataAuth = new ArrayList<>();
        List<SysMenuEntity> menuAuth =new ArrayList<>();
        List<SysRoleEntity> roleList=listAllRole(userId);
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

    /**
     * 获取角色所有的菜单
     * @param roleId
     * @return
     */
    @Override
    public List<SysMenuEntity> listAllMenuAuth(String roleId) {

        List<SysMenuEntity> roleMenuList = sysMenuService.findAllByRole(roleId);

        return roleMenuList;
    }

    @Override
    public List<SysRoleEntity> listAllRole(String userId) {
        List<SysRoleEntity> roleList= sysRoleDao.listRoleByUserId(userId);
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
