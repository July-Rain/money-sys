package com.lawschool.controller;

import com.lawschool.base.AbstractController;
import com.lawschool.base.Page;
import com.lawschool.beans.ManuscriptEntity;
import com.lawschool.service.ManuscriptService;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ManuscriptController
 * @Author wangtongye
 * @Date 2018/12/27 17:31
 * @Versiom 1.0
 **/
@RestController
@RequestMapping("/manuscript")
public class ManuscriptController extends AbstractController {

    @Autowired
    private ManuscriptService manuscriptService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(@RequestParam Map<String, Object> params){
        Page<ManuscriptEntity> page = manuscriptService.findPage(new Page<ManuscriptEntity>(params), new ManuscriptEntity());
        return Result.ok().put("page", page);
    }

    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public Result info(@PathVariable("id") String id){
        ManuscriptEntity manuscriptEntity = manuscriptService.findOne(id);
        return Result.ok().put("data", manuscriptEntity);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestBody ManuscriptEntity entity){
        manuscriptService.save(entity);
        return Result.ok();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result delete(@RequestBody List<String> ids){
        manuscriptService.delete(ids);
        return Result.ok();
    }

    @RequestMapping(value = "/auditList", method = RequestMethod.GET)
    public Result auditList(@RequestParam Map<String, Object> params){

        return Result.ok();
    }

    @RequestMapping(value = "/audit/{id}", method = RequestMethod.POST)
    public Result audit(@PathVariable("id") String id, String audit){

        return Result.ok();
    }
}
