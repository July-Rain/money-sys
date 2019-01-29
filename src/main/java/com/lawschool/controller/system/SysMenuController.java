package com.lawschool.controller.system;

import com.lawschool.annotation.SysLog;
import com.lawschool.base.AbstractController;
import com.lawschool.beans.User;
import com.lawschool.beans.system.SysMenuEntity;
import com.lawschool.form.TreeForm;
import com.lawschool.service.system.SysMenuService;
import com.lawschool.util.Result;
import com.lawschool.util.UtilValidate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: Moon
 * @Date: 2019/1/16 19:33
 * @Description:
 */
@RestController
@RequestMapping("/sysmenu")
public class SysMenuController extends AbstractController {

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 菜单管理列表，不分页
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result index(@RequestParam String parentId){

        if(StringUtils.isBlank(parentId)){
            parentId = "0";
        }

        List<SysMenuEntity> resultList = sysMenuService.findList(parentId);

        return Result.ok().put("list", resultList);
    }

    /**
     * 获取用户可见菜单
     * @return
     */
    @RequestMapping(value = "/nav", method = RequestMethod.GET)
    public Result userMenu(@RequestParam(defaultValue = "0") String parentId){
        User user = getUser();
        if(user == null){
           throw new RuntimeException("用户信息获取失败，请重新登录");
        }

        List<String> parentIds = new ArrayList<String>();
        parentIds.add(parentId);

        List<SysMenuEntity> resultList = sysMenuService.userMenu(user.getId(), parentIds);

        return Result.ok().put("menuList", resultList);
    }

    /**
     * 获取所有菜单(只有目录)
     * @return
     */
    @RequestMapping(value = "/catalog", method = RequestMethod.GET)
    public Result getAllCatalog(){
        List<Integer> typeList = new ArrayList<Integer>(1);
        typeList.add(0);
        List<TreeForm> resultList = sysMenuService.getAllCatalog(typeList);

        return Result.ok().put("list", resultList);
    }

    /**
     * 保存
     * @param entity
     * @return
     */
    @SysLog("菜单/目录保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestBody SysMenuEntity entity){

        if(StringUtils.isBlank(entity.getParentId())){
            entity.setParentId("0");
        }

        int type = entity.getType();
        String url = entity.getUrl();
        if(type == 1){// 菜单
            if(StringUtils.isBlank(url)){
                return Result.error("请输入路由信息");
            }
        }

        boolean result = sysMenuService.save(entity);
        if(result){
            return Result.ok();
        } else {
            return Result.error("保存失败");
        }
    }

    /**
     * 获取菜单信息
     * @param id
     * @return
     */
    @RequestMapping("/info/{id}")
    public Result info(@PathVariable("id") String id){

        SysMenuEntity entity = sysMenuService.findOne(id);

        // 获取上级，目录名称
        String parentId = entity.getParentId();
        String parentName = "";
        if(!"0".equals(parentId)){
            // 非一级目录
            SysMenuEntity parent = sysMenuService.findOne(parentId);
            parentName = parent.getName();
        }

        return Result.ok().put("info", entity).put("parentName", parentName);
    }

    /**
     * 获取所有菜单，除按钮
     * @return
     */
    @RequestMapping("/elTree")
    public Result getAllMenu(){
        List<Integer> typeList = new ArrayList<Integer>(2);
        typeList.add(0);
        typeList.add(1);

        List<TreeForm> resultList = sysMenuService.getAllCatalog(typeList);

        return Result.ok().put("list", resultList);
    }

    /**
     * @Author MengyuWu
     * @Description 获取请求菜单的上级菜单
     * @Date 17:50 2019-1-18
     * @Param [id]
     * @return com.lawschool.util.Result
     **/
    @RequestMapping("/getParent")
    public Result getParent(String id){
        if(UtilValidate.isEmpty(id)){
            id="0";
        }
        List<SysMenuEntity> parentList = sysMenuService.queryParentById(id);
        return Result.ok().put("parentList", parentList);
    }

    @SysLog("删除目录/菜单")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result delete(@RequestBody List<String> ids){
        boolean result = sysMenuService.remove(ids);
        return Result.ok();
    }
}
