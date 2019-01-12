package com.lawschool.controller.practicecenter;

import com.lawschool.base.AbstractController;
import com.lawschool.beans.User;
import com.lawschool.form.AnalysisForm;
import com.lawschool.service.practicecenter.*;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Auther: Moon
 * @Date: 2018/12/15 15:46
 * @Description: 练习中心分析统计
 */
@RestController
@RequestMapping("/analysis/exercise")
public class AnalysisController extends AbstractController {

    @Autowired
    private AnalysisService analysisService;

    /**
     * 统计分析
     * @return
     */
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public Result index(){
        Result result = Result.ok();

        User user = getUser();
        if(user == null){
            throw new RuntimeException("用户信息获取失败，请重新登录");
        }

        /* -------- 封装参数 -------- */
        Date date = new Date();
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM");
        String month = sim.format(date);

        String userId = user.getId();

        Map<String, Object> resultMap = analysisService.analysis(month, userId);

        return result.put("map", resultMap);
    }

}
