package com.lawschool.controller.practicecenter;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.base.AbstractController;
import com.lawschool.base.Page;
import com.lawschool.beans.User;
import com.lawschool.beans.practicecenter.ExerciseEntity;
import com.lawschool.form.*;
import com.lawschool.service.DictService;
import com.lawschool.service.TestQuestionService;
import com.lawschool.service.practicecenter.ExerciseAnswerRecordService;
import com.lawschool.service.practicecenter.ExerciseService;
import com.lawschool.service.system.TopicTypeService;
import com.lawschool.util.Result;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

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

    @Autowired
    private DictService dictService;

    @Autowired
    private TopicTypeService topicTypeService;

    @Autowired
    private TestQuestionService testQuestionService;

    @Autowired
    private ExerciseAnswerRecordService exerciseAnswerRecordService;

    /**
     * 随机练习主页列表信息
     * @param params 分页信息
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(@RequestParam Map<String, Object> params){
        ExerciseEntity entity = new ExerciseEntity();
        entity.setCreateUser(getUser().getId());

        Page<ExerciseEntity> page = exerciseService.findPage(new Page<ExerciseEntity>(params), entity);

        return Result.ok().put("page", page);
    }

    /**
     * 随机练习，获取各字典表的值
     * @return
     */
    @RequestMapping(value = "/dict", method = RequestMethod.GET)
    public Result dict(){

        // 获取试题难度字典list
        List<CommonForm> diffList = dictService.findCodeByType("QUESTION_DIFF");

        // 获取试题分类list
        List<CommonForm> typeList = dictService.findCodeByType("TYPE");

        // 获取试题类型list
        List<CommonForm> qtList = dictService.findCodeByType("QUESTION_TYPE");

        List<CommonForm> topicList = topicTypeService.findAll(new ArrayList<String>());

        return Result.ok().put("diffList", diffList)
                .put("typeList", typeList)
                .put("qtList", qtList)
                .put("topicList", topicList);
    }

    /**
     * 开始随机练习
     *  生成随机练习任务，并返回试题
     * @param form
     * @return
     */
    @RequestMapping(value = "/start", method = RequestMethod.POST)
    public Result startExercise(@RequestBody RandomExerciseForm form){

        User user = this.getUser();
        if(user == null){
            throw new RuntimeException("用户信息异常，请重新登录...");
        }
        String userId = user.getId();
        form.setUserId(userId);
        form.setDate(new Date());

        Map<String, String> params = new HashMap<String, String>();
        params.put("classify", form.getClassify());
        params.put("type", form.getType());
        params.put("themeId", form.getTopic());
        params.put("difficulty", form.getDifficulty());

        int result = testQuestionService.getNumByConditions(params);
        if(result == 0){
            return Result.error("该设置暂无题目信息，请重新设置...");
        }

        String id = exerciseService.startExercise(form);

        return Result.ok().put("id", id);
    }

    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/preserve/{type}", method = RequestMethod.POST)
    public Result preserve(@RequestBody ThemeForm form){
        User user = getUser();

        exerciseService.preserve(form);

        return Result.ok();
    }

    /**
     * 获取题目信息
     * @param id 任务配置ID
     * @param index 获取题目index
     * @return
     */
    @RequestMapping(value = "/getQuestion", method = RequestMethod.GET)
    public Result getQuestion(@RequestParam("id") String id, @RequestParam("index") Integer index){
        User user = getUser();
        if(user == null){
            throw new RuntimeException("用户信息获取失败，请重新登录...");
        }
        QuestForm quest = exerciseService.getQuestion(id, index, user.getId());
        return Result.ok().put("question", quest);
    }

    /**
     * 提交主题练习
     * @param form
     * @return
     */
    @RequestMapping(value = "/commit", method = RequestMethod.POST)
    public Result commit(@RequestBody ThemeForm form){
        User user = getUser();
        if(user == null){
            throw new RuntimeException("用户信息获取失败，请重新登陆");
        }
        form.setUserId(form.getId());

        AnalysisForm resultForm = exerciseService.commit(form);

        return Result.ok().put("form", resultForm);
    }

    /**
     * 保存答题记录
     * @param form
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/saveAnswer", method = RequestMethod.POST)
    public Result saveAnswerRecord(@RequestBody ThemeAnswerForm form){
        User user = getUser();
        if(user == null){
            return Result.error("用户信息获取失败，请重新登录...");
        }
        form.setCreateTime(new Date());
        form.setCreateUser(user.getId());
        form.setId(IdWorker.getIdStr());

        boolean result = exerciseAnswerRecordService.saveForm(form);
        if(result){
            // 更新随机练习配置信息
            result = exerciseService.updateNum(1, form.getRight(), form.getTaskId());
            if(result){
                return Result.ok();
            } else {
                return Result.error("答题信息保存失败...");
            }

        } else {

            return Result.error("答题信息保存失败...");
        }
    }

}
