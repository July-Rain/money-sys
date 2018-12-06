package com.lawschool.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lawschool.beans.User;
import com.lawschool.beans.UserExample;
import com.lawschool.dao.UserMapper;
import com.lawschool.service.UserService;
import com.lawschool.util.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static com.lawschool.util.Constant.*;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;


    //获取用户用户/教官
    @Override
    public User selectUserByUserId(String id) {
        return userMapper.selectById(id);
    }

    //查询所有的用户/教官
    @Override
    public PageUtils selectAllUsers(Map<String,Object> params) {
        int pageNo=1;
        int pageSize=10;
        if(UtilValidate.isNotEmpty(params.get("pageNo"))){
            pageNo=Integer.parseInt((String) params.get("pageNo"));
        }
        if(UtilValidate.isNotEmpty(params.get("pageSize"))){
            pageSize=Integer.parseInt((String) params.get("pageSize"));
        }

        Page<User> page=new Page<User>(pageNo,pageSize);

        EntityWrapper<User> ew = new EntityWrapper<>();//默认所有的用户
        if(UtilValidate.isNotEmpty(params.get("identify"))){
            ew.eq("IDENTIFY",params.get("identify"));// 0-普通用户  1-教官
        }
        List<User> users = userMapper.selectPage(page,ew);

        PageUtils pageUtils=new PageUtils(users,users.size(),pageNo,pageSize);
        return pageUtils;
    }

    //修改用户/教官（禁用。恢复）
    @Override
    public int updateUserStatus(String userId, Integer nowStatus, Integer updateStatus) {
        UserExample example=new UserExample();
        example.createCriteria().andUserIdEqualTo(userId).andUserStatusEqualTo(nowStatus);
        User use=new User();
        use.setUserStatus(updateStatus);
        int res=userMapper.updateByExampleSelective(use,example);
        return res==1? SUCCESS:ERROR;
    }

    //修改用户在线状态与否
    @Override
    public int updateUserOnlineStatus(String userId, String nowStatus, String updateStatus) {
        EntityWrapper<User> ew=new EntityWrapper<>();
        ew.eq("ID",userId).eq("IS_ONLINE",nowStatus).eq("IDENTIFY",0);
        User use=new User();
        use.setIsOnline(updateStatus);
        int res=userMapper.update(use,ew);
        return res==1? SUCCESS:ERROR;
    }

    //登录
    @Override
    public int login(String userCode,String password,HttpServletRequest request) {
        UserExample example=new UserExample();
        example.createCriteria().andUserCodeEqualTo(userCode);
        List<User> users = userMapper.selectByExample(example);
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

    //修改密码
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

    //添加用户/教官
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

    //获取在线用户
    @Override
    public PageUtils selectOnlineUser(Map<String,Object> params) {
        int pageNo=1;
        int pageSize=10;
        if(UtilValidate.isNotEmpty(params.get("pageNo"))){
            pageNo=Integer.parseInt((String) params.get("pageNo"));
        }
        if(UtilValidate.isNotEmpty(params.get("pageSize"))){
            pageSize=Integer.parseInt((String) params.get("pageSize"));
        }

        Page<User> page=new Page<User>(pageNo,pageSize);
        List<User> users = userMapper.selectPage(page, new EntityWrapper<User>().eq("IS_ONLINE",1).eq("identify",0));
        PageUtils pageUtils=new PageUtils(users,users.size(),pageNo,pageSize);
        return pageUtils;
    }
}
