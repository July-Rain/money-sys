package com.lawschool.service;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.User;
import com.lawschool.enums.UserGrade;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface UserService  extends AbstractService<User> {
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
    public PageUtils selectAllUsers(Map<String,String> params);


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
     * 用户登录
     * @param userCode
     * @param password
     * @return
     */
    public Result loginShiro(String userCode, String password, HttpServletRequest request);


    /**
     * 修改用户密码
     * @param userCode
     * @param password
     * @param newPassword
     * @return
     */
    public int updatePassword(String userCode, String password, String newPassword,HttpServletRequest request);

    /**
     * 密码重置
     * @param id
     * @return
     */
    public int resetPassword(String id);
    /**
     * 添加用户
     * @param user
     * @return
     */
    public Result addUser(User user);

    /**
     * @Author zjw
     * @Description 修改用户
     * @Date 16:37 2018/12/24
     * @Param [user]
     * @return boolean
    **/
     int updateUser(User user);

     /**
      * @Author MengyuWu
      * @Description 修改身份
      * @Date 19:28 2019-2-28
      * @Param [id, identify]
      * @return int
      **/
     
     int changeIdentify(String id,String identify,String flag);





      int  getAllusesCount();

      UserGrade getUserGradeByOrgCode(String orgCode);
}
