package com.lawschool.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.beans.ExamConfig;
import com.lawschool.beans.ExamQueConfig;
import com.lawschool.beans.ExamQuestions;
import com.lawschool.dao.ExamConfigDao;
import com.lawschool.service.ExamConfigService;
import com.lawschool.util.PageUtils;

@Service
public class ExamConfigServiceImpl extends ServiceImpl<ExamConfigDao, ExamConfig> implements ExamConfigService{

	@Autowired
	private ExamConfigDao examConfigDao;

	
	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @Description:考试配置页面请求
	 * @Pwarms:@param type 预览试卷or生成试卷
	 * @Pwarms:@param examConfig
	 * @Pwarms:@param list
	 * @return:void
	 */
	public void examConfig(String type,ExamConfig examConfig,List<ExamQueConfig> examQueList,List<ExamQuestions> queList) {
		if("1".equals(type)) {
			//预览试卷
			preview(examConfig,examQueList,queList);
		}else {
			//生成试卷
			generate();
		}
	}

	private void generate() {
		// TODO Auto-generated method stub
		
	}

	//预览试卷
	private List<ExamQuestions> preview(ExamConfig examConfig,List<ExamQueConfig> examQueList,List<ExamQuestions> queList) {
		//获取考试出题类型
		examConfig.setId(IdWorker.getIdStr());
		if("1".equals(examConfig.getQuestionWay())) {
			//随机出题
			//获取题目配置规则
			List<ExamQuestions> eqList = new ArrayList<ExamQuestions>();
			for (ExamQueConfig examQueConfig : examQueList) {
				//根据配置从题库中获取
				
				
			}
		}
		return null;
	}

	
}
