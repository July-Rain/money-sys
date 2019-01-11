package com.lawschool.controller.competition;

import com.lawschool.beans.competition.CompetitionTeam;
import com.lawschool.service.competition.BattlePlatformService;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 *
 * @Descriptin  对战平台
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
@RestController
@RequestMapping("/battlePlatform")
public class BattlePlatformController {

    @Autowired
    private BattlePlatformService battlePlatformService;

    //查询
    @RequestMapping("/PkCountByUser")
    public Result PkCountByUser(String uid){
         int i =battlePlatformService.PkCountByUser(uid);

        return Result.ok().put("count",i);
    }

    @RequestMapping("/leitaiCountByUser")
    public Result leitaiCountByUser(String uid){
        int i =battlePlatformService.leitaiCountByUser(uid);

        return Result.ok().put("count",i);
    }

}
