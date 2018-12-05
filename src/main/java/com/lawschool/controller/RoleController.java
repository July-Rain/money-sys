package com.lawschool.controller;

import com.lawschool.annotation.SysLog;
import com.lawschool.beans.Role;
import com.lawschool.beans.SysConfig;
import com.lawschool.service.RoleService;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
public class RoleController {

    @Autowired
    private RoleService roleService;

    @SysLog("查询配置")
    @RequestMapping("/list")
    public Result list(@RequestParam Map<String, Object> params){

        return Result.ok().put("data", roleService.findAll());
    }

}
