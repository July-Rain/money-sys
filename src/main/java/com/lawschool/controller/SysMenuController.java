package com.lawschool.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.beans.SysMenu;
import com.lawschool.service.SysMenuService;
import com.lawschool.util.Result;
import com.lawschool.util.UtilValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
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
    public Result nav(String id){
        EntityWrapper<SysMenu> ew = new EntityWrapper<SysMenu>();
        if(UtilValidate.isNotEmpty(id)){
            ew.eq("parent_id",id);
        }
        List<SysMenu> menuList = sysMenuService.selectList(ew
                .ne("type","2").eq("is_show","1"));
        if(UtilValidate.isNotEmpty(id)){
            return Result.ok().put("menuList", menuList);
        }else{
            return Result.ok().put("menuList", bulidDeptTree(menuList));
        }

    }
    /**
     * 两层循环实现建树
     * @param list 传入的树节点列表
     * @return
     */
    private List<SysMenu> bulidDeptTree(List<SysMenu> list) {

        List<SysMenu> finalTrees = new ArrayList<SysMenu>();

        for (SysMenu menu : list) {

            if ("0".equals(menu.getParentId())) {
                finalTrees.add(menu);
            }
            List<SysMenu> menuList = new ArrayList<SysMenu>();
            for (SysMenu d : list) {
                if ((d.getParentId()).equals(menu.getId())) {
                    menuList.add(d);
                }
            }
            if(UtilValidate.isNotEmpty(menuList)){
                Collections.sort(menuList);

            }
            menu.setList(menuList);

        }
        return finalTrees;
    }

}
