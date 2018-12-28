package com.lawschool.dao;

import java.util.List;

import com.lawschool.beans.UserTest;

//不用
public interface UserTestDao {

	public UserTest selectUserById(Integer userId);
	
	public List<UserTest> selectAllUsers();
	
	//public void addUser(User user);
	
}
