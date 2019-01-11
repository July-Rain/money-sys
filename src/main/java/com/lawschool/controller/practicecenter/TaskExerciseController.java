package com.lawschool.controller.practicecenter;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.base.AbstractController;
import com.lawschool.base.Page;
import com.lawschool.beans.User;
import com.lawschool.beans.practicecenter.TaskExerciseConfigureEntity;
import com.lawschool.beans.practicecenter.TaskExerciseEntity;
import com.lawschool.form.ThemeAnswerForm;
import com.lawschool.service.practicecenter.TaskExerciseService;
import com.lawschool.util.Result;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

        // 获取登录用户信息
        User user = getUser();

        // 初始化查询参数
        TaskExerciseEntity entity = new TaskExerciseEntity();
        entity.setCreateUser(user.getId());

        Page<TaskExerciseEntity> page = taskExerciseService.findPage(
                new Page<TaskExerciseEntity>(params), entity
        );

        return Result.ok().put("page", page);
    }

    /**
     * 开始任务
     * @param id 个人任务ID
     * @param taskId 任务配置ID
     * @param limit 每页显示题目数量
     * @param page 当前页
     * @return
     */
    @RequestMapping(value = "/paper", method = RequestMethod.GET)
    public Result start(@RequestParam(required = false) String id,
                        @RequestParam String taskId,
                        @RequestParam Integer limit,
                        @RequestParam Integer page){

        // 是否需要创建个人任务
        boolean isNew = false;
        if(StringUtils.isBlank(id) || "null".equals(id)){
            // 需要创建新的个人任务
            id = IdWorker.getIdStr();
            isNew = true;
        }

        User user = getUser();
        Map<String, Object> resultMap = taskExerciseService.showPaper(taskId, id, user.getId(),
                isNew, limit, page);

        return Result.ok().put("result", resultMap);
    }

    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/preserve/{type}", method = RequestMethod.POST)
    public Result preserve(@RequestBody List<ThemeAnswerForm> formList,
                           @PathVariable("type") Integer type){
        User user = getUser();

        if(CollectionUtils.isNotEmpty(formList)){
            taskExerciseService.preserve(formList, user.getId());
            if(type == 1){
                // 提交，更新任务状态
                taskExerciseService.updateStatus(formList.get(0).getTaskId(), TaskExerciseEntity.STATUS_OFF);
            }
        }

        return Result.ok();
    }

    /**
     * 提交操作
     * @param id
     * @return
     */
    @RequestMapping(value = "/commit/{id}", method = RequestMethod.POST)
    public Result commit(@PathVariable("id") String id){
        User user = getUser();

        // 提交，更新任务状态
        taskExerciseService.updateStatus(id, TaskExerciseEntity.STATUS_OFF);

        return Result.ok();
    }
}
