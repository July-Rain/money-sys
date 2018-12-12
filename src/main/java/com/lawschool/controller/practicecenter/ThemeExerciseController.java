package com.lawschool.controller.practicecenter;

import java.util.List;

import com.lawschool.base.AbstractController;
import com.lawschool.beans.TestQuestions;
import com.lawschool.beans.practicecenter.ThemeExerciseEntity;
import com.lawschool.form.AnalysisForm;
import com.lawschool.form.QuestForm;
import com.lawschool.form.ThemeForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lawschool.beans.User;
import com.lawschool.form.ThemeExerciseForm;
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
public class ThemeExerciseController  extends AbstractController {

    @Autowired
    private ThemeExerciseService themeExerciseService;

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("/index")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("/exerciseCenter/theme_index");

        return mv;
    }

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
     * 开始练习、重新练习
     * @param id 主题练习ID
     * @param status 主题练习状态，0待开始、2已提交
     * @param typeId 主题类型ID
     * @return 返回新的主题任务ID，用于获取题目
     */
    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public Result startTask(@RequestParam(required = false) String id,
                            @RequestParam Integer status,
                            @RequestParam String typeId,
                            @RequestParam String typeName) {

        User user = this.getUser();

        String result = themeExerciseService.startTheme(id, status, typeId, user.getId(), typeName);
    	
    	return Result.ok().put("id", result);
    }

    /**
     * 主题练习获取题目
     * @return 返回题目
     */
    @ResponseBody
    @RequestMapping(value = "/questions", method = RequestMethod.POST)
    public Result getQuestions(@RequestBody ThemeForm form){
        User user = getUser();
        String userId = user.getId();
        form.setUserId(userId);

        List<QuestForm> list = themeExerciseService.saveAndGetQuestions(form);
        // 返回题目list
        return Result.ok().put("list", list);
    }

    /**
     * 主题练习保存操作
     * @param form
     * @return
     */
    @RequestMapping(value = "/preserve", method = RequestMethod.POST)
    public Result preserve(@RequestBody ThemeForm form){

        form.setStatus(ThemeExerciseEntity.STATUS_ON);
        themeExerciseService.preserve(form);

        return Result.ok();
    }

    /**
     * 提交主题练习
     * @param form
     * @return
     */
    @RequestMapping(value = "/commit", method = RequestMethod.POST)
    public Result commit(@RequestBody ThemeForm form){

        AnalysisForm resultForm = themeExerciseService.commit(form);

        return Result.ok().put("form", resultForm);
    }

    /**
     * 获取主题练习的统计信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/analysis", method = RequestMethod.GET)
    public Result analysisAnswer(@RequestParam String id){

        AnalysisForm form = themeExerciseService.analysisAnswer(id);

        return Result.ok().put("result", form);
    }

    @RequestMapping(value = "/answer", method = RequestMethod.GET)
    public ModelAndView answer(@RequestParam String id){
        ModelAndView mv = new ModelAndView("/exerciseCenter/theme_answer");

        mv.addObject("id", id);
        return mv;
    }
}
