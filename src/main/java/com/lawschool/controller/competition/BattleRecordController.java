package com.lawschool.controller.competition;

import com.lawschool.beans.User;
import com.lawschool.beans.competition.CompetitionRecord;
import com.lawschool.service.competition.BattleRecordService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 *
 * @Descriptin  对战记录
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
@RestController
@RequestMapping("/battleRecord")
public class BattleRecordController {

    @Autowired
    private BattleRecordService battleRecordService;

    @RequestMapping("/list")
    public Result list(@RequestParam Map<String,Object> params,String userid){
//        User u = (User) SecurityUtils.getSubject().getPrincipal();
        PageUtils page = battleRecordService.queryPage(params,userid);
        return Result.ok().put("page", page);
    }
    @RequestMapping("/listByLeitai")
    public Result listByLeitai(@RequestParam Map<String,Object> params,String userid){
//        User u = (User) SecurityUtils.getSubject().getPrincipal();
        PageUtils page = battleRecordService.queryPageByLeitai(params,userid);
        return Result.ok().put("page", page);
    }


    @RequestMapping("/PkCountBydept")
    public Result PkCountBydept(String deptcode){

        int i=battleRecordService.PkCountBydept(deptcode);

        return Result.ok().put("count", i);
    }

    @RequestMapping("/leitaiCountBydept")
    public Result leitaiCountBydept(String deptcode){

        int i=battleRecordService.leitaiCountBydept(deptcode);

        return Result.ok().put("count", i);
    }


    @RequestMapping("/pkSorceBydept")
    public Result pkSorceBydept(String deptcode){
        int i=battleRecordService.pkSorceBydept(deptcode);
        return Result.ok().put("Sorce", i);
    }

    @RequestMapping("/leitaiSorceBydept")
    public Result leitaiSorceBydept(String deptcode){
        int i=battleRecordService.leitaiSorceBydept(deptcode);
        return Result.ok().put("Sorce", i);
    }
    //获取在线pk 排行榜
    @RequestMapping("/firstListByPk")
    public Result firstListByPk(){
        List<Map> firstListByPk= battleRecordService.firstListByPk();
        return Result.ok().put("firstListByPk",firstListByPk);
    }
    //获取擂台赛排行榜
    @RequestMapping("/firstListByleitai")
    public Result firstListByleitai(){
        List<Map> firstListByleitai= battleRecordService.firstListByleitai();
        return Result.ok().put("firstListByleitai",firstListByleitai);
    }
}
