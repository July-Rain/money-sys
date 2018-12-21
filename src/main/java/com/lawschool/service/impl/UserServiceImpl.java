package com.lawschool.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.User;
import com.lawschool.beans.UserExample;
import com.lawschool.dao.UserMapper;
import com.lawschool.service.UserService;
import com.lawschool.util.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.util.Password;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.lawschool.util.Constant.*;

@Service
public class UserServiceImpl extends AbstractServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;


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
    public PageUtils selectAllUsers(Map<String,Object> params) {

        int pageNo= Integer.parseInt( Optional.ofNullable(params.get("currPage")).orElse("1").toString());
        int pageSize=Integer.parseInt((String) Optional.ofNullable(params.get("pageSize")).orElse("10").toString());

        Page<User> page=new Page<>(pageNo,pageSize);

        EntityWrapper<User> ew = new EntityWrapper<>();//默认所有的用户


        //加 max  防止多条数据 报错
        ew.setSqlSelect("ID,ORG_CODE,USER_NAME,USER_POLICE_ID,USER_CODE,(select max(ORG_NAME) from law_org where ORG_CODE = ORG_CODE) as orgName");

        if(UtilValidate.isNotEmpty(params.get("identify"))){
            ew.eq("IDENTIFY",params.get("identify"));// 0-普通用户  1-教官
        }

        if(UtilValidate.isNotEmpty(params.get("orgCode"))){
            ew.eq("ORG_CODE",params.get("orgCode"));//部门
        }
        if(UtilValidate.isNotEmpty(params.get("userName"))){
            ew.like("USER_NAME", (String) params.get("userName"));//姓名

        }

        if(UtilValidate.isNotEmpty(params.get("userCode"))){
            ew.like("USER_CODE", (String) params.get("userCode"));//身份证号

        }

        if(UtilValidate.isNotEmpty(params.get("isOnline"))){
            ew.eq("IS_ONLINE",1);//1  在线
        }

        List<User> users = userMapper.selectPage(page,ew);

        PageUtils pageUtils=new PageUtils(users,users.size(),pageSize,pageNo);
        return pageUtils;
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
    public int login(String userCode,String password,HttpServletRequest request) {
        List<User> users = userMapper.selectList(new EntityWrapper<User>().eq("USER_CODE",userCode));
        if(users!=null && users.size()>0){
            User user=users.get(0);
            String salt=user.getSalt();
            String pass1 = MD5Util.Md5Hex(password + salt);
            if(user.getPassword().equals(pass1)){
                request.getSession().setAttribute("user", user);
                return SUCCESS;//登录成功
            }
            return ERROR_PSW;//密码错误
        }
        return IS_NOT_EXIST;//用户不存在
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
            UserExample example=new UserExample();
            example.createCriteria().andUserCodeEqualTo(userCode);
            User user=new User();
            String salt = RandomStringUtils.randomAlphanumeric(20);//生成盐
            String pass2=MD5Util.Md5Hex(newPassword+salt);//数据库中新密码
            user.setSalt(salt);
            user.setPassword(pass2);
            int resr = userMapper.updateByExampleSelective(user, example);//修改密码
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
    public int addUser(User user) {
        String salt = RandomStringUtils.randomAlphanumeric(20);//生成盐
        String pass2=MD5Util.Md5Hex(user.getPassword()+salt);//数据库中新密码
        user.setSalt(salt);
        user.setPassword(pass2);
        if(user.getIdentify().equals("1")){
            user.setId(GetUUID.getUUIDs("T"));
        }
        user.setId(GetUUID.getUUIDs("U"));
        int i = userMapper.insert(user);
        return 0;
    }
}
