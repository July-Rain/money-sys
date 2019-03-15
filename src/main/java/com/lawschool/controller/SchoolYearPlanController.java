package com.lawschool.controller;

import com.lawschool.annotation.SysLog;
import com.lawschool.base.AbstractController;
import com.lawschool.base.Page;
import com.lawschool.beans.SchoolYearPlan;
import com.lawschool.service.SchoolYearPlanService;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName SchoolYearPlanController
 * @Author wangtongye
 * @Date 2018/12/26 14:55
 * @Versiom 1.0
 **/
@RestController
@RequestMapping("/schoolYearPlan")
public class SchoolYearPlanController extends AbstractController {

    @Autowired
    private SchoolYearPlanService schoolYearPlanService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(@RequestParam Map<String, Object> param){
        Page<SchoolYearPlan> page = schoolYearPlanService.findPage(new Page<SchoolYearPlan>(param), new SchoolYearPlan());
        return Result.ok().put("page", page);
    }

    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public Result info(@PathVariable("id") String id){
        SchoolYearPlan  schoolYearPlan = schoolYearPlanService.findOne(id);
        return Result.ok().put("data", schoolYearPlan);
    }

    @SysLog("保存学年目标")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestBody SchoolYearPlan entity){
//        schoolYearPlanService.save(entity);
        int i=  schoolYearPlanService.insertsave(entity);
        if(i==0)
        {
            return Result.ok().put("code",1).put("msg","失败，输入的时间区间和已添加的时间段有交集");
        }



        return Result.ok();
    }

    @SysLog("删除学年目标")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result delete(@RequestBody List<String> ids){
        schoolYearPlanService.delete(ids);
        return Result.ok();
    }

    @RequestMapping(value = "/yearPlan", method = RequestMethod.GET)
    public Result yearPlan(){
        SchoolYearPlan schoolYearPlan = schoolYearPlanService.findYearPlanByNow(getUser().getId());
        System.out.println(getUser().getId());
        if(schoolYearPlan == null){
            schoolYearPlan = new SchoolYearPlan();
            schoolYearPlan.setCredit("0");
            schoolYearPlan.setIntegral("0");
        }
        return Result.ok().put("data", schoolYearPlan);
    }
}
