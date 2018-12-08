package com.lawschool.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 站内消息
 */
@Controller
@RequestMapping("/msg")
public class MsgController extends AbstractController {
    @Autowired
    MsgService msgService;

    @Autowired
    UserService userService;

    @Autowired
    OrgService orgService;

    /**
     * 展示全部站内消息
     */
    @RequestMapping("/listAll")
    public Result showAllMsgList(){
        PageUtils msgList = msgService.selectAllMsg();
        return Result.ok().put("msgList",msgList);
    }

    /**
     * 删除选中的消息
     * @param ids
     */
    @RequestMapping("/delete")
    public Result deleteByMsgId(@RequestBody String[] ids){
        Result result = Result.ok();
        msgService.deleteBatchIds(Arrays.asList(ids));
        return result;
    }

    /**
     * 发布站内消息
     * @param ids
     */
    public Result release(@RequestBody String[] ids){
        User user = getUser();
        for(String id:ids){
            Msg msg = msgService.selectByMsgId(id);
            msg.setReleaseState("1");
            msg.setReleaseDate(new Date());
            msg.setReleasePeople(user.getUserName());
            msgService.updateByMsg(msg);
        }
        return Result.ok().put("state","已发送").put("user",user);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public Result info(@PathVariable("id") String id){
        Msg msg = msgService.selectByMsgId(id);
        Date date = new Date();
        return Result.ok().put("msg",msg).put("date",date);

    }

}
