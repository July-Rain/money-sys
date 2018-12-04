package com.lawschool.service;

import com.lawschool.beans.User;
import com.lawschool.beans.UserTest;
import com.lawschool.util.PageUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface UserService {
    /**
     * 根据userId查询用户信息
	 * @param userId
	 * @return
      */
    public User selectUserByUserId(String userId);

    /**
     * 获取全部用户集合
     * @return
     */
    public PageUtils selectAllUsers(Map<String,Object> params);


    /**
     * 修改用户状态（注销的话2000-》800    恢复800-》2000）
     * @param userId
     * @param nowStatus
     * @param updateStatus
     * @return
     */
    public int updateUserStatus(String userId,Integer nowStatus,Integer updateStatus);

    /**
     * 修改用户在线状态（下线0 上线1）
     * @param userId
     * @param nowStatus
     * @param updateStatus
     * @return
     */
    public int updateUserOnlineStatus(String userId,String nowStatus,String updateStatus);

    /**
     * 用户登录
     * @param userId
     * @param password
     * @return
     */
    public int login(String userId,String password);

    /**
     * 修改用户密码
     * @param userId
     * @param password
     * @param newPassword
     * @return
     */
    public int updatePassword(String userId, String password, String newPassword);

    /**
     * 添加用户
     * @param user
     * @return
     */
    public int addUser(User user);

    /**
     * 获取所有在线用户
     * @return
     */
    public PageUtils  selectOnlineUser(Map<String,Object> params);
}
