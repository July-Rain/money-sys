package com.lawschool.controller.practicecenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.base.AbstractController;
import com.lawschool.beans.TestQuestions;
import com.lawschool.beans.practicecenter.TaskExerciseEntity;
import com.lawschool.beans.practicecenter.ThemeExerciseEntity;
import com.lawschool.form.*;
import com.lawschool.service.TestQuestionService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lawschool.beans.User;
import com.lawschool.service.practicecenter.ThemeExerciseService;
import com.lawschool.util.RedisUtil;
import com.lawschool.util.Result;
import org.springframework.web.servlet.ModelAndView;

/**
 * 主题练习
 * @author xuxiang
 * @date 2018/12/4 11:52
 */
@RestController
@RequestMapping("/exercise/theme")
public class ThemeExerciseController extends AbstractController {

    @Autowired
    private ThemeExerciseService themeExerciseService;

    @Autowired
    private TestQuestionService testQuestionService;

    /**
     * 主题练习首页列表
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result indexList(){

        User user = getUser();
        String userId = user.getId();
        
        List<ThemeExerciseForm> list = themeExerciseService.indexList(userId);

        return Result.ok().put("list", list);
    }

    /**
     * 生成个人任务
     * @param form
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestBody ThemeExerciseForm form){
        User user = getUser();
        String userId = user.getId();

        String id = themeExerciseService.saveTask(form, userId);

        return Result.ok().put("id", id);
    }

    /**
     * 开始任务
     * @param id 个人任务ID
     * @param limit 每页显示题目数量
     * @param page 当前页
     * @return
     */
    @RequestMapping(value = "/paper", method = RequestMethod.GET)
    public Result start(@RequestParam String id,
                        @RequestParam Integer limit,
                        @RequestParam Integer page){

        Map<String, Object> resultMap = themeExerciseService.showPaper(id, getUser().getId(), limit, page);

        return Result.ok().put("result", resultMap).put("id", id);
    }

    /**
     *
     * @param form
     * @param type
     * @return
     */
    @RequestMapping(value = "/preserve/{type}", method = RequestMethod.POST)
    public Result preserve(@RequestBody ThemeForm form,
                           @PathVariable("type") Integer type){

        User user = getUser();

        themeExerciseService.preserve(form.getList(), user.getId(), type, form.getId());

        return Result.ok();
    }

    @RequestMapping(value = "/analysis/{id}", method = RequestMethod.GET)
    public Result commit(@PathVariable("id") String id){

        AnalysisForm resultForm = themeExerciseService.analysisAnswer(id);

        return Result.ok().put("form", resultForm);
    }

    /**
     * 清空重做
     * @param id
     * @return
     */
    @RequestMapping(value = "/restart/{id}", method = RequestMethod.POST)
    public Result restart(@PathVariable("id") String id){

        String idNew = themeExerciseService.restart(id);
        return Result.ok().put("id", idNew);
    }
}