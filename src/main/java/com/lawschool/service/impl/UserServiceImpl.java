package com.lawschool.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.beans.User;
import com.lawschool.beans.UserExample;
import com.lawschool.dao.UserMapper;
import com.lawschool.service.UserService;
import com.lawschool.util.Constant;
import com.lawschool.util.MD5Util;
import com.lawschool.util.PageUtils;
import com.lawschool.util.UtilValidate;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.lawschool.util.Constant.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;


    @Override
    public User selectUserByUserId(String userId) {
        UserExample example=new UserExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<User> users = userMapper.selectByExample(example);
        if(UtilValidate.isEmpty(users)){
            return null;
        }
        return users.get(0);
    }

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

        List<User> users = userMapper.selectPage(page, new EntityWrapper<User>());

        PageUtils pageUtils=new PageUtils(users,users.size(),pageNo,pageSize);
        return pageUtils;
    }

    @Override
    public int updateUserStatus(String userId, Integer nowStatus, Integer updateStatus) {
        UserExample example=new UserExample();
        example.createCriteria().andUserIdEqualTo(userId).andUserStatusEqualTo(nowStatus);
        User use=new User();
        use.setUserStatus(updateStatus);
        int res=userMapper.updateByExampleSelective(use,example);
        return res==1? SUCCESS:ERROR;
    }

    @Override
    public int updateUserOnlineStatus(String userId, String nowStatus, String updateStatus) {
        UserExample example=new UserExample();
        example.createCriteria().andUserIdEqualTo(userId).andIsOnlineEqualTo(nowStatus);
        User use=new User();
        use.setIsOnline(updateStatus);
        int res=userMapper.updateByExampleSelective(use,example);
        return res==1? SUCCESS:ERROR;
    }

    @Override
    public int login(String userId,String password) {
        UserExample example=new UserExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<User> users = userMapper.selectByExample(example);
        if(users!=null && users.size()>0){
            User user=users.get(0);
            String salt=user.getSalt();
            String pass1 = MD5Util.Md5Hex(password + salt);
            if(user.getPassword().equals(pass1)){
                return SUCCESS;//登录成功
            }
            return ERROR_PSW;//密码错误
        }
        return IS_NOT_EXIST;//用户不存在
    }

    @Override
    public int updatePassword(String userId, String password, String newPassword) {
        int rst = this.login(userId, password);
        if(rst==0){//用户存在
            UserExample example=new UserExample();
            example.createCriteria().andUserIdEqualTo(userId);
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

    @Override
    public int addUser(User user) {
        String salt = RandomStringUtils.randomAlphanumeric(20);//生成盐
        String pass2=MD5Util.Md5Hex(user.getPassword()+salt);//数据库中新密码
        user.setSalt(salt);
        user.setPassword(pass2);
        user.setId(IdWorker.get32UUID());
        int i = userMapper.insert(user);
        return 0;
    }

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
        List<User> users = userMapper.selectPage(page, new EntityWrapper<User>().eq("IS_ONLINE",1));
        PageUtils pageUtils=new PageUtils(users,users.size(),pageNo,pageSize);
        return pageUtils;
    }


}
