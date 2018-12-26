package com.lawschool.controller.practicecenter;

import com.lawschool.base.AbstractController;
import com.lawschool.base.Page;
import com.lawschool.beans.ExerciseConfigureEntity;
import com.lawschool.beans.User;
import com.lawschool.service.ExerciseConfigureService;
import com.lawschool.service.practicecenter.TaskExerciseService;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Auther: Moon
 * @Date: 2018/12/21 11:34
 * @Description: 练习中心，练习任务
 */
@RestController
@RequestMapping("/exercise/task")
public class TaskExerciseController extends AbstractController {

    @Autowired
    private ExerciseConfigureService exerciseConfigureService;

    @Autowired
    private TaskExerciseService taskExerciseService;

    /**
     * 分页列表
     * @param params
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(@RequestParam Map<String, Object> params){
        User user = getUser();

        ExerciseConfigureEntity entity = new ExerciseConfigureEntity();
        entity.setUsers(user.getId());
        entity.setDepts(user.getOrgCode());

        Page<ExerciseConfigureEntity> page = exerciseConfigureService.findPageByUser(
                new Page<ExerciseConfigureEntity>(params), entity
        );

        return Result.ok().put("page", page);
    }
}
