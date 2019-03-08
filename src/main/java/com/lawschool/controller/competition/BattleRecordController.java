package com.lawschool.controller.competition;

import com.lawschool.beans.User;
import com.lawschool.beans.competition.CompetitionRecord;
import com.lawschool.service.UserIntegralService;
import com.lawschool.service.UserService;
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
    @Autowired
    private UserIntegralService userIntegralService;
    @Autowired
    private UserService userServiceService;


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
    //获取擂台赛擂主次数
    @RequestMapping("/leitaiWinCount")
    public Result leitaiWinCount(){
        User u = (User) SecurityUtils.getSubject().getPrincipal();
        int leitaibaifengbi=0;
        int t=0;
        int i= battleRecordService.leitaiWinCount();
        if(i!=0)
        {
            int userscount= userServiceService.getAllusesCount();
            List<Map> firstListByleitai= battleRecordService.firstListByleitai();
            //遍历 list
            for(int k=0;k<firstListByleitai.size();k++)
            {
                if(firstListByleitai.get(k).get("userid").equals(u.getId()))
                {
                    t=k;
                    break;
                }
            }

            leitaibaifengbi=(userscount-(t+1))*100/userscount;
        }

        return Result.ok().put("leitaiWinCount",i).put("leitaibaifengbi",leitaibaifengbi);
    }
    //获取pk总分
    @RequestMapping("/pkSum")
    public Result pkSum(){
        User u = (User) SecurityUtils.getSubject().getPrincipal();
        int onlPkSumbaifengbi=0;
        int t=0;
        int i= battleRecordService.pkSum();
        if(i!=0)
        {
            int userscount= userServiceService.getAllusesCount();
            List<Map> firstListByPk= battleRecordService.firstListByPk();

            //遍历 list
            for(int k=0;k<firstListByPk.size();k++)
            {
                if(firstListByPk.get(k).get("userid").equals(u.getId()))
                {
                    t=k;
                    break;
                }
            }

            onlPkSumbaifengbi=(userscount-(t+1))*100/userscount;
        }



        return Result.ok().put("pkSum",i).put("onlPkSumbaifengbi",onlPkSumbaifengbi);
    }

    //根据id 获取擂台获胜总次数与总积分
    @RequestMapping("/winLeiTaiCountByUserId")
    public Result winLeiTaiCountByUserId(String winId){
        int i= battleRecordService.winLeiTaiCountByUserId(winId);

        int t=   userIntegralService.getJifenByUserId(winId);
        return Result.ok().put("winLeiTai",i).put("jifen",t);
    }
}
