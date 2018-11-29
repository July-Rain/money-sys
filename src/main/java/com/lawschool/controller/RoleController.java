package com.lawschool.controller;

import com.lawschool.beans.Role;
import com.lawschool.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 *
 * @Descriptin  用户
 * @author      张奇
 * @version     v1.0
 * @Time        2018/11/28
 *
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

 /*   @RequestMapping("/add")
    public String add(Role role){
      roleService.add(role);
        return json();
    }*/

  /* @RequestMapping("/deleteRoleById")
    public String deleteRoleById(Integer RoleId){
       roleService.deleteById(roleId);
       return json;
   }*/

  /*  @RequestMapping("/update")
    public String update(){
        roleService.updaterRole(role);
        return json();
    }*/

 /*   @RequestMapping("findById")
    public String FindById(){
        roleService.findByRoleId(roleId);
        return  json():

    }*/
}
