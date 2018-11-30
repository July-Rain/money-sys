package com.lawschool.annotation;

import com.alibaba.fastjson.JSON;
import com.lawschool.beans.SysLogEntity;
import com.lawschool.beans.User;
import com.lawschool.service.SysLogService;
import com.lawschool.util.GetUUID;
import com.lawschool.util.HttpContextUtils;
import com.lawschool.util.IpUtil;
import com.lawschool.util.UtilValidate;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;


@Aspect
@Component
public class SysLogAspect extends HandlerInterceptorAdapter {
	@Autowired
	private SysLogService sysLogService;
	
	@Pointcut("@annotation(com.lawschool.annotation.SysLog)")
	public void logPointCut() { 
		
	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		//执行方法
		Object result = point.proceed();
		//执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;

		//保存日志
		saveSysLog(point, time);

		return result;
	}

	private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		SysLogEntity sysLog = new SysLogEntity();
		sysLog.setId(GetUUID.getUUIDs("LO"));//
		SysLog syslog = method.getAnnotation(SysLog.class);
		if(syslog != null){
			//注解上的描述
			sysLog.setOperation(syslog.value());
		}

		//请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		sysLog.setMethod(className + "." + methodName + "()");

		//请求的参数
		Object[] args = joinPoint.getArgs();
		try{
			String params = JSON.toJSONString(args[0]);
			sysLog.setParams(params);
		}catch (Exception e){

		}

		//获取request
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		//设置IP地址
		sysLog.setIp(IpUtil.getIpAddr(request));

		//用户名
		User user=(User)request.getSession().getAttribute("user");
		String username ="";
		if(UtilValidate.isNotEmpty(user)){
			username =user.getUserName();
		}

		sysLog.setUsername(username);

		sysLog.setTime(time);
		sysLog.setCreateDate(new Date());
		//保存系统日志
		sysLogService.insert(sysLog);
	}
}
