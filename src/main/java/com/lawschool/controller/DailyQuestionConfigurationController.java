package com.lawschool.controller;

import com.lawschool.annotation.SysLog;
import com.lawschool.base.Page;
import com.lawschool.beans.DailyQuestionConfiguration;
import com.lawschool.form.QuestForm;
import com.lawschool.service.AnswerService;
import com.lawschool.service.DailyQuestionConfigurationService;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 每日一题管理
 * @author liuhuan
 */
@RestController
@RequestMapping("/dailyQuestion")
public class DailyQuestionConfigurationController {
    @Autowired
    DailyQuestionConfigurationService dailyQuestionConfigurationService;


    /**
     * 展示配置列表
     * @param params
     * @return
     */
    @SysLog("展示每日一题配置")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Result findPage(@RequestParam Map<String, Object> params){
        DailyQuestionConfiguration entity = new DailyQuestionConfiguration();
        Page<DailyQuestionConfiguration> page = dailyQuestionConfigurationService.findPage(new Page<DailyQuestionConfiguration>(params),entity);
        return Result.ok().put("page",page);
    }

    /**
     * 查询配置
     */
    @SysLog("查询配置")
    @RequestMapping("/info")
    public Result info(String id){
        DailyQuestionConfiguration entity = dailyQuestionConfigurationService.selectByDailyId(id);
        return Result.ok().put("data",entity);
    }

    /**
     * 删除配置
     */
    @SysLog("删除每日一题规则")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Result deleteById(@RequestParam String id){
        dailyQuestionConfigurationService.deleteByDailyId(id);
        return Result.ok();
    }

    /**
     * 新增配置
     */
    @SysLog("新增每日一题规则")
    @RequestMapping("/insert")
    public Result insert(@RequestBody DailyQuestionConfiguration dailyQuestionConfiguration){
         int i=  dailyQuestionConfigurationService.insertDailyConfig(dailyQuestionConfiguration);
         if(i==0)
         {
            return Result.ok().put("code",1).put("msg","添加失败，输入的时间区间和已添加的时间段有交集");
         }
        return Result.ok();
    }

    /**
     * 修改配置
     */
    @SysLog("修改配置")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result updateDailyConfig(@RequestBody DailyQuestionConfiguration dailyQuestionConfiguration){
        dailyQuestionConfigurationService.updateByDailyConfig(dailyQuestionConfiguration);
        return Result.ok().put("id",dailyQuestionConfiguration.getId());
    }

    /**
     * 每日一题题目展示
     */
    @SysLog("题目展示")
    @RequestMapping(value = "/showDailyTest",method = RequestMethod.POST)
    public Result showTest(){
        Result result = dailyQuestionConfigurationService.dailyTestCreate();
        return result;
    }
}
