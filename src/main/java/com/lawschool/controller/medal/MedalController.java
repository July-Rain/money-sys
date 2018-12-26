package com.lawschool.controller.medal;

import com.lawschool.annotation.SysLog;
import com.lawschool.base.Page;
import com.lawschool.beans.medal.MedalEntity;
import com.lawschool.beans.medal.UserMedalEntity;
import com.lawschool.service.medal.MedalService;
import com.lawschool.service.medal.UserMedalService;
import com.lawschool.util.GeneralRuntimeException;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/medal")
public class MedalController {

    @Autowired
    private MedalService medalService;
    @Autowired
    private UserMedalService userMedalService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    private Result list(@RequestParam Map<String, Object> params){
        Page<MedalEntity> list = medalService.findPage(new Page<MedalEntity>(params), new MedalEntity());
        return Result.ok().put("list", list);
    }

    /**
     * 查询单个勋章
     */
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public Result info(@PathVariable("id") String id) {
        MedalEntity medal = medalService.findOne(id);
        return Result.ok().put("data", medal);
    }

    /**
     * 保存勋章
     */
    @SysLog("保存勋章")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestBody MedalEntity medalEntity) {
        medalService.save(medalEntity);
        return Result.ok();
    }

    /**
     * 删除勋章
     */
    @SysLog("删除勋章")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result deleteById(@RequestBody List<String> idList) {
        medalService.delete(idList);
        return Result.ok();
    }

    /**
     * 新增我的勋章（领取勋章）
     */
    @SysLog("新增我的勋章（领取勋章）")
    @RequestMapping(value = "/saveMedal", method = RequestMethod.POST)
    public Result saveMedal(@RequestBody UserMedalEntity medal) {

        if(userMedalService.checkUserMedal(medal)){
            throw new GeneralRuntimeException("勋章已经领取过！");
        }
        userMedalService.save(medal);
        return Result.ok();
    }

    /**
     * 查询我的勋章
     */
    @RequestMapping(value = "/find/{userId}", method = RequestMethod.GET)
    public Result findByUserId(@PathVariable("userId") String userId) {
        List<String> list = userMedalService.findMedalIdList(userId);
        return Result.ok().put("list", list);
    }
}
