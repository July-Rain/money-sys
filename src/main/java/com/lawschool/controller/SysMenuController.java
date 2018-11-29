package com.lawschool.controller;


import com.lawschool.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @Descriptin  菜单
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
@Controller
@RequestMapping("/menu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;


}
