package com.lawschool.controller.competition;

import com.lawschool.beans.competition.RecruitConfiguration;
import com.lawschool.service.competition.RecruitConfigurationService;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 *
 * @Descriptin  闯关配置
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
@Controller
@RequestMapping("/recruitConfiguration")
public class RecruitConfigurationController {

    @Autowired
    private RecruitConfigurationService recruitConfigurationService;


    //查询
    @RequestMapping("/list")
    public Result list(@RequestParam Map<String, Object> params){
        RecruitConfiguration recruitConfiguration = new RecruitConfiguration();

        return Result.ok().put("comOnlineList", recruitConfigurationService.findAll());
    }

    //根据id来找数据
    @RequestMapping("/info")
    public Result info(@RequestParam String id){

        RecruitConfiguration recruitConfiguration=recruitConfigurationService.info(id);
        return Result.ok().put("recruitConfiguration", recruitConfiguration);
    }
//
//
    //保存
    @RequestMapping("/save")
    public Result save(){


        //前提 要前端 传过来  看 是不是统一配置
        RecruitConfiguration recruitConfiguration=new RecruitConfiguration();
        recruitConfigurationService.save();//这边到时候和前端商量  传个json串
        return Result.ok();
    }

//    /**
//     * 删除
//     */
    @RequestMapping("/delete")
    public Result delete(@RequestParam String id){

        //因为数据库的结构  设计为有多少条数据 就是多少个 大关   没有一点多余数据    所以要删除的话 就是  全删   不存在 删一条的说法
        recruitConfigurationService.deleteAll();
System.out.println();
        return Result.ok();
    }
//
//    //根据id来找数据    //修改功能待定
//    @RequestMapping("/update")
//    public Result update(@RequestParam Map<String, Object> params){
//
//        //不要更改
//        recruitConfigurationService.updateAll();
//        return Result.ok();
//    }


}
