package com.lawschool.service.exam;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.lawschool.base.AbstractService;
import com.lawschool.beans.TestQuestions;
import com.lawschool.beans.exam.ExamConfig;
import com.lawschool.form.QuestForm;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;

/***
 *
 * @Title:ExamConfigService.java
 * @Description: 考试配置接口
 * @author: 王帅奇
 * @date 2018年11月29日
 */
public interface ExamConfigService extends AbstractService<ExamConfig> {

    PageUtils queryPage(Map<String, Object> params);

    List<QuestForm> preview(ExamConfig examConfig) throws Exception;

    void generate(ExamConfig examConfig) throws Exception;
}
