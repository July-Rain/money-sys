package com.lawschool.controller.competition;

import com.lawschool.beans.competition.CompetitionRecord;
import com.lawschool.service.competition.CompetitionRecordService;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 *
 * @Descriptin  竞赛记录
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
@Controller
@RequestMapping("/competitionRecord")
public class CompetitionRecordController {

    @Autowired
    private CompetitionRecordService competitionRecordService;


    //查询
    @ResponseBody
    @RequestMapping("/list")
    public Result list(@RequestParam Map<String, Object> params){
        CompetitionRecord competitionRecord = new CompetitionRecord();

        return Result.ok().put("competitionRecordList", competitionRecordService.findAll());
    }

    //根据id来找数据
    @RequestMapping("/info")
    public Result info(@RequestParam String id){

        CompetitionRecord competitionRecord=competitionRecordService.info(id);
        return Result.ok().put("competitionRecord", competitionRecord);
    }
    //
//
    //保存
    @RequestMapping("/save")
    public Result save(){

        CompetitionRecord competitionRecord=new CompetitionRecord();
        competitionRecordService.save();//这边到时候和前端商量  传个json串
        return Result.ok();
    }

    //    /**
//     * 删除
//     */
    @RequestMapping("/delete")
    public Result delete(@RequestParam String id){

        //因为数据库的结构  设计为有多少条数据 就是多少个 大关   没有一点多余数据    所以要删除的话 就是  全删   不存在 删一条的说法
        competitionRecordService.deleteId(id);

        return Result.ok();
    }
//
//    //根据id来找数据
    @RequestMapping("/update")
    public Result update(@RequestParam Map<String, Object> params){


        competitionRecordService.updatedata();
        return Result.ok();
    }




}
