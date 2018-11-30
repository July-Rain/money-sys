package com.lawschool.controller;

import com.lawschool.beans.RecruitCheckpointConfiguration;
import com.lawschool.beans.RecruitConfiguration;
import com.lawschool.service.RecruitCheckpointConfigurationService;
import com.lawschool.service.RoleService;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 *
 * @Descriptin  闯关关卡
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
@Controller
@RequestMapping("/recruitCheckpointConfiguration")
public class RecruitCheckpointConfigurationController {

    @Autowired
    private RecruitCheckpointConfigurationService recruitCheckpointConfigurationService;


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
}
