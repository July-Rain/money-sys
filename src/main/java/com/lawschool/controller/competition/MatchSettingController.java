package com.lawschool.controller.competition;


import com.lawschool.beans.competition.BattleTopicSetting;
import com.lawschool.beans.competition.CompetitionOnline;
import com.lawschool.beans.competition.MatchSetting;
import com.lawschool.service.competition.MatchSettingService;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * @Descriptin 擂台配置
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
@RestController
@RequestMapping("/matchSetting")
public class MatchSettingController {

    @Autowired
    private MatchSettingService matchSettingService;

    @RequestMapping("/list")
    public Result list()
    {
        List<MatchSetting> list=  matchSettingService.list();
        return Result.ok().put("data",list);
    }
    /**
     * 删除
     */
    @RequestMapping("/deleteAll")
    public Result deleteAll(){

        matchSettingService.deleteAll();

        return Result.ok();
    }

    @RequestMapping("/save")
    public Result save(@RequestBody MatchSetting matchSetting){

        matchSettingService.save(matchSetting);
        return Result.ok();
    }

    @RequestMapping("/getSonList")
    public Result getSonList(String id){
        List<BattleTopicSetting> list= matchSettingService.getSonList(id);

        return Result.ok().put("data", list);
    }


    //查找所有数据
    @RequestMapping("/findAll")
    public Result findAll(){

        MatchSetting matchSetting=  matchSettingService.findAll();

        return Result.ok().put("data", matchSetting);
    }

    //改变擂主
    @RequestMapping("/chuangLeizhu")
    public Result chuangLeizhu(@RequestBody MatchSetting matchSetting,String uid){

        matchSettingService.updateWin(matchSetting,uid);

        return Result.ok();
    }
}
