package com.lawschool.controller.bbs.Sensitivefilter;

import com.lawschool.base.AbstractController;
import com.lawschool.base.Page;
import com.lawschool.beans.bbs.PostEntity;
import com.lawschool.service.bbs.sensitivefilter.SensitivefilterService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sensitivefilter")
public class SensitivefilterController extends AbstractController {

    @Autowired
    private SensitivefilterService sensitivefilterService;


//    @RequestMapping("/list")
//    public Result list(@RequestParam Map<String,Object> params,String userid){
////        User u = (User) SecurityUtils.getSubject().getPrincipal();
//        PageUtils page = sensitivefilterService.queryPage(params,userid);
//        return Result.ok().put("page", page);
//    }
}
