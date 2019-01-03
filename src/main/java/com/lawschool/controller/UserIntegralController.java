package com.lawschool.controller;

import com.lawschool.base.AbstractController;
import com.lawschool.beans.CrdStatOrg;
import com.lawschool.beans.ItrStatOrg;
import com.lawschool.beans.UserIntegral;
import com.lawschool.service.UserIntegralService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.lang.Integer.parseInt;

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

    /**
     * @Author zjw
     * @Description 学分 人员
     * @Date 11:22 2018-12-29
     * @Param []
     * @return com.lawschool.util.Result
    **/
    @RequestMapping("crdStatUser")
    public Result crdStatUser(@RequestParam  Map<String,Object> param){
        PageUtils page= userIntegralService.crdStatUser(param);
        return Result.ok().put("page", page);
    }

    /**
     * @Author zjw
     * @Description 学分 部门
     * @Date 11:22 2018-12-29
     * @Param []
     * @return com.lawschool.util.Result
     **/
    @RequestMapping("crdStatOrg")
    public Result crdStatOrg(){
        List<CrdStatOrg> orgList = userIntegralService.crdStatOrg();

        return Result.ok().put("orgList", orgList);
    }



    /**
     * @Author zjw
     * @Description 积分 人员
     * @Date 11:22 2018-12-29
     * @Param []
     * @return com.lawschool.util.Result
     **/
    @RequestMapping("itrStatUser")
    public Result itrStatUser(@RequestParam  Map<String,Object> param){
        PageUtils page= userIntegralService.itrStatUser(param);
        return Result.ok().put("page", page);
    }


    /**
     * @Author zjw
     * @Description 积分 部门
     * @Date 11:22 2018-12-29
     * @Param []
     * @return com.lawschool.util.Result
     **/
    @RequestMapping("itrStatOrg")
    public Result itrStatOrg(){
        List<ItrStatOrg> orgList = userIntegralService.itrStatOrg();
        return Result.ok().put("orgList", orgList);
    }
    /**
     * @Author zjw
     * @Description 模糊查找用户的积分学分情况
     * @Date 11:22 2018-12-29
     * @Param []
     * @return com.lawschool.util.Result
     **/
    @RequestMapping("lst")
    public Result list(@RequestParam  Map<String,Object> param){
        PageUtils page= userIntegralService.list(param);
        return Result.ok().put("page", page);
    }

}
