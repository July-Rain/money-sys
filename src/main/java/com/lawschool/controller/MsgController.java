package com.lawschool.controller;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.annotation.SysLog;
import com.lawschool.base.AbstractController;
import com.lawschool.beans.Msg;
import com.lawschool.beans.User;
import com.lawschool.service.MsgService;
import com.lawschool.service.OrgService;
import com.lawschool.service.UserService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 站内消息
 */
@RestController
@RequestMapping("/msg")
public class MsgController extends AbstractController{
    @Autowired
    MsgService msgService;

    @Autowired
    UserService userService;

    @Autowired
    OrgService orgService;

    /**
     * 展示全部站内消息
     */
    @RequestMapping(value = "/listAll",method = RequestMethod.GET)
    public Result showAllMsgList(@RequestParam Map<String,Object> params){
        params.put("userId",getUser().getId());
        PageUtils pageUtils =msgService.selectAllMsg(params);
        List<Msg> MsgList=(List<Msg>)pageUtils.getList();
        for(Msg msg:MsgList)
        {
            String[] idArr = msg.getRecievePeople().split(",");
            for(int i=0;i<idArr.length;i++){
                if(userService.selectUserByUserId(idArr[i])==null)
                {
                    System.out.println("sfsdfsd");
                }
                if(i==0)
                {
                    msg.setRecievePeopleNmae(userService.selectUserByUserId(idArr[i]).getFullName());
                }
                else {
                    msg.setRecievePeopleNmae(msg.getRecievePeopleNmae() + "," + userService.selectUserByUserId(idArr[i]).getFullName());
                }
            }

        }
        return Result.ok().put("page",pageUtils);
    }

    /**
     * 删除选中的消息
     * @param id
     */
    @RequestMapping("/delete")
    public Result deleteByMsgId(@RequestParam String id){
        msgService.deleteByMsgId(id);
        return Result.ok();
    }
    /**
     * 撤回选中的消息
     * @param id
     */
    @RequestMapping("/recall")
    public Result recall(@RequestParam String id){
        //先查后改  简单点
        Msg msg = msgService.selectById(id);
        msg.setReleaseState("0");//0未发送
        msg.setReleaseDate(null);
        msgService.updateById(msg);
        return Result.ok();
    }
    /**
     * 信息
     */
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public Result info(@RequestParam String id){
        Msg msg = msgService.selectByMsgId(id);
        String[] idArr = msg.getRecievePeople().split(",");
        for(int i=0;i<idArr.length;i++){
            if(i==0)
            {
                msg.setRecievePeopleNmae(userService.selectUserByUserId(idArr[i]).getFullName());
            }
            else {
                msg.setRecievePeopleNmae(msg.getRecievePeopleNmae() + "," + userService.selectUserByUserId(idArr[i]).getFullName());
            }
        }
        return Result.ok().put("msg",msg);

    }

    @SysLog("保存消息")
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public Result insert(@RequestBody Msg msg){

//        String[] idArr = recievePeopleIds.split(",");
//        for(int i=0;i<idArr.length;i++){
//            msg.setId(IdWorker.getIdStr());
//            msg.setRecievePeople(idArr[i]);
//            msg.setReleaseState("0");//未发送
//            msg.setRecieveDate(new Date());
//            msgService.insert(msg);
//        }
        msg.setReleaseState("0");//未发送
        msg.setRecieveDate(new Date());
        msg.setId(IdWorker.getIdStr());
        msgService.insert(msg);
        return Result.ok().put("id",msg.getId());
    }

    @SysLog("修改消息")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result update(@RequestBody Msg msg){
//        String recievePeopleIds = msg.getRecievePeople();
//        String[] idArr = recievePeopleIds.split(",");
//        for(int i=0;i<idArr.length;i++){
//            msg.setId(IdWorker.getIdStr());
//            msg.setRecievePeople(idArr[i]);
//            msg.setReleaseState("0");//未发送
//            msgService.updateById(msg);
//        }
        msg.setRecieveDate(new Date());
        msgService.updateById(msg);
        return Result.ok().put("id",msg.getId());
    }

    @SysLog("发送消息")
    @RequestMapping(value = "/send",method = RequestMethod.POST)
    public Result sendMsg(@RequestParam String id){
//        User currentUser = getUser();
        Msg msg = msgService.selectById(id);
//        msg.setReleasePeople(currentUser.getUserName());
//        msg.setReleaseDept(orgService.findOrgByCode(currentUser.getOrgCode()).getFullName());//发布部门(数据暂时有冲突)
        msg.setReleaseState("1");//1已发送
        msg.setReleaseDate(new Date());
        msgService.updateById(msg);
        return Result.ok().put("msg",msg);
    }

    @SysLog("前台获取已收到消息")
    @RequestMapping(value = "/findMsgList",method = RequestMethod.GET)
    public Result findMsgList(@RequestParam Map<String,Object> param){

        param.put("userId",getUser().getId());
        PageUtils pageUtils = msgService.findMsgList(param);
        return Result.ok().put("page",pageUtils);


//        String recievePeople = getUser().getUserId();
//        PageUtils page = msgService.showSent(recievePeople,param);
//        return Result.ok().put("page",page);
    }

}
