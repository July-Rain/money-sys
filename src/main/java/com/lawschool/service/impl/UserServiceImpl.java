package com.lawschool.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.SysUserRole;
import com.lawschool.beans.User;
import com.lawschool.beans.UserExample;
import com.lawschool.beans.diagnosis.StuDiagnosisEntity;
import com.lawschool.config.ShiroUtils;
import com.lawschool.dao.SysUserRoleDao;
import com.lawschool.dao.UserMapper;
import com.lawschool.service.UserService;
import com.lawschool.util.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.security.util.Password;

import javax.rmi.CORBA.Util;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.*;

import static com.lawschool.util.Constant.*;

@Service
public class UserServiceImpl extends AbstractServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    SysUserRoleDao sysUserRoleDao;



    /**
     * @Author zjw
     * @Description 获取用户信息
     * @Date 10:08 2018/12/19
     * @Param [id]
     * @return com.lawschool.beans.User
    **/
    @Override
    public User selectUserByUserId(String id) {
        return userMapper.selectById(id);
    }

    /**
     * @Author zjw
     * @Description 查询用户或者教官或在线用户
     * @Date 9:21 2018/12/19
     * @Param [params]
     * @return com.lawschool.util.PageUtils
    **/
    @Override
    public PageUtils selectAllUsers(Map<String,String> params) {


//        EntityWrapper<User> ew = new EntityWrapper<>();//默认所有的用户
//
//        if(UtilValidate.isNotEmpty(params.get("identify"))){
//            ew.eq("IDENTIFY",params.get("identify"));// 0-普通用户  1-教官
//        }
//
//        if(UtilValidate.isNotEmpty(params.get("orgCode"))){
//            ew.like("ORG_CODE",UtilString.reverseAndReplaceStr(params.get("orgCode").toString()));//部门
//        }
//        if(UtilValidate.isNotEmpty(params.get("userName"))){
//            ew.like("USER_NAME", (String) params.get("userName"));//姓名
//
//        }
//
//        if(UtilValidate.isNotEmpty(params.get("userCode"))){
//            ew.like("USER_CODE", (String) params.get("userCode"));//身份证号
//
//        }
//
//        if(UtilValidate.isNotEmpty(params.get("isOnline"))){
//            ew.eq("IS_ONLINE",1);//1  在线
//        }
//        if(UtilValidate.isNotEmpty(params.get("userStatus"))){
//            ew.eq("USER_STATUS","2000");//1  在线
//        }
//        ew.orderBy("create_time",false);
//        Page<User> page = this.selectPage( new Query<User>(params).getPage(),ew);
//
//        PageUtils pageUtils=new PageUtils(page);
//        return pageUtils;
        Page<User> page = new Page<User>(Integer.parseInt(params.get("currPage")),Integer.parseInt(params.get("pageSize")));
        page.setRecords(userMapper.selectAllUsers(page,params));
        int userNum =userMapper.countUser(params);
        page.setTotal(userNum);
        return new PageUtils(page);
    }
    
    

    /**
     * @Author zjw
     * @Description 修改用户状态
     * @Date 10:08 2018/12/19
     * @Param [userId, nowStatus, updateStatus]
     * @return int
    **/
    @Override
    public int updateUserStatus(String id, Integer nowStatus, Integer updateStatus) {
        User use=new User();
        use.setIsAdmin(1);
        use.setUserStatus(updateStatus);
        EntityWrapper<User> ew=new EntityWrapper<>();
        ew.eq("ID",id);
        ew.eq("USER_STATUS",nowStatus);
        int res=userMapper.update(use,ew);
        return res==1? SUCCESS:ERROR;
    }

    /**
     * @Author zjw
     * @Description 修改用户在线状态
     * @Date 10:09 2018/12/19
     * @Param [userId, nowStatus, updateStatus]
     * @return int
    **/
    @Override
    public int updateUserOnlineStatus(String id, String nowStatus, String updateStatus) {
        User use=new User();
        use.setIsOnline(updateStatus);
        use.setIsAdmin(1);
        EntityWrapper<User> ew=new EntityWrapper<>();
        ew.eq("ID",id);
        ew.eq("IS_ONLINE",nowStatus);
        int res=userMapper.update(use,ew);
        return res==1? SUCCESS:ERROR;
    }

    /**
     * @Author zjw
     * @Description 登陆
     * @Date 10:10 2018/12/19
     * @Param [userCode, password, request]
     * @return int
    **/
    @Override
    @Transactional(rollbackFor=Exception.class)
    public int login(String userCode,String password,HttpServletRequest request) {
        List<User> users = userMapper.selectList(new EntityWrapper<User>().eq("id",userCode));
        if(users!=null && users.size()>0){
            User user=users.get(0);
            String salt=user.getSalt();
            String pass1 = MD5Util.Md5Hex(password + salt);
            if(user.getPassword().equals(pass1)){
                this.updateUserOnlineStatus(user.getId(),"0","1");//标记为上线
                request.getSession().setAttribute("user", user);

                return SUCCESS;//登录成功
            }
            return ERROR_PSW;//密码错误
        }
        return IS_NOT_EXIST;//用户不存在
    }
    
    /**
     * @Author zjw
     * @Description 
     * @Date 16:03 2018-12-27
     * @Param 
     * @return 
    **/
    @Override
    @Transactional(rollbackFor=Exception.class)
    public Result loginShiro(String userCode,String password,HttpServletRequest request) {
        SecurityUtils.getSubject().logout();// 此行代码用于修改会话标识未更新的BUG，作用清空登录之前产生的session信息
        try{
            Subject subject = ShiroUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(userCode, password);
            subject.login(token);
        }catch (UnknownAccountException e) {
            return Result.error(e.getMessage());
        }catch (IncorrectCredentialsException e) {
            return Result.error("账号或密码不正确");
        }
        catch (Exception e) {
            return Result.error(e.getMessage());
        }
        User userTemp=this.selectOne(new EntityWrapper<User>().eq("USER_CODE",userCode));
        this.updateUserOnlineStatus(userTemp.getId(),"0","1");//标记为上线
        //request.getSession().setAttribute("user", user);
        return  Result.ok();
    }

    /**
     * @Author zjw
     * @Description 修改密码
     * @Date 10:13 2018/12/19
     * @Param [userCode, password, newPassword, request]
     * @return int
    **/
    @Override
    public int updatePassword(String userCode, String password, String newPassword,HttpServletRequest request) {
        int rst = this.login(userCode, password,request);
        if(rst==0){//用户存在
            User user=new User();
            String salt = RandomStringUtils.randomAlphanumeric(20);//生成盐
            String pass2=MD5Util.Md5Hex(newPassword+salt);//数据库中新密码
            user.setSalt(salt);
            user.setPassword(pass2);
            user.setId(userCode);
            int resr = userMapper.update(user, new EntityWrapper<User>().eq("ID", user.getId()));//修改密码
            return resr==1?SUCCESS:ERROR;
        }
        return rst;//-2    -1

    }

    //重置密码
    @Override
    public int resetPassword(String id) {
        User user = dao.selectById(id);
        int result = 0;
        if(UtilValidate.isNotEmpty(user)){
            String salt = RandomStringUtils.randomAlphanumeric(20);//生成盐
            String pass2=MD5Util.Md5Hex("123456"+salt);//数据库中新密码
            user.setSalt(salt);
            user.setPassword(pass2);
            result = userMapper.updateById(user);
            return result;
        }
        return result;
    }

    /**
     * @Author zjw
     * @Description 添加用户/教官
     * @Date 10:10 2018/12/19
     * @Param [user]
     * @return int
    **/
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result addUser(User user) {
        String salt = RandomStringUtils.randomAlphanumeric(20);//生成盐
        String pass2=MD5Util.Md5Hex(user.getPassword()+salt);//数据库中新密码
        user.setSalt(salt);
        user.setPassword(pass2);
        user.setIsAdmin(1);//默认不为超级管理员
        if(user.getIdentify().equals("1")){
            user.setId(GetUUID.getUUIDs("T"));
        }
        user.setId(GetUUID.getUUIDs("U"));
        if(UtilValidate.isEmpty(user.getUserName())){
            return  Result.error("名字不能用空");
        }
        user.setFullName(user.getUserName());
        if(UtilValidate.isEmpty(user.getUserCode())){
            return  Result.error("用户账号不能为空");
        }

        if(UtilValidate.isEmpty(user.getUserPoliceId())){
            return  Result.error("警号不能为空");
        }

        if(UtilValidate.isEmpty(user.getUserPoliceId())){
            return  Result.error("警号不能为空");
        }

        if(UtilValidate.isEmpty(user.getPassword())){
            return  Result.error("密码不能为空");
        }
        user.setCreateTime(new Date());

        String roles=user.getRoles();
        if(UtilValidate.isNotEmpty(roles)){
            String[] strs = roles.split(",");
            String uid = user.getId();
            Arrays.stream(strs).forEach(e->{
                SysUserRole ur=new SysUserRole();
                ur.setId(GetUUID.getUUIDs("UR"));
                ur.setUserId(uid);
                ur.setRoleId(e);
                sysUserRoleDao.insert(ur);
            });

        }
        int i = userMapper.insert(user);
        return Result.ok();
    }

    /**
     * @Author zjw
     * @Description 修改用户
     * @Date 16:38 2018/12/24
     * @Param [user]
     * @return boolean
    **/
    @Override
    public  int updateUser(User user) {
        user.setIsAdmin(1);
        int integer = userMapper.update(user, new EntityWrapper<User>().eq("ID", user.getId()));
        return integer==1?SUCCESS:ERROR;
    }

    @Override
    public int changeIdentify(String id, String identify) {
        User user = new User();
        user.setId(id);
        user.setIsAdmin(1);
        if("0".equals(identify)){
            user.setIdentify("1");
        }else if("1".equals(identify)){
            user.setIdentify("0");
        }
        return userMapper.updateById(user);
    }
}
