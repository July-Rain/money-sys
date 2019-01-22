package com.lawschool.controller.system;

import com.lawschool.beans.system.SysMenuEntity;
import com.lawschool.service.system.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


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
	@Autowired
	private SysMenuService sysMenuService;

	@RequestMapping("modules/{module}/{url}.html")
	public String module(@PathVariable("module") String module, @PathVariable("url") String url, String id, Model model){
		model.addAttribute("menuId",id);
		List<SysMenuEntity> parentList = sysMenuService.queryParentById(id);
		model.addAttribute("menuId",id);
		return  module + "/" + url;
	}

}
