package com.lawschool.controller.competition;

import com.lawschool.base.AbstractController;
import com.lawschool.beans.User;
import com.lawschool.beans.competition.CompetitionRecord;
import com.lawschool.service.UserService;
import com.lawschool.service.accessory.AccessoryService;
import com.lawschool.service.competition.CompetitionRecordService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.AbstractCollection;
import java.util.List;
import java.util.Map;

/**
 *
 * @Descriptin  竞赛记录
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
@RestController
@RequestMapping("/competitionRecord")
public class CompetitionRecordController extends AbstractController {

    @Autowired
    private CompetitionRecordService competitionRecordService;
    @Autowired
    private UserService userServiceService;



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


    //保存分数
    @RequestMapping("/recordScore")
    public Result recordScore( String foreignKeyId, String nowbig, String nowlit, String sorce){
        User u = (User) SecurityUtils.getSubject().getPrincipal();
      String s=  competitionRecordService.recordScore(foreignKeyId,nowbig,nowlit,u,sorce);//这边到时候和前端商量  传个json串
        return Result.ok().put("s",s);
    }




    //根据人的id 来查 玩了闯关的次数
    @RequestMapping("/chuangguanCountByUser")
    public Result chuangguanCountByUser(String uid){

        int i=competitionRecordService.chuangguanCountByUser(uid);
        System.out.println(i);
        return Result.ok().put("count",i);
    }



    //查询分页
    @RequestMapping("/list")
    public Result list(@RequestParam Map<String,Object> params,String userid){
//        User u = (User) SecurityUtils.getSubject().getPrincipal();
        PageUtils page = competitionRecordService.queryPage(params,userid);
        return Result.ok().put("page", page);
    }

    //根据部门code 来查 玩了闯关的次数
    @RequestMapping("/chuangguanCountBydept")
    public Result chuangguanCountBydept(String deptcode){

        int i=competitionRecordService.chuangguanCountBydept(deptcode);
        System.out.println(i);
        return Result.ok().put("count",i);
    }


    //根据部门code 来查 玩了闯关的次数
    @RequestMapping("/chuangguanSorceBydept")
    public Result chuangguanSorceBydept(String deptcode){

        int i=competitionRecordService.chuangguanSorceBydept(deptcode);
        System.out.println(i);
        return Result.ok().put("Sorce",i);
    }

    //获取闯关排行榜
    @RequestMapping("/chuangGuanRanking")
    public Result chuangGuanRanking(){
        List<CompetitionRecord> competitionRecordList= competitionRecordService.chuangGuanRanking();
        return Result.ok().put("competitionRecordList",competitionRecordList);
    }

    //获取闯关最高档
    @RequestMapping("/chuangGuanRankingByUser")
    public Result chuangGuanRankingByUser(){
        CompetitionRecord competitionRecord= competitionRecordService.chuangGuanRankingByUser();
        User u = (User) SecurityUtils.getSubject().getPrincipal();
        int t=0;
        int baifengbi=0;
        if(competitionRecord!=null)
        {
            List<CompetitionRecord> competitionRecordList= competitionRecordService.chuangGuanRanking();
            int userscount= userServiceService.getAllusesCount();
            for(int i=0;i<competitionRecordList.size();i++)
            {
                if(competitionRecordList.get(i).getUserId().equals(u.getId()))
                {
                    t=i;
                    break;
                }
            }
               baifengbi=(userscount-(t+1))*100/userscount;


        }

        return Result.ok().put("competitionRecord",competitionRecord).put("baifengbi",baifengbi);
    }
}
