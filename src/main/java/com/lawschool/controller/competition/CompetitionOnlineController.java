package com.lawschool.controller.competition;

import com.lawschool.beans.competition.CompetitionOnline;
import com.lawschool.service.competition.CompetitionOnlineService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 *
 * @Descriptin  在线比武配置
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
@RestController
@RequestMapping("/competitionOnline")
public class CompetitionOnlineController {

    @Autowired
    private CompetitionOnlineService competitionOnlineService;

    @RequestMapping("/list")
    public Result list(@RequestParam Map<String, Object> params){
        PageUtils page = competitionOnlineService.queryPage(params);
        return Result.ok().put("page", page);
    }

    //根据id来找数据
    @RequestMapping("/info")
    public Result info(@RequestParam String id){

        CompetitionOnline competitionOnline=competitionOnlineService.info(id);
        return Result.ok().put("competitionOnline", competitionOnline);
    }


    //根据id来找数据
    @RequestMapping("/save")
    public Result save(@RequestBody CompetitionOnline competitionOnline){

        competitionOnlineService.save(competitionOnline);
        return Result.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public Result delete(@RequestParam String id){

        competitionOnlineService.deleteComOnline(id);

        return Result.ok();
    }
    /**
     * 删除
     */
    @RequestMapping("/deleteAll")
    public Result deleteAll(){

        competitionOnlineService.deleteAll();

        return Result.ok();
    }
//先不考虑修改
//    //根据id来找数据
//    @RequestMapping("/update")
//    public Result update(@RequestParam Map<String, Object> params){
//
//        competitionOnlineService.updateComOnline();
//        return Result.ok();
//    }

}
