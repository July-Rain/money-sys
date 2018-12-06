package com.lawschool.base;

import com.lawschool.beans.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public abstract class AbstractController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * @Author MengyuWu
	 * @Description 获取HttpServletRequest
	 * @Date 15:43 2018-11-29
	 * @Param []
	 * @return javax.servlet.http.HttpServletRequest
	 **/
	
	public HttpServletRequest getRequest()
	{
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	/**
	 * @Author MengyuWu
	 * @Description 获取HttpServletResponse
	 * @Date 15:43 2018-11-29
	 * @Param []
	 * @return javax.servlet.http.HttpServletResponse
	 **/
	
	public HttpServletResponse getResponse()
	{
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
	}

	/**
	 * @Author MengyuWu
	 * @Description 获取HttpSession
	 * @Date 15:43 2018-11-29
	 * @Param []
	 * @return javax.servlet.http.HttpSession
	 **/
	
	public HttpSession getHttpSession()
	{
		return getRequest().getSession();
	}

	/**
	 * @Author MengyuWu
	 * @Description  获取Session中用户信息
	 * @Date 15:43 2018-11-29
	 * @Param 
	 * @return 
	 **/

	protected User getUser() {
		return (User) getHttpSession().getAttribute("user");
	}

}
