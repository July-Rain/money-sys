package com.lawschool.controller.competition;

import com.lawschool.beans.competition.TeamUser;
import com.lawschool.service.competition.TeamUserService;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @Descriptin  战队人员表
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
@Controller
@RequestMapping("/teamUser")
public class TeamUserController {

    @Autowired
    private TeamUserService teamUserService;


    //保存
    @RequestMapping("/save")
    public Result save(){

        //String teamid   假如传过来了一个战队的id
        String teamid="123456";
        TeamUser TeamUser=new TeamUser();
        teamUserService.save(teamid);
        return Result.ok();
    }

    @ResponseBody
    @RequestMapping("/delete")
    public Result delete(@RequestParam String id){

        //因为数据库的结构  设计为有多少条数据 就是多少个 大关   没有一点多余数据    所以要删除的话 就是  全删   不存在 删一条的说法
        teamUserService.deleteId(id);

        return Result.ok();
    }
}
