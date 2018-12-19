package com.lawschool.controller.practicecenter;

import com.lawschool.base.AbstractController;
import com.lawschool.beans.User;
import com.lawschool.form.AnalysisForm;
import com.lawschool.service.practicecenter.ExerciseService;
import com.lawschool.service.practicecenter.ThemeExerciseService;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther: Moon
 * @Date: 2018/12/15 15:46
 * @Description: 练习中心分析统计
 */
@RequestMapping("/analysis/exercise")
public class AnalysisController extends AbstractController {

    @Autowired
    private ThemeExerciseService themeExerciseService;

    @Autowired
    private ExerciseService exerciseService;

    /**
     * 统计分析
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public Result index(){

        Result result = Result.ok();

        User user = getUser();
        if(user == null){
            throw new RuntimeException("用户信息获取失败，请重新登陆系统");
        }

        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();
        String month = sim.format(date);
        // 随机练习
        AnalysisForm random = exerciseService.analysis(month, user.getId());
        if(random != null){
            random.setTypeName("随机练习");
            result.put("random", random);
        }

        // 主题练习
        AnalysisForm theme = themeExerciseService.analysis(month, user.getId());
        if(theme != null){
            theme.setTypeName("主题练习");
            result.put("theme", theme);
        }

        return result;
    }

}
