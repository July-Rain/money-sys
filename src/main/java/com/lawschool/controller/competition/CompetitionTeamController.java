package com.lawschool.controller.competition;


import com.lawschool.beans.competition.CompetitionTeam;
import com.lawschool.service.competition.CompetitionTeamService;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 *
 * @Descriptin 比武战队
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
@Controller
@RequestMapping("/competitionTeam")
public class CompetitionTeamController {

    @Autowired
    private CompetitionTeamService competitionTeamService;


    //查询
    @RequestMapping("/list")
    public Result list(@RequestParam Map<String, Object> params){
        CompetitionTeam competitionTeam = new CompetitionTeam();

        return Result.ok().put("competitionTeamList", competitionTeamService.findAll());
    }

    //根据id来找数据
    @RequestMapping("/info")
    public Result info(@RequestParam String id){

        CompetitionTeam competitionTeam=competitionTeamService.info(id);
        return Result.ok().put("competitionTeam", competitionTeam);
    }
    //
//
    //保存
    @RequestMapping("/save")
    public Result save(){



        CompetitionTeam competitionTeam=new CompetitionTeam();
        competitionTeamService.save();
        return Result.ok();
    }

    //    /**
//     * 删除
//     */
    @RequestMapping("/delete")
    public Result delete(@RequestParam String id){

        //因为数据库的结构  设计为有多少条数据 就是多少个 大关   没有一点多余数据    所以要删除的话 就是  全删   不存在 删一条的说法
        competitionTeamService.deleteId(id);

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
