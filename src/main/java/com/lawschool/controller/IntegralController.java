package com.lawschool.controller;

import com.lawschool.base.AbstractController;
import com.lawschool.beans.Integral;
import com.lawschool.beans.User;
import com.lawschool.beans.diagnosis.OrgDiagnosisEntity;
import com.lawschool.beans.vo.OrgCompetitionVO;
import com.lawschool.service.IntegralService;
import com.lawschool.service.UserIntegralService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public Result add(@RequestBody Integral integral){
        integralService.addIntegralRecord(integral, getUser());
        return Result.ok();
    }


    @RequestMapping("/chuangguanCountByUser")
    public Result chuangguanCountByUser(String uid){
        int i=integralService.chuangguanCountByUser(uid);
        return Result.ok().put("CheckpointSorce", i);
    }

    @RequestMapping("/pkByUser")
    public Result pkByUser(String uid){
        int i=integralService.pkByUser(uid);
        return Result.ok().put("PkSorce", i);
    }

    @RequestMapping("/leitaiByUser")
    public Result leitaiByUser(String uid){
        int i=integralService.leitaiByUser(uid);
        return Result.ok().put("leitaiSorce", i);
    }


    @RequestMapping("/userByDeptList")
    public Result userByDeptList(@RequestParam Map<String,Object> params){

        User u = (User) SecurityUtils.getSubject().getPrincipal();
        String deptcode=u.getOrgCode();
        PageUtils page = integralService.userByDeptList(params,deptcode);
        return Result.ok().put("page", page);
    }


    @RequestMapping("getOrgDiaStat")
    public Result getOrgDiaStat(@RequestParam  Map<String,String> params){
        //获取部门统计数据
        User user = getUser();
        params.put("userId",user.getId());
        params.put("orgid",user.getOrgId());
        List<OrgCompetitionVO> orgComList = integralService.orgDiaStat(params);
        return  Result.ok().put("data",orgComList);
    }
}
