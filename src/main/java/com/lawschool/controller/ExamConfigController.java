package com.lawschool.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lawschool.beans.ExamConfig;
import com.lawschool.beans.ExamQueConfig;
import com.lawschool.beans.ExamQuestions;
import com.lawschool.service.impl.ExamConfigServiceImpl;
import com.lawschool.util.Result;

@Controller
@RequestMapping("/exam/config")
public class ExamConfigController {

	@Autowired
	private ExamConfigServiceImpl examConfigServiceImpl;

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
