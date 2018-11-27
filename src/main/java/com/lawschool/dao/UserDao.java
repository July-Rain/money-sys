package com.lawschool.dao;

import java.util.List;

import com.lawschool.beans.User;

public interface UserDao {

	public User selectUserById(Integer userId);
	
	public List<User> selectAllUsers();
	
	public void addUser(User user);
}
