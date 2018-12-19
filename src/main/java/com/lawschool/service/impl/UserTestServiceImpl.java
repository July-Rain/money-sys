package com.lawschool.service.impl;

import java.util.List;

import com.lawschool.dao.UserTestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawschool.beans.UserTest;
import com.lawschool.service.UserTestService;
import com.lawschool.dao.UserTestDao;

@Service
public class UserTestServiceImpl implements UserTestService{
	
	@Autowired
	private UserTestDao userDao;
	
	public UserTest selectUserById(Integer userId) {
		// TODO Auto-generated method stub
//		return userDao.selectUserById(userId);
		return null;
	}

	public List<UserTest> selectAllUsers() {
		// TODO Auto-generated method stub
		return userDao.selectAllUsers();
	}

	public void addUser(UserTest user) {
		// TODO Auto-generated method stub
		//userDao.addUser(user);
	}

}
