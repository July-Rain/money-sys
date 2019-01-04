package com.lawschool.controller.bbs;

import com.lawschool.base.Page;
import com.lawschool.beans.bbs.ReplyEntity;
import com.lawschool.service.bbs.ReplyService;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ReplyController
 * @Author wangtongye
 * @Date 2019/1/3 10:52
 * @Versiom 1.0
 **/
@RestController
@RequestMapping("/reply")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(@RequestParam Map<String, Object> params){
        Page<ReplyEntity> page = replyService.findPage(new Page<ReplyEntity>(params), new ReplyEntity());
        return Result.ok().put("page", page);
    }

    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public Result info(@PathVariable("id") String id){
        ReplyEntity replyEntity = replyService.findOne(id);
        return Result.ok().put("data", replyEntity);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestBody ReplyEntity entity){
        replyService.save(entity);
        return Result.ok();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result delete(@RequestBody List<String> ids){
        replyService.delete(ids);
        return Result.ok();
    }
}
