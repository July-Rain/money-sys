package com.lawschool.controller.system;

import com.lawschool.annotation.SysLog;
import com.lawschool.base.Page;
import com.lawschool.beans.SysRoleMenu;
import com.lawschool.beans.system.SysRoleEntity;
import com.lawschool.service.SysRoleMenuService;
import com.lawschool.service.SysRoleOrgService;
import com.lawschool.service.system.SysRoleService;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 *
 * @Descriptin  用户
 * @author      张奇
 * @version     v1.0
 * @Time        2018/11/28
 *
 */
@RestController
@RequestMapping("/role")
public class SysRoleController {

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysRoleMenuService roleMenuService;

    @Autowired
    private SysRoleOrgService sysRoleOrgService;


    /**
     * 查询角色列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(@RequestParam Map<String, Object> params){
        Page<SysRoleEntity> page = roleService.findPage(new Page<SysRoleEntity>(params), new SysRoleEntity());
        return Result.ok().put("page", page);
    }

    /**
     * 根据ID查询单个角色
     */
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public Result info(@PathVariable("id") String id) {
        SysRoleEntity sysRoleEntity = roleService.findOne(id);
        sysRoleEntity.setMenuList(roleMenuService.queryMenuIdList(id));
        sysRoleEntity.setOrgList(sysRoleOrgService.queryOrgIdList(id));
        return Result.ok().put("data", sysRoleEntity);
    }

    /**
     * 保存角色
     */
    @SysLog("保存角色")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestBody SysRoleEntity sysRoleEntity) {
        roleService.saveOrUpdate(sysRoleEntity);
        return Result.ok();
    }

    /**
     * 批量删除角色
     */
    @SysLog("删除角色")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result deleteById(@RequestBody List<String> ids) {
        roleService.delete(ids);
        return Result.ok();
    }


    @RequestMapping(value = "/getAllRoles", method = RequestMethod.POST)
    public Result getAllRoles(){
        List<SysRoleEntity> all = roleService.findAll();
        return Result.ok().put("roles", all);
    }

}
