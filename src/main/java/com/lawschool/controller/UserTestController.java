package com.lawschool.controller;

import javax.annotation.Resource;

import com.lawschool.beans.UserTest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lawschool.service.UserTestService;

@Controller
public class UserTestController {
	
	@Resource
	private UserTestService userService;
	
	@RequestMapping("/")
	public ModelAndView getIndex(){
		
		ModelAndView mav=new ModelAndView("index");
		UserTest user=userService.selectUserById(1);
		mav.addObject("user",user);
		return mav;
	}

}
