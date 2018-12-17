package com.lawschool.controller.exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lawschool.form.CommonForm;
import com.lawschool.service.DictService;
import com.lawschool.service.system.TopicTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

	@Autowired
	private DictService dictService;

	@Autowired
	private TopicTypeService topicTypeService;
	
	@RequestMapping("/list")
	public Result list(@RequestParam Map<String, Object> params) {
		ExamConfig entity = new ExamConfig();
        Page<ExamConfig> page = examConfigService.findPage(new Page<ExamConfig>(params), entity);
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

	@RequestMapping(value="/dict",method = RequestMethod.GET)
	private Result dict(){
		//examType考试类型字典
		List<CommonForm> etOption = dictService.findByType("EXAM_TYPE");
		//groupForm组卷方式字典
		List<CommonForm> gfOption = dictService.findByType("GROUP_FORM");
		//isMustTest是否必考字典
		List<CommonForm> imtOption = dictService.findByType("IS_MUST_TEST");
		//questionWay出题方式字典
		List<CommonForm> qwOption = dictService.findByType("QUESTION_WAY");
		//topicOrderType题目/选项顺序字典
		List<CommonForm> otOption = dictService.findByType("ORDER_TYPE");
		//answerShowRule答案显示规则
		List<CommonForm> asuOption = dictService.findByType("ANSWER_SHOW_RULE");
		//reachRewardType达标奖励类型
		List<CommonForm> rrtOption = dictService.findByType("REACH_REWARD_TYPE");
		//checkType阅卷方式
		List<CommonForm> ctOption = dictService.findByType("CHECK_TYPE");
		//specilKnowledge专项知识
		List<CommonForm> skOption = topicTypeService.findAll(null);
		//题目类型
		List<CommonForm> qtOption = dictService.findByType("QUESTION_TYPE");
		return Result.ok().put("etOption", etOption)
				.put("gfOption", gfOption)
				.put("imtOption", imtOption)
				.put("qwOption", qwOption)
				.put("otOption", otOption)
				.put("asuOption", asuOption)
				.put("rrtOption", rrtOption)
				.put("ctOption", ctOption)
				.put("skOption", skOption)
				.put("qtOption",qtOption);
	}

}
