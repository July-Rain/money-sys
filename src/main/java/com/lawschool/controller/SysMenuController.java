package com.lawschool.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.annotation.SysLog;
import com.lawschool.base.Page;
import com.lawschool.beans.SysMenu;
import com.lawschool.beans.SysRoleOrg;
import com.lawschool.service.SysMenuService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;
import com.lawschool.util.UtilValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

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

    private  String id;
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
        List<SysMenu> menuList = sysMenuService.selectList(
                ew.ne("type","2").eq("is_show","1").orderBy("ORDER_NUM", true)
        );
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

//实现实体类里面list包 下级  下级list在包下级的 树结构数据
    @RequestMapping("/listAllMenuTree")
    public Result listAllMenuTree(){
     List<SysMenu>  SysMenuList= sysMenuService.listAllMenuTree();
        return Result.ok().put("listAllMenuTree", SysMenuList);
    }



    @RequestMapping("/list")
    public Result list(@RequestParam Map<String, Object> params){

        System.out.println(params);
      //条件先不写了
        PageUtils page = sysMenuService.queryPage(params);
        return Result.ok().put("page", page);
    }


    @SysLog("添加目录")
    @RequestMapping("/insert")
    public Result insert(@RequestBody SysMenu sysmenu){
        sysmenu.setId(IdWorker.getIdStr());
        sysMenuService.insert(sysmenu);
        return Result.ok().put("id",sysmenu.getId());
    }

    @RequestMapping("/info")
    public Result info(String id){
        SysMenu sysmenu = sysMenuService.selectById(id);
        return Result.ok().put("data", sysmenu);
    }


    @SysLog("更新目录")
    @RequestMapping("/update")
    public Result update(@RequestBody SysMenu sysmenu){
         sysMenuService.updateById(sysmenu);
        return Result.ok().put("id",sysmenu.getId());
    }


    @SysLog("删除参数配置")
    @RequestMapping("/delete")
    public Result delete(String id){
        String str="";

            List<SysMenu> list=   sysMenuService.queryListParentId(id);//先查找子节点
            if(list.size()>0)
            {
//              return Result.ok().put("str","有子节点");
                str="有子节点";
            }

        if(str.equals("有子节点"))
        {
            return Result.ok().put("str",str);
        }
        else {
            sysMenuService.deleteById(id);
            str="删除成功！";
        }
        return Result.ok().put("str",str);
    }
    @RequestMapping("/list2")
    public Object list2(){
        List<Map<String, Object>> maps = sysMenuService.queryForZtree();
        System.out.println(maps);
        return maps;
    }

    @RequestMapping("/select")
    public Result select(){
        //查询列表数据
        List<SysMenu> menuList = sysMenuService.queryNotButtonList();
        //添加顶级菜单
        SysMenu root = new SysMenu();
        root.setId(IdWorker.getIdStr());
        root.setName("一级菜单");
        root.setParentId("-1");
        root.setOpen(true);
        menuList.add(root);
        return Result.ok().put("menuList", menuList);
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
