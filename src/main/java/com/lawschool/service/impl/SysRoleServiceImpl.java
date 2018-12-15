package com.lawschool.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.system.SysRoleEntity;
import com.lawschool.beans.SysRoleMenu;
import com.lawschool.beans.SysRoleOrg;
import com.lawschool.dao.system.SysRoleDao;
import com.lawschool.service.system.SysRoleService;
import com.lawschool.service.SysAuthService;
import com.lawschool.util.GetUUID;
import com.lawschool.util.UtilValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @Descriptin
 * @author      张奇
 * @version     v1.0
 * @Time        2018/11/28
 *
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
     * 增加角色
     */
    @Override
    public void add(SysRoleEntity role) {
        dao.insert(role);
        //新增时添加相关的权限
        insertAuth(role);

    }
    /**
     * @Author MengyuWu
     * @Description 根据角色添加对应的菜单权限和数据权限
     * @Date 15:39 2018-11-29
     * @Param [role]
     * @return void
     **/
    
    public void insertAuth(SysRoleEntity role){
        //新增完角色后需要新增相关的数据权限和菜单权限
        String menuIds=role.getMenuIds();
        String orgIds=role.getOrgIds();
        if(UtilValidate.isNotEmpty(menuIds)){
            String [] menuIdArr=menuIds.split(",");
            List<SysRoleMenu> roleMenus= new ArrayList<>();
            for(String id:menuIdArr){
                SysRoleMenu roleMenu=new SysRoleMenu();
                roleMenu.setId(GetUUID.getUUIDs("RM"));
                roleMenu.setMenuId(id);
                roleMenu.setRoleId(role.getId());
                roleMenus.add(roleMenu);
            }
            roleMenuService.insertBatch(roleMenus);
        }
        if(UtilValidate.isNotEmpty(orgIds)){
            String [] orgIdArr=menuIds.split(",");
            List<SysRoleOrg> roleOrgs= new ArrayList<>();
            for(String id:orgIdArr){
                SysRoleOrg roleOrg=new SysRoleOrg();
                roleOrg.setId(GetUUID.getUUIDs("RO"));
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
    public void deleteById(String roleId) {
        dao.deleteRoleById(roleId);
        //删除角色对应的相关权限
        authService.deleteAuth(roleId);
    }
    /**
     * 修改角色
     */
    @Override
    public void updaterRole(SysRoleEntity role) {
        dao.update(role);
        //删除角色对应的相关权限
        authService.deleteAuth(role.getId());
        //新增权限
        insertAuth(role);
    }
    /**
     * 查找角色
     */
    @Override
    public SysRoleEntity findByRoleId(String roleId) {
        return dao.selectRoleById(roleId);
    }


    @Override
    public List<SysRoleEntity> findAll() {

        return dao.selectList(new EntityWrapper<SysRoleEntity>());

    }
}
