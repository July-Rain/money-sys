package com.lawschool.controller.exam;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lawschool.beans.exam.ExamConfig;
import com.lawschool.beans.exam.ExamQueConfig;
import com.lawschool.beans.exam.ExamQuestions;
import com.lawschool.service.impl.exam.ExamConfigServiceImpl;
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
