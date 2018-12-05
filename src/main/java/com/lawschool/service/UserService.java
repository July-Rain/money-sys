package com.lawschool.service;

import com.baomidou.mybatisplus.service.IService;
import com.lawschool.beans.SysLogEntity;
import com.lawschool.beans.User;
import com.lawschool.beans.UserTest;
import com.lawschool.util.PageUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface UserService  extends IService<User> {
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
     * @param userCode
     * @param password
     * @return
     */
    public int login(String userCode,String password,HttpServletRequest request);

    /**
     * 修改用户密码
     * @param userCode
     * @param password
     * @param newPassword
     * @return
     */
    public int updatePassword(String userCode, String password, String newPassword,HttpServletRequest request);

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
