package com.lawschool.controller;

import com.lawschool.beans.SysConfig;
import com.lawschool.service.SysConfigService;
import com.lawschool.service.UserService;
import com.lawschool.util.Constant;
import com.lawschool.util.GetUUID;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author MengyuWu
 * @Description 登陆页面
 * @Date 15:18 2018-12-5
 * @Param 
 * @return 
 **/

@Controller
public class SysLoginController {
	/**
	 * request对象
	 */
	@Autowired
	protected HttpServletRequest request;
	@Autowired
	private UserService userService;
	@Autowired
	private SysConfigService configService;



	/**
	 * @Author MengyuWu
	 * @Description 登陆
	 * @Date 15:18 2018-12-5
	 * @Param [username, password]
	 * @return com.lawschool.util.Result
	 **/
	
	@ResponseBody
	@RequestMapping(value = "/sys/login", method = RequestMethod.POST)
	public Result login(String username, String password) {

		int code = userService.login(username,password,request);
		if(Constant.SUCCESS==code){
			return Result.ok();
			//hhh
		} else if (Constant.ERROR_PSW==code) {
			return Result.error("密码错误");
		}else if(Constant.IS_NOT_EXIST==code){
			return Result.error("用户不存在");
		}
		return Result.ok();
	}

	/**
	 * @Author MengyuWu
	 * @Description 退出系统
	 * @Date 15:18 2018-12-5
	 * @Param []
	 * @return java.lang.String
	 **/
	
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout() {
		request.getSession().setAttribute("user", null);
		return "redirect:login.html";
	}
	
	/**
	 * @Author MengyuWu
	 * @Description 进入首页
	 * @Date 15:19 2018-12-5
	 * @Param [model]
	 * @return java.lang.String
	 **/
	
	@RequestMapping(value = "main.html")
	public String main(Model model) {
		model.addAttribute("userName","小明");
		model.addAttribute("userAge",23);
		return "main";
	}


	@RequestMapping(value = "{pageName}.html")
	public String enterPage1(@PathVariable String pageName) {
		return pageName;
	}

	@RequestMapping(value = "{path}/{pageName}.html")
	public String enterPage2(@PathVariable String pageName,@PathVariable String path) {
		return path+"/"+pageName;
	}
}
