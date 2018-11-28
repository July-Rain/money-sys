package com.lawschool.service.impl;

import com.lawschool.beans.User;
import com.lawschool.beans.UserExample;
import com.lawschool.dao.UserMapper;
import com.lawschool.service.UserService;
import com.lawschool.util.MD5Util;
import com.lawschool.util.UtilValidate;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

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
    public List<User> selectAllUsers() {
        UserExample example=new UserExample();
        List<User> users = userMapper.selectByExample(example);
        return users;
    }

    @Override
    public int updateUserStatus(String userId, Integer nowStatus, Integer updateStatus) {
        UserExample example=new UserExample();
        example.createCriteria().andUserIdEqualTo(userId).andUserStatusEqualTo(nowStatus);
        User use=new User();
        use.setUserStatus(updateStatus);
        int res=userMapper.updateByExampleSelective(use,example);
        return 0;
    }

    @Override
    public int updateUserOnlineStatus(String userId, String nowStatus, String updateStatus) {
        UserExample example=new UserExample();
        example.createCriteria().andUserIdEqualTo(userId).andIsOnlineEqualTo(nowStatus);
        User use=new User();
        use.setIsOnline(updateStatus);
        int res=userMapper.updateByExampleSelective(use,example);
        return 0;
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
                return 0;//登录成功
            }
            return -2;//密码错误
        }
        return -1;//用户不存在

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
            userMapper.updateByExampleSelective(user,example);//修改密码
        }
        return rst;//-2    -1

    }

    @Override
    public int addUser(User user) {
        String salt = RandomStringUtils.randomAlphanumeric(20);//生成盐
        String pass2=MD5Util.Md5Hex(user.getPassword()+salt);//数据库中新密码
        user.setSalt(salt);
        user.setPassword(pass2);
        int i = userMapper.insertSelective(user);
        return 0;
    }

    @Override
    public List<User> selectOnlineUser() {
        UserExample example=new UserExample();
        example.createCriteria().andIsOnlineEqualTo("1");
        List<User> users = userMapper.selectByExample(example);
        return  users;

    }


}
