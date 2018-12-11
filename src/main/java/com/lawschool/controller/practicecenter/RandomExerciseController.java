package com.lawschool.controller.practicecenter;

import com.lawschool.base.AbstractController;
import com.lawschool.form.RandomExerciseForm;
import com.lawschool.form.ThemeAnswerForm;
import com.lawschool.service.practicecenter.ExerciseService;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @version V1.0
 * @Description: 随机练习Controller
 * @author: 中石电子科技 徐祥
 * @date: 2018-12-07 15:36
 */
@RestController
@RequestMapping("/exercise/random")
public class RandomExerciseController extends AbstractController {

    @Autowired
    private ExerciseService exerciseService;

    /**
     * 开始随机练习
     *  生成随机练习任务，并返回试题
     * @param form
     * @return
     */
    @RequestMapping(value = "/start", method = RequestMethod.POST)
    public Result startExercise(@RequestBody RandomExerciseForm form){

        exerciseService.startExercise(form);

        return Result.ok();
    }

    /**
     * 随机练习获取题目
     * @param form 答题结果信息，用于更新随机任务信息
     * @return
     */
    public Result getQuestion(@RequestBody ThemeAnswerForm form){


        return Result.ok();
    }

}
