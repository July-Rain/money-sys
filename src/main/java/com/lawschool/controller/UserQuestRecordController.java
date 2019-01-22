package com.lawschool.controller;


import com.lawschool.service.UserQuestRecordService;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @Descriptin  做过的题目保存
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
@RestController
@RequestMapping("/userQuestRecord")
public class UserQuestRecordController {

    @Autowired
    private UserQuestRecordService userQuestRecordService;

    @RequestMapping("CheckpointByUser")
    public Result CheckpointByUser(String uid){
        int i= userQuestRecordService.CheckpointByUser(uid);
        return Result.ok().put("CheckpointByUser",i );
    }

    @RequestMapping("OnlinByUser")
    public Result OnlinByUser(String uid){
        int i= userQuestRecordService.OnlinByUser(uid);
        return Result.ok().put("OnlinByUser",i );
    }
    @RequestMapping("leitaiByUser")
    public Result leitaiByUser(String uid){
        int i= userQuestRecordService.leitaiByUser(uid);
        return Result.ok().put("leitaiByUser",i );
    }

    @RequestMapping("chuangguanCorrectBydept")
    public Result chuangguanCorrectBydept(String deptcode){
        int i= userQuestRecordService.chuangguanCorrectBydept(deptcode);
        return Result.ok().put("Correct",i );
    }

    @RequestMapping("OnlinCorrectBydept")
    public Result OnlinCorrectBydept(String deptcode){
        int i= userQuestRecordService.OnlinCorrectBydept(deptcode);
        return Result.ok().put("Correct",i );
    }

    @RequestMapping("leitaiCorrectBydept")
    public Result leitaiCorrectBydept(String deptcode){
        int i= userQuestRecordService.leitaiCorrectBydept(deptcode);
        return Result.ok().put("Correct",i );
    }
}