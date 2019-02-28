package com.lawschool.controller.practicecenter;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.annotation.SysLog;
import com.lawschool.base.AbstractController;
import com.lawschool.base.Page;
import com.lawschool.beans.User;
import com.lawschool.beans.practicecenter.TaskExerciseEntity;
import com.lawschool.form.CommonForm;
import com.lawschool.form.QuestForm;
import com.lawschool.form.ThemeAnswerForm;
import com.lawschool.service.practicecenter.TaskExerciseService;
import com.lawschool.util.Result;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Moon
 * @Date: 2019/1/2 16:53
 * @Description:
 */
@RestController
@RequestMapping("/exercise/task")
public class TaskExerciseController extends AbstractController {

    @Autowired
    private TaskExerciseService taskExerciseService;

    /**
     * 获取个人任务分页列表
     * @param params
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(@RequestParam Map<String, Object> params){

        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");

        // 获取登录用户信息
        User user = getUser();

        // 初始化查询参数
        TaskExerciseEntity entity = new TaskExerciseEntity();
        entity.setCreateUser(user.getId());
        if(params.get("name") != null){
            entity.setName(String.valueOf(params.get("name")));
        }

        try{
            if(params.get("kssj") != null && params.get("kssj") != ""){
                entity.setKssj(sim.parse(String.valueOf(params.get("kssj"))));
            }
            if(params.get("jssj") != null && params.get("jssj") != ""){
                entity.setJssj(sim.parse(String.valueOf(params.get("jssj"))));
            }
        } catch (Exception e){
            return Result.error("日期格式错误，请修正...");
        }

        Page<TaskExerciseEntity> page = taskExerciseService.findPage(
                new Page<TaskExerciseEntity>(params), entity
        );

        return Result.ok().put("page", page);
    }

    /**
     * 开始任务
     * @param id 个人任务ID
     * @param taskId 任务配置ID
     * @param index 当前题目序号
     * @param isReview 有值为错题回顾
     * @return
     */
    @RequestMapping(value = "/paper", method = RequestMethod.GET)
    public Result start(@RequestParam(required = false) String id,
                        @RequestParam String taskId,
                        @RequestParam Integer index,
                        @RequestParam(required = false) String isReview){

        // 是否需要创建个人任务
        boolean isNew = false;
        if(StringUtils.isBlank(id) || "null".equals(id)){
            // 需要创建新的个人任务
            id = IdWorker.getIdStr();
            isNew = true;
        }

        User user = getUser();
        Map<String, Object> resultMap = taskExerciseService.showPaper(taskId, id, user.getId(),
                isNew, index, isReview);

        id = String.valueOf(resultMap.get("id"));
        QuestForm form = (QuestForm)resultMap.get("question");

        return Result.ok().put("id", id).put("question", form);
    }

    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/preserve", method = RequestMethod.POST)
    public Result preserve(@RequestBody ThemeAnswerForm form){
        User user = getUser();

        form.setId(IdWorker.getIdStr());
        form.setCreateUser(user.getId());
        form.setCreateTime(new Date());
        taskExerciseService.preserve(form);

        return Result.ok().put("recordId", form.getId());
    }

    /**
     * 提交操作
     * @param id
     * @return
     */
    @SysLog("提交练习")
    @RequestMapping(value = "/commit/{id}", method = RequestMethod.POST)
    public Result commit(@PathVariable("id") String id){
        User user = getUser();

        // 提交，更新任务状态
        taskExerciseService.updateStatus(id, TaskExerciseEntity.STATUS_OFF);

        return Result.ok();
    }

    /**
     * 收藏题目
     * @return
     */
    @SysLog("收藏题目")
    @RequestMapping(value = "/doCollect/{type}", method = RequestMethod.POST)
    public Result doCollect(@RequestBody CommonForm params, @PathVariable("type") Integer type){

        String id = params.getKey();// 题目ID
        String recordId = params.getValue();// 记录ID

        taskExerciseService.doCollect(id, recordId, type);

        return Result.ok();
    }
}
