package com.lawschool.controller;

import com.lawschool.beans.User;
import com.lawschool.service.UserService;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sys")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/getUser")
    public Result selectUserByUserId(String userId){
        Result result=Result.ok();
        User user = userService.selectUserByUserId(userId);
        result.put("info",user);
        return result;
    }

    @RequestMapping("/getAllUsers")
    public Result selectAllUsers(){
        Result result=Result.ok();
        List<User> users = userService.selectAllUsers();
        result.put("users",users);
        return result;
    }

    @RequestMapping("/deleteUser")
    public Result deleteUser(String userId){
        int rst = userService.updateUserStatus(userId, 2000, 800);
        return rst==0?Result.ok():Result.error("禁用用户失败");
    }

    @RequestMapping("/recoveryUser")
    public Result recoveryUser(String userId){
        int rst = userService.updateUserStatus(userId, 800, 2000);
        return rst==0?Result.ok():Result.error("恢复用户失败");
    }

    @RequestMapping("/login")
    public Result login(String userId,String password){
        int rst = userService.login(userId, password);
        return rst==0?Result.ok():rst==-1?Result.error("用户不存在"):Result.error("密码错误");
    }

    @RequestMapping("/udtPsw")
    public Result updatePassword(String userId,String password,String newPassword){
        int rst = userService.updatePassword(userId, password, newPassword);
        return rst==0?Result.ok():Result.error("修改密码失败");
    }

    @RequestMapping("/getOnlineUsers")
    public Result selectOnlineUser(){
        Result result=Result.ok();
        List<User> users = userService.selectOnlineUser();
        result.put("users",users);
        return result;
    }

    @RequestMapping("/offlineUser")
    public Result offlineUser(String userId){
        int rst = userService.updateUserOnlineStatus(userId, "1", "0");
        return rst==0?Result.ok():Result.error("下线失败");
    }

}
