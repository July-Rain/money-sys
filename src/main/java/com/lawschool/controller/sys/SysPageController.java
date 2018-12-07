package com.lawschool.controller.sys;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * ClassName: SysCommonController
 * Description: 系统通用请求处理
 * date: 2018-12-7 13:48
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
@Controller
public class SysPageController {

	@RequestMapping("modules/{module}/{url}.html")
	public String module(@PathVariable("module") String module, @PathVariable("url") String url){
		return  module + "/" + url;
	}

}
