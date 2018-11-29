package com.lawschool.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.beans.StuMedia;
import com.lawschool.service.StuMediaService;
import com.lawschool.util.Result;
import org.apache.velocity.runtime.directive.MacroParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stuMedia")
public class StuMediaController extends  AbstractController{

    @Autowired
    private StuMediaService stuMediaService;

    //所有我收藏的
    @RequestMapping("/mycollection")
    public Result listMyCollection(Map<String,Object> params){
       // params.put("userId",getUser().getUserId());
        params.put("userId",1);
        List<StuMedia> stuMedias = stuMediaService.listMyCollection(params);
        return Result.ok().put("result",stuMedias);
    }

    //获取课件信息信息
    @RequestMapping("/getStuMedia")
    public Result getStuMedia(StuMedia stuMedia){
        StuMedia stuMedia1 = stuMediaService.getStuMedia(stuMedia);
        return Result.ok().put("info",stuMedia1);
    }

}