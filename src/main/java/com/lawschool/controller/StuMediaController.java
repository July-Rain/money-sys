package com.lawschool.controller;

import com.lawschool.base.AbstractController;
import com.lawschool.beans.StuMedia;
import com.lawschool.service.StuMediaService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/stuMedia")
public class StuMediaController extends AbstractController {

    @Autowired
    private StuMediaService stuMediaService;

    //所有我收藏的
    @RequestMapping("/mycollection")
    public Result listMyCollection(@RequestParam Map<String,Object> params){
        params.put("userId",getUser().getId());
        PageUtils pageUtils = stuMediaService.listMyCollection(params);
        return Result.ok().put("result",pageUtils);
    }

    //获取课件信息信息
    @RequestMapping("/getStuMedia")
    public Result getStuMedia(@RequestBody StuMedia stuMedia){
        StuMedia stuMedia1 = stuMediaService.getStuMedia(stuMedia);
        return Result.ok().put("info",stuMedia1);
    }

}
