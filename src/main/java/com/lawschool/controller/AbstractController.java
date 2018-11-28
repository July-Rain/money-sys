package com.lawschool.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class AbstractController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/*protected SysUserEntity getUser() {
		return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
	}

	protected String getUserId() {

		return getUser().getUserId();
	}

	protected Long getDeptId() {
		return getUser().getDeptId();
	}*/
}
