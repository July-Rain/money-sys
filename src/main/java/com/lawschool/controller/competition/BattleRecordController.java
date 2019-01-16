package com.lawschool.controller.competition;

import com.lawschool.beans.User;
import com.lawschool.service.competition.BattleRecordService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public Result list(@RequestParam Map<String,Object> params){
        User u = (User) SecurityUtils.getSubject().getPrincipal();
        PageUtils page = battleRecordService.queryPage(params,u.getId());
        return Result.ok().put("page", page);
    }
    @RequestMapping("/listByLeitai")
    public Result listByLeitai(@RequestParam Map<String,Object> params){
        User u = (User) SecurityUtils.getSubject().getPrincipal();
        PageUtils page = battleRecordService.queryPageByLeitai(params,u.getId());
        return Result.ok().put("page", page);
    }
}
