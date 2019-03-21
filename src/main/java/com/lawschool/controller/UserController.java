package com.lawschool.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.annotation.SysLog;
import com.lawschool.base.AbstractController;
import com.lawschool.base.Page;
import com.lawschool.beans.User;
import com.lawschool.beans.exam.ExamConfig;
import com.lawschool.service.UserService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;
import com.lawschool.util.UtilValidate;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static com.lawschool.util.Constant.SUCCESS;

@RestController
@RequestMapping("/sys")
public class UserController extends AbstractController {

    @Autowired
    private UserService userService;

    /**
     * @Author zjw
     * @Description 获取当前用户
     * @Date 9:19 2018/12/19
     * @Param [userId]
     * @return com.lawschool.util.Result
    **/
    @RequestMapping("/getUser")
    public Result selectUserByUserId(String userId){
        Result result=Result.ok();
        User user = userService.selectUserByUserId(userId);
        result.put("info",user);
        return result;
    }
    /**
     * @Author zjw
     * @Description 获取当前用户
     * @Date 9:19 2018/12/19
     * @Param [userId]
     * @return com.lawschool.util.Result
     **/
    @RequestMapping("/getUser2")
    public Result selectUserByUserId2(){
        Result result=Result.ok();
       String userId=getUser().getId();
        User user = userService.selectUserByUserId(userId);
        result.put("info",user);
        return result;
    }


    //列表
    @RequestMapping("/getAllUsers")
    public Result selectAllUsers(@RequestParam Map<String,Object> params){
        Result result=Result.ok();
        User user = new User();
        if(UtilValidate.isEmpty(params.get("orgCode"))){
            params.put("orgCode","32");
        }
        if(UtilValidate.isNotEmpty(params.get("userName"))){
            user.setUserName(String.valueOf(params.get("userName")));
        }
        if(UtilValidate.isNotEmpty(params.get("userCode"))){
            user.setUserCode(String.valueOf(params.get("userCode")));
        }
        System.out.println(params.get("orgCode"));

        user.setOrgCode(String.valueOf(params.get("orgCode")));

        user.setIdentify("0");//取用户
        Page<User> page = userService.findPage(new Page<User>(params),user);
        result.put("page", page);
        return result;
    }




    /**
     * @Author zjw
     * @Description 获取所有用户/教官/在线 identify  isOnline
     * @Date 9:19 2018/12/19
     * @Param [params]
     * @return com.lawschool.util.Result
    **/
    @RequestMapping("/getUorT")
    public Result getUorT(@RequestParam Map<String,String> params){
        PageUtils pageUtils = userService.selectAllUsers(params);
        return Result.ok().put("page",pageUtils);
    }

    @SysLog("添加用户")
    @RequestMapping("/add")
    public Result addUorT(@RequestBody User user){
        Result result= userService.addUser(user);
        return result;
    }
    @SysLog("修改员工身份")
    @RequestMapping("/changeIdentify")
    public Result changeIdentify(String id,String identify){
       userService.changeIdentify(id,identify,"identify");
       return  Result.ok();
    }
    @SysLog("修改管理员身份")
    @RequestMapping("/changeAdmin")
    public Result changeAdmin(String id,String isAdmin){
       userService.changeIdentify(id,isAdmin,"isAdmin");
       return  Result.ok();
    }

    @RequestMapping("/userPoliceId")
    public Result userPoliceId(String userPoliceId ,String mytype,String id){
        String type="";
        User user=null;
        if(mytype.equals("1"))
        {
             user= userService.selectOne(new EntityWrapper<User>().eq("USER_POLICE_ID",userPoliceId));

        }
        else if(mytype.equals("2"))
        {
            user= userService.selectOne(new EntityWrapper<User>().eq("USER_POLICE_ID",userPoliceId).ne("ID",id));
        }
        if(user==null)
        {
            type="0";
        }
        else
        {
            type="1";
        }
        return Result.ok().put("type",type);
    }
    @RequestMapping("/userCode")
    public Result userCode(String userCode ,String mytype,String id){
        String type="";
        User user=null;
        if(mytype.equals("1"))
        {
            user= userService.selectOne(new EntityWrapper<User>().eq("USER_CODE",userCode));

        }
        else if(mytype.equals("2"))
        {
            user= userService.selectOne(new EntityWrapper<User>().eq("USER_CODE",userCode).ne("ID",id));
        }
        if(user==null)
        {
            type="0";
        }
        else
        {
            type="1";
        }
        return Result.ok().put("type",type);
    }


   /**
    * @Author zjw
    * @Description 删除用户
    * @Date 9:20 2018/12/19
    * @Param [userId]
    * @return com.lawschool.util.Result
   **/
   @SysLog("禁用用户")
    @RequestMapping("/deleteUser")
    public Result deleteUser(String id){
        int rst = userService.updateUserStatus(id, 2000, 800);
        return rst==SUCCESS?Result.ok():Result.error("禁用用户失败");
    }

    /**
     * @Author zjw
     * @Description 恢复用户
     * @Date 9:20 2018/12/19
     * @Param [userId]
     * @return com.lawschool.util.Result
    **/
    @SysLog("恢复用户")
    @RequestMapping("/recoveryUser")
    public Result recoveryUser(String id){
        int rst = userService.updateUserStatus(id, 800, 2000);
        return rst==SUCCESS?Result.ok():Result.error("恢复用户失败");
    }

    @RequestMapping("/resetPassword")
    public Result resetPassword(@RequestParam String id){
        int result = userService.resetPassword(id);
        return result==1?Result.ok():Result.error("重置密码失败");
    }

    /**
     * @Author zjw
     * @Description 修改密码
     * @Date 10:41 2018/12/19
     * @Param [password, newPassword, request]
     * @return com.lawschool.util.Result
    **/
    @SysLog("修改密码")
    @RequestMapping("/udtPsw")
    public Result updatePassword(@RequestParam Map<String,Object> params,HttpServletRequest request){
        if(UtilValidate.isEmpty(params.get("oldPassword"))){
            return Result.error("旧密码不能为空");
        }
        if(UtilValidate.isEmpty(params.get("newPassword"))){
            return Result.error("新密码不能为空");
        }
        int rst = userService.updatePassword(getUser().getId(), params.get("oldPassword").toString(),  params.get("newPassword").toString(),request);
        return rst==SUCCESS?Result.ok():Result.error("修改密码失败");
    }


    /**
     * @Author zjw
     * @Description 下线
     * @Date 9:22 2018/12/19
     * @Param [userId]
     * @return com.lawschool.util.Result
    **/
    @RequestMapping("/offlineUser")
    public Result offlineUser(String id){
        int rst = userService.updateUserOnlineStatus(id, "1", "0");
        return rst==SUCCESS?Result.ok():Result.error("下线失败");

    }

    /**
     * @Author zjw
     * @Description 修改用户信息
     * @Date 15:46 2018/12/24
     * @Param [user]
     * @return com.lawschool.util.Result
    **/
    @SysLog("修改用户信息")
    @RequestMapping("/updata")
    public Result update(@RequestBody User user){
        int rst = userService.updateUser(user);
        return rst==SUCCESS?Result.ok():Result.error("修改用户失败");
    }
    /**
     * @Author
     * @Description 修改用户信息----勋章
     * @Date 15:46 2018/12/24
     * @Param [user]
     * @return com.lawschool.util.Result
     **/
    @SysLog("修改用户信息")
    @RequestMapping("/updateBymyMedal")
    public Result updateBymyMedal(String myMedal){
        String userId=getUser().getId();
        User user = userService.selectUserByUserId(userId);
        user.setMyMedal(myMedal);
        userService.updateById(user);
        return Result.ok();
    }

    @RequestMapping("/jsGetUser")
    public Result jsGetUser(){
        User u = (User) SecurityUtils.getSubject().getPrincipal();
        return Result.ok().put("u",u);
    }
    @SysLog("删除用户信息")
    @RequestMapping("/deleteuser")
    public Result deleteuser(String id){
        userService.deleteById(id);
        return Result.ok();
    }
}
