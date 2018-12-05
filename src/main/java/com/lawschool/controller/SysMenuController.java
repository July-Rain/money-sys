package com.lawschool.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.beans.SysMenu;
import com.lawschool.service.SysMenuService;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * @Descriptin  菜单
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
@RestController
@RequestMapping("/menu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * @Author MengyuWu
     * @Description 查询首页的菜单
     * @Date 15:19 2018-12-5
     * @Param []
     * @return com.lawschool.util.Result
     **/
    
    @RequestMapping("/nav")
    public Result nav(){
        List<SysMenu> menuList = sysMenuService.selectList(new EntityWrapper<>());
        return Result.ok().put("menuList", menuList);
    }

}
