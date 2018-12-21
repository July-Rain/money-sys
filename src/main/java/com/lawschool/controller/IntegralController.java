package com.lawschool.controller;

import com.lawschool.base.AbstractController;
import com.lawschool.beans.Integral;
import com.lawschool.service.IntegralService;
import com.lawschool.service.UserIntegralService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author zjw
 * @Title: IntegralController
 * @ProjectName law_school
 * @Description: TODO
 * @date 2018/12/2019:34
 */
@RestController
@RequestMapping("/integral")
public class IntegralController extends AbstractController {

    @Autowired
    private IntegralService integralService;

    /**
     * @Author zjw
     * @Description 获取用户的积分学分记录
     * @Date 23:28 2018/12/20
     * @Param [param]
     * @return com.lawschool.util.Result
    **/
    @RequestMapping("/list")
    public Result getInfo(@RequestParam Map<String,Object> param){
        PageUtils page=integralService.list(param,getUser());
        return Result.ok().put("page", page);
    }

    /**
     * @Author zjw
     * @Description 添加用户积分学分记录
     * @Date 23:31 2018/12/20
     * @Param [integral]
     * @return com.lawschool.util.Result
    **/
    @RequestMapping("/add")
    public Result getInfo(@RequestBody Integral integral){
        integralService.addIntegralRecord(integral, getUser());
        return Result.ok();
    }

}
