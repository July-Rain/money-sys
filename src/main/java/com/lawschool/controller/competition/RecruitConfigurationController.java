package com.lawschool.controller.competition;

import com.lawschool.base.AbstractController;
import com.lawschool.beans.TestQuestions;
import com.lawschool.beans.User;
import com.lawschool.beans.competition.RecruitCheckpointConfiguration;
import com.lawschool.beans.competition.RecruitConfiguration;
import com.lawschool.form.CommonForm;
import com.lawschool.service.competition.RecruitConfigurationService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.AbstractCollection;
import java.util.List;
import java.util.Map;

/**
 *
 * @Descriptin  闯关配置
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
@RestController
@RequestMapping("/recruitConfiguration")
public class RecruitConfigurationController{

    @Autowired
    private RecruitConfigurationService recruitConfigurationService;



    //根据id来找数据
    @RequestMapping("/info")
    public Result info(@RequestParam String id){

        RecruitConfiguration recruitConfiguration=recruitConfigurationService.info(id);


        return Result.ok().put("recruitConfiguration", recruitConfiguration);
    }


    //查找所有数据
    @RequestMapping("/findAll")
    public Result findAll(){

         List<RecruitConfiguration> list=  recruitConfigurationService.findAll();

        return Result.ok().put("data", list);
    }
    //查找所有数据
    @RequestMapping("/findAll2")
    public Result findAll2(){

        List<RecruitConfiguration> list=  recruitConfigurationService.findAll2();

        return Result.ok().put("data", list);
    }
//
//
    //保存
    @RequestMapping("/save")
    public Result save(@RequestBody List<RecruitConfiguration> list){

        //去配置表里 找 对应的  积分   写在了servce层了



        //前提 要前端 传过来  (看 是不是统一配置)  先不考虑统一配置
        recruitConfigurationService.save(list);//这边到时候和前端商量  传个json串
        return Result.ok();
    }



    @RequestMapping("/list")
    public Result list(@RequestParam Map<String, Object> params){
        PageUtils page = recruitConfigurationService.queryPage(params);
        System.out.println(page);
        return Result.ok().put("page", page);
    }



    @RequestMapping("/getSonList")
    public Result getSonList(String id){
        List<RecruitCheckpointConfiguration> list= recruitConfigurationService.getSonList(id);

        return Result.ok().put("data", list);
    }




    //    /**
//     * 删除
//     */
    @RequestMapping("/delete")
    public Result delete(){

        //因为数据库的结构  设计为有多少条数据 就是多少个 大关   没有一点多余数据    所以要删除的话 就是  全删   不存在 删一条的说法
        recruitConfigurationService.deleteAll();

        return Result.ok();
    }

    @RequestMapping("/findAllTopic")
    public Result findAllTopic(){


        List<CommonForm> CommonFormList= recruitConfigurationService.findAllTopic();

        return Result.ok().put("data",CommonFormList);
    }


    @RequestMapping("/getQuest")
    public Result getQuest(@RequestBody RecruitConfiguration recruitConfiguration){


        List<TestQuestions>  testQuestionsList = recruitConfigurationService.getQuest(recruitConfiguration);

        return Result.ok().put("data",testQuestionsList);
    }

    //答过的题目入库保存
    @RequestMapping("/saveQuestion")
    public void saveQuestion(@RequestBody TestQuestions testQuestions,String myanswer){

        recruitConfigurationService.saveQuestion(testQuestions,myanswer);

    }
}
