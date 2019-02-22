package com.lawschool.controller.practicecenter;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.base.AbstractController;
import com.lawschool.beans.practicecenter.ThemeExerciseEntity;
import com.lawschool.dao.practicecenter.ThemeExerciseDao;
import com.lawschool.form.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.lawschool.beans.User;
import com.lawschool.service.practicecenter.ThemeExerciseService;
import com.lawschool.util.Result;

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
    private ThemeExerciseDao themeExerciseDao;

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
     * @param index 每页显示题目数量
     * @param isReview 当前页
     * @return
     */
    @RequestMapping(value = "/paper", method = RequestMethod.GET)
    public Result start(@RequestParam String id,
                        @RequestParam Integer index,
                        @RequestParam String isReview){

        Map<String, Object> resultMap = themeExerciseService.showPaper(id, getUser().getId(), index, isReview);

        return Result.ok()
                .put("question", resultMap.get("question"))
                .put("id", id).put("typeName", resultMap.get("typeName"));
    }

    /**
     *
     * @param form
     * @return
     */
    @RequestMapping(value = "/preserve", method = RequestMethod.POST)
    public Result preserve(@RequestBody ThemeAnswerForm form){

        User user = getUser();

        themeExerciseService.preserve(form, user.getId(), form.getTaskId());

        return Result.ok().put("recordId", form.getId());
    }

    /**
     * 提交练习，并返回统计信息
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/commit/{id}", method = RequestMethod.POST)
    public Result commit(@PathVariable("id") String id){

        int result = themeExerciseDao.updateStatus(id, ThemeExerciseEntity.STATUS_OFF);
        if(result < 1){
            return Result.error("请重新提交...");
        }
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

    /**
     * 收藏题目
     * @return
     */
    @RequestMapping(value = "/doCollect/{type}", method = RequestMethod.POST)
    public Result doCollect(@RequestBody CommonForm params, @PathVariable("type") Integer type){

        String id = params.getKey();// 题目ID
        String recordId = params.getValue();// 记录ID

        themeExerciseService.doCollect(id, recordId, type);

        return Result.ok();
    }

}