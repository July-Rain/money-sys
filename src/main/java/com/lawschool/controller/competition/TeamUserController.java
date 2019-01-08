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


        return Result.ok();
    }

    @ResponseBody
    @RequestMapping("/delete")
    public Result delete(@RequestParam String id){


        return Result.ok();
    }
}
