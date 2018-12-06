package com.lawschool.controller;

import com.lawschool.base.AbstractController;
import com.lawschool.beans.User;
import com.lawschool.service.UserService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static com.lawschool.util.Constant.SUCCESS;

@RestController
@RequestMapping("/sys")
public class UserController extends AbstractController {

    @Autowired
    private UserService userService;

    //当前用户
    @RequestMapping("/getUser")
    public Result selectUserByUserId(String userId){
        Result result=Result.ok();
        User user = userService.selectUserByUserId(userId);
        result.put("info",user);
        return result;
    }

    //列表
    @RequestMapping("/getAllUsers")
    public Result selectAllUsers(Map<String,Object> params){
        Result result=Result.ok();
        PageUtils pageUtils = userService.selectAllUsers(params);
        result.put("users",pageUtils);
        return result;
    }

    //删除用户/
    @RequestMapping("/deleteUser")
    public Result deleteUser(String userId){
        int rst = userService.updateUserStatus(userId, 2000, 800);
        return rst==SUCCESS?Result.ok():Result.error("禁用用户失败");
    }

    //恢复用户
    @RequestMapping("/recoveryUser")
    public Result recoveryUser(String userId){
        int rst = userService.updateUserStatus(userId, 800, 2000);
        return rst==SUCCESS?Result.ok():Result.error("恢复用户失败");
    }


    //更改密码
    @RequestMapping("/udtPsw")
    public Result updatePassword(String password,String newPassword,HttpServletRequest request){
        int rst = userService.updatePassword(getUser().getUserId(), password, newPassword,request);
        return rst==SUCCESS?Result.ok():Result.error("修改密码失败");
    }

    //在线用户
    @RequestMapping("/getOnlineUsers")
    public Result selectOnlineUser(Map<String,Object> params){
        Result result=Result.ok();
        PageUtils pageUtils = userService.selectOnlineUser(params);
        result.put("users",pageUtils);
        return result;
    }

    //下线
    @RequestMapping("/offlineUser")
    public Result offlineUser(String userId){
        int rst = userService.updateUserOnlineStatus(userId, "1", "0");
        return rst==0?Result.ok():Result.error("下线失败");
    }

}
