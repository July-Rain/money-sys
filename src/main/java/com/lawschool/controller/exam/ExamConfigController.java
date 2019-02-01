package com.lawschool.controller.exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lawschool.form.CheckSetForm;
import com.lawschool.form.CommonForm;
import com.lawschool.form.QuestForm;
import com.lawschool.service.DictService;
import com.lawschool.service.system.TopicTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
import org.springframework.web.servlet.ModelAndView;

/**
 * @Title:ExamConfigController.java
 * @Description: 考试配置
 * @author: 王帅奇
 * @date 2018年12月10日
 */
@RestController
@RequestMapping("/exam/config")
public class ExamConfigController {

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

    @RequestMapping(value = "/examConfig/preview", method = RequestMethod.POST)
    private Result examConfigPre(@RequestBody ExamConfig examConfig) {

        Result res = new Result();
        try {
            List<QuestForm> list = examConfigService.preview(examConfig);
            return Result.ok().put("list", list);
        } catch (Exception e) {
            return  Result.error(e.getMessage());
        }
    }

    @RequestMapping(value = "/examConfig/generate", method = RequestMethod.POST)
    private Result examConfigGen(
            @RequestBody ExamConfig examConfig) {

        Result res = new Result();
        try {
            examConfigService.generate(examConfig);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }

    @RequestMapping(value = "/checkset", method = RequestMethod.POST)
    private  Result checkset(@RequestBody CheckSetForm checkSetForm){

        System.out.println(checkSetForm.toString());
        examConfigService.checkset(checkSetForm);
        return Result.ok();
    }
    @RequestMapping(value = "/dict", method = RequestMethod.GET)
    private Result dict() {
        //examType考试类型字典
        List<CommonForm> etOption = dictService.findCodeByType("EXAM_TYPE");
        //groupForm组卷方式字典
        List<CommonForm> gfOption = dictService.findCodeByType("GROUP_FORM");
        //isMustTest是否必考字典
        List<CommonForm> imtOption = dictService.findCodeByType("IS_MUST_TEST");
        //questionWay出题方式字典
        List<CommonForm> qwOption = dictService.findCodeByType("QUESTION_WAY");
        //topicOrderType题目/选项顺序字典
        List<CommonForm> otOption = dictService.findCodeByType("ORDER_TYPE");
        //answerShowRule答案显示规则
        List<CommonForm> asuOption = dictService.findCodeByType("ANSWER_SHOW_RULE");
        //reachRewardType达标奖励类型
        List<CommonForm> rrtOption = dictService.findCodeByType("REACH_REWARD_TYPE");
        //checkType阅卷方式
        List<CommonForm> ctOption = dictService.findCodeByType("CHECK_TYPE");
        //specilKnowledge专项知识
        List<CommonForm> skOption = topicTypeService.findAll(null);
        //题目类型
        List<CommonForm> qtOption = dictService.findCodeByType("QUESTION_TYPE");
        return Result.ok().put("etOption", etOption)
                .put("gfOption", gfOption)
                .put("imtOption", imtOption)
                .put("qwOption", qwOption)
                .put("otOption", otOption)
                .put("asuOption", asuOption)
                .put("rrtOption", rrtOption)
                .put("ctOption", ctOption)
                .put("skOption", skOption)
                .put("qtOption", qtOption);
    }

}
