package com.lawschool.controller.system;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.base.Page;
import com.lawschool.beans.system.SysRoleEntity;
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
        SysRoleEntity sysRoleEntity = roleService.selectById(id);
        return Result.ok().put("sysRoleEntity", sysRoleEntity);
    }

    /**
     * 保存角色
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Result insert(SysRoleEntity sysRoleEntity) {
        roleService.save(sysRoleEntity);
        return Result.ok();
    }

    /**
     * 批量删除角色
     */
    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public Result deleteById(List<String> idList) {
        roleService.deleteBatchIds(idList);
        return Result.ok();
    }

}
