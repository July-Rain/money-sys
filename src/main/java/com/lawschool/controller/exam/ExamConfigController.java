package com.lawschool.controller.exam;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.lawschool.form.CheckSetForm;
import com.lawschool.form.CommonForm;
import com.lawschool.form.QuestForm;
import com.lawschool.service.system.DictionService;
import com.lawschool.service.system.TopicTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lawschool.beans.exam.ExamConfig;
import com.lawschool.service.exam.ExamConfigService;
import com.lawschool.util.Result;

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
    private DictionService dictionService;

    @Autowired
    private TopicTypeService topicTypeService;

    @RequestMapping("/list")
    public Result list(@RequestParam Map<String, Object> params) {
        Result result;
        try {
            result = examConfigService.getExamList(params);
        } catch (ParseException e) {
            return Result.error(e.getMessage());
        }
        return result;
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

        try {
            examConfigService.generate(examConfig);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
        return Result.ok();
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
        List<CommonForm> etOption = dictionService.findCodeByType("EXAM_TYPE");
        //groupForm组卷方式字典
        List<CommonForm> gfOption = dictionService.findCodeByType("GROUP_FORM");
        //isMustTest是否必考字典
        List<CommonForm> imtOption = dictionService.findCodeByType("IS_MUST_TEST");
        //questionWay出题方式字典
        List<CommonForm> qwOption = dictionService.findCodeByType("QUESTION_WAY");
        //topicOrderType题目/选项顺序字典
        List<CommonForm> otOption = dictionService.findCodeByType("ORDER_TYPE");
        //answerShowRule答案显示规则
        List<CommonForm> asuOption = dictionService.findCodeByType("ANSWER_SHOW_RULE");
        //reachRewardType达标奖励类型
        List<CommonForm> rrtOption = dictionService.findCodeByType("REACH_REWARD_TYPE");
        //checkType阅卷方式
        List<CommonForm> ctOption = dictionService.findCodeByType("CHECK_TYPE");
        //specilKnowledge专项知识
        List<CommonForm> skOption = topicTypeService.findAll(null);
        //题目类型
        List<CommonForm> qtOption = dictionService.findCodeByType("QUESTION_TYPE");
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