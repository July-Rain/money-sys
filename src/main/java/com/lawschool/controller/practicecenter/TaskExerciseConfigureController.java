package com.lawschool.controller.practicecenter;

import com.lawschool.base.AbstractController;
import com.lawschool.base.Page;
import com.lawschool.beans.User;
import com.lawschool.beans.practicecenter.TaskExerciseConfigureEntity;
import com.lawschool.form.TaskConfigureForm;
import com.lawschool.service.practicecenter.TaskExerciseConfigureService;
import com.lawschool.util.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Auther: Moon
 * @Date: 2018/12/29 14:07
 * @Description: 练习中心--练习任务配置--Controller
 */
@RestController
@RequestMapping("/taskConfigure")
public class TaskExerciseConfigureController extends AbstractController {

    @Autowired
    private TaskExerciseConfigureService service;

    /**
     * 分页列表
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(@RequestParam Map<String, Object> params){

        // 获取登录用户信息
        User user = getUser();

        // 初始化查询参数
        TaskExerciseConfigureEntity entity = new TaskExerciseConfigureEntity();
        entity.setCreateUser(user.getId());
        entity.setDelFlag(TaskExerciseConfigureEntity.DEL_NORMAL);
        if(params.get("source") != null){
            entity.setSource(Integer.parseInt(params.get("source").toString()));
        }

        Page<TaskExerciseConfigureEntity> page = service.findPage(
                new Page<TaskExerciseConfigureEntity>(params), entity
        );

        return Result.ok().put("page", page);
    }

    /**
     * 保存配置
     * @param form
     * @param source，来源 0个人、1部门
     * @return
     */
    @RequestMapping(value = "/save/{source}", method = RequestMethod.POST)
    public Result save(@RequestBody TaskConfigureForm form,
                       @PathVariable("source") Integer source){

        User user = getUser();

        TaskExerciseConfigureEntity entity = new TaskExerciseConfigureEntity();

        entity.setName(form.getName());
        entity.setCreateUser(user.getId());
        entity.setSource(source);
        entity.setDifficulty(StringUtils.join(form.getDifficultys(), ","));
        entity.setClassify(StringUtils.join(form.getClassifys(), ","));
        entity.setType(StringUtils.join(form.getTypes(), ","));
        entity.setThemeId(StringUtils.join(form.getTopics(), ","));
        entity.setThemeName(form.getThemeName());
        entity.setCreateName(user.getUserName());
        entity.setId(form.getId());

        if(source == 0){
            // 个人设置
            entity.setUsers(user.getId());
        } else {
            entity.setUsers(StringUtils.join(form.getUsers(), ","));
            entity.setDepts(StringUtils.join(form.getDepts(), ","));
        }

        service.mySave(entity);

        return Result.ok();
    }

    /**
     * 删除，逻辑删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public Result delete(@PathVariable("id") String id){

        boolean result = service.logicDelete(id);
        if(result){
            return Result.ok().put("msg", "删除成功");
        } else {

            return Result.error("删除失败");
        }

    }

    /**
     * 获取单个设置信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public Result info(@PathVariable("id") String id){

        TaskExerciseConfigureEntity info = service.findOne(id);

        return Result.ok().put("info", info);
    }

}
