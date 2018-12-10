package com.lawschool.controller.exam;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.base.Page;
import com.lawschool.beans.SysConfig;
import com.lawschool.beans.TestQuestions;
import com.lawschool.beans.exam.ExamConfig;
import com.lawschool.beans.exam.ExamQueConfig;
import com.lawschool.beans.exam.ExamQuestions;
import com.lawschool.service.exam.ExamConfigService;
import com.lawschool.service.impl.exam.ExamConfigServiceImpl;
import com.lawschool.util.Result;

/**
 * 
 * @Title:ExamConfigController.java
 * @Description: 考试配置
 * @author: 王帅奇
 * @date 2018年12月10日
 */
@RestController
@RequestMapping("/exam/config")
public class ExamConfigController {

	@Autowired
	private ExamConfigServiceImpl examConfigServiceImpl;
	
	@Autowired
	private ExamConfigService examConfigService;

	
	@RequestMapping("/list")
	public Result list(@RequestParam Map<String, Object> params) {

//        String typeId = (String) params.get("typeId");
//        String questionDifficulty = (String) params.get("questionDifficulty");
//        String questionType = (String) params.get("questionType");
//        String isEnble = (String) params.get("isEnble");
		EntityWrapper<ExamConfig> ew = new EntityWrapper<>();
		ExamConfig ec = new ExamConfig();
		ew.setEntity(ec);
        Page<ExamConfig> page = examConfigService.findPage(new Page<ExamConfig>(params), ew);
		return Result.ok().put("page", page);
	}
	
	@ResponseBody
	@RequestMapping("/examConfig")
	private Result examConfig(String type,ExamConfig examConfig,List<ExamQueConfig> examQueList,List<ExamQuestions> queList) {
		
		try {
			examConfigServiceImpl.examConfig(type,examConfig,examQueList,queList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Result.ok();
	} 
}
