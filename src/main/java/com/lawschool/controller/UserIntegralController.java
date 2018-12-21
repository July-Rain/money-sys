package com.lawschool.controller;

import com.lawschool.base.AbstractController;
import com.lawschool.beans.UserIntegral;
import com.lawschool.service.UserIntegralService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Description:用户积分学分
 * @Author:Zjw
 * @Date:Create in 18:06 2018/12/20
 * @Modifid By:
 */

@RestController
@RequestMapping("/userIntegral")
public class UserIntegralController extends AbstractController {

    @Autowired
    private UserIntegralService userIntegralService;

    /**
     * @Author zjw
     * @Description 获取用户积分学分情况
     * @Date 23:31 2018/12/20
     * @Param []
     * @return com.lawschool.util.Result
    **/
    @RequestMapping("info")
    public Result getInfo(){
        UserIntegral userIntegral =userIntegralService.getInfo(getUser());
        return Result.ok().put("info", userIntegral);
    }

}
