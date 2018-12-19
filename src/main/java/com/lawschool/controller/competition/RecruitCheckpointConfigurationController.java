package com.lawschool.controller.competition;

import com.lawschool.beans.competition.RecruitCheckpointConfiguration;
import com.lawschool.form.QuestForm;
import com.lawschool.service.TestQuestionService;
import com.lawschool.service.competition.RecruitCheckpointConfigurationService;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @Descriptin  闯关关卡
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
@RestController
@RequestMapping("/recruitCheckpointConfiguration")
public class RecruitCheckpointConfigurationController {

    @Autowired
    private RecruitCheckpointConfigurationService recruitCheckpointConfigurationService;

    @Autowired
    private TestQuestionService testQuestionService;
    //查询
    @RequestMapping("/list")
    public Result list(@RequestParam Map<String, Object> params){
        RecruitCheckpointConfiguration recruitCheckpointConfiguration = new RecruitCheckpointConfiguration();

        return Result.ok().put("comOnlineList", recruitCheckpointConfigurationService.findAll());
    }

    //根据id来找数据
    @RequestMapping("/info")
    public Result info(@RequestParam String id){

        RecruitCheckpointConfiguration recruitCheckpointConfiguration=recruitCheckpointConfigurationService.info(id);
        return Result.ok().put("recruitConfiguration", recruitCheckpointConfiguration);
    }
    //
//
    //保存
    @RequestMapping("/save")
    public Result save(@RequestParam Map<String, Object> params){

        RecruitCheckpointConfiguration recruitCheckpointConfiguration=new RecruitCheckpointConfiguration();
        recruitCheckpointConfigurationService.save(recruitCheckpointConfiguration);
        return Result.ok();
    }

    //查询
    @RequestMapping("/getQuestByids")
    public Result getQuestByids(){
        //先写死玩玩  取2题
         List<QuestForm> Questlist= recruitCheckpointConfigurationService.getQuestByids();

        return Result.ok().put("Questlist", Questlist);
    }
}
