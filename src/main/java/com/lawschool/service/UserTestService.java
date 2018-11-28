package com.lawschool.service;

import java.util.List;

import com.lawschool.beans.UserTest;


/**
 * 
 * @Descriptin  用户service
 * @author      马庆飞 
 * @version     v1.0
 * @Time        2018/11/27
 *
 */
public interface UserTestService {

	/**
	 * 根据userId查询用户信息
	 * @param userId
	 * @return
	 */
	public UserTest selectUserById(Integer userId);
	
	/**
	 * 获取全部用户集合
	 * @return
	 */
	public List<UserTest> selectAllUsers();
	
	/**
	 * 新增用户
	 * @param user
	 */
	public void addUser(UserTest user);
}
