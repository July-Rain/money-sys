package com.lawschool.controller;

import com.alibaba.fastjson.JSON;
import com.lawschool.service.SysGeneratorService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Query;
import com.lawschool.util.Result;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 生成代码管理
 */
@Controller
@RequestMapping("/sys/generator")
public class SysGeneratorController {
	@Autowired
	private SysGeneratorService sysGeneratorService;
	
	@ResponseBody
	@RequestMapping("/list")
	//@RequiresPermissions("sys:generator:list")
	public Result list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<Map<String, Object>> list = sysGeneratorService.queryList(query);
		int total = sysGeneratorService.queryTotal(query);
		PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getCurrPage());
		return Result.ok().put("page", pageUtil);
	}
	@RequestMapping("index")
	public String module(){
		return "generator";
	}
	@RequestMapping("/code")
	//@RequiresPermissions("sys:generator:create")
	public void code(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String[] tableNames = new String[]{};
		String tables = request.getParameter("tables");
		tableNames = JSON.parseArray(tables).toArray(tableNames);
		byte[] data = sysGeneratorService.generatorCode(tableNames);
		response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"law_school.zip\"");
        response.addHeader("Content-Length", "" + data.length);  
        response.setContentType("application/octet-stream; charset=UTF-8");  
        IOUtils.write(data, response.getOutputStream());
	}
}
