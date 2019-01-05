package com.lawschool.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.system.SysRoleEntity;
import com.lawschool.beans.SysRoleMenu;
import com.lawschool.beans.SysRoleOrg;
import com.lawschool.dao.system.SysRoleDao;
import com.lawschool.service.system.SysRoleService;
import com.lawschool.service.SysAuthService;
import com.lawschool.util.GetUUID;
import com.lawschool.util.UtilValidate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 张奇
 * @version v1.0
 * @Descriptin
 * @Time 2018/11/28
 */
@Service
public class SysRoleServiceImpl extends AbstractServiceImpl<SysRoleDao, SysRoleEntity> implements SysRoleService {

    @Autowired
    private SysAuthService authService;

    @Autowired
    private SysRoleMenuServiceImpl roleMenuService;

    @Autowired
    private SysRoleOrgServiceImpl roleOrgService;

    /**
     * 增加或修改角色
     */
    public void saveOrUpdate(SysRoleEntity role) {

        if (StringUtils.isEmpty(role.getId())) {
            role.setId(IdWorker.getIdStr());
            //新增角色
            dao.insert(role);
            //新增时添加相关的权限
            roleMenuService.save(role.getId(), role.getMenuList());
        } else {
            //修改角色
            dao.update(role);
            //删除角色对应的相关权限
            authService.deleteAuth(role.getId());
            //新增权限
            roleMenuService.save(role.getId(), role.getMenuList());

            List<SysRoleOrg> roleOrgs = new ArrayList<>();
            for (String id : role.getOrgList()) {
                SysRoleOrg roleOrg = new SysRoleOrg();
                roleOrg.setId(IdWorker.getIdStr());
                roleOrg.setOrgId(id);
                roleOrg.setRoleId(role.getId());
                roleOrgs.add(roleOrg);
            }
            roleOrgService.insertBatch(roleOrgs);
        }
    }

    /**
     * 删除角色
     */
    @Override
    public void delete(List<String> roleIds) {
        dao.delete(roleIds);
        //删除角色对应的相关权限
        for (String roleId : roleIds){
            authService.deleteAuth(roleId);
        }
    }

    public List<SysRoleEntity> findAll() {

        return dao.findList(new SysRoleEntity());

    }
}
