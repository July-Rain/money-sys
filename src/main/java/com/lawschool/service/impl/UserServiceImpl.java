package com.lawschool.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawschool.beans.User;
import com.lawschool.service.UserService;
import com.lawschool.dao.UserDao;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDao userDao;
	
	public User selectUserById(Integer userId) {
		// TODO Auto-generated method stub
		return userDao.selectUserById(userId);
	}

	public List<User> selectAllUsers() {
		// TODO Auto-generated method stub
		return userDao.selectAllUsers();
	}

	public void addUser(User user) {
		// TODO Auto-generated method stub
		//userDao.addUser(user);
	}

}
