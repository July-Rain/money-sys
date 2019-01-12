package com.lawschool.controller.practicecenter;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.base.AbstractController;
import com.lawschool.base.Page;
import com.lawschool.beans.ExerciseConfigureEntity;
import com.lawschool.beans.User;
import com.lawschool.beans.practicecenter.PaperExerciseEntity;
import com.lawschool.form.ThemeAnswerForm;
import com.lawschool.service.ExerciseConfigureService;
import com.lawschool.service.practicecenter.PaperExerciseService;
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
 * @Date: 2018/12/21 11:34
 * @Description: 练习中心，组卷练习
 */
@RestController
@RequestMapping("/exercise/paper")
public class PaperExerciseController extends AbstractController {

    @Autowired
    private ExerciseConfigureService exerciseConfigureService;

    @Autowired
    private PaperExerciseService paperExerciseService;

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
        entity.setCreateUser(user.getId());
        entity.setDelFlag(0);

        Page<ExerciseConfigureEntity> page = exerciseConfigureService.findPageByUser(
                new Page<ExerciseConfigureEntity>(params), entity
        );

        return Result.ok().put("page", page);
    }

    /**
     * 展示试卷信息（带分页）
     *
     * @param configureId 配置ID
     * @param id 练习ID
     * @param limit 每页数量
     * @param page 当前页
     * @return
     */
    @RequestMapping(value = "/paper", method = RequestMethod.GET)
        public Result showPaper(@RequestParam String configureId,
                                @RequestParam(required = false) String id,
                                @RequestParam Integer limit,
                                @RequestParam Integer page){

        // 标识是否需要创建个人练习任务，默认为false
        Boolean isNew = false;

        if(StringUtils.isBlank(id) || "null".equals(id)){
            // 生成ID
            id = IdWorker.getIdStr();
            isNew = true;
        }

        User user = getUser();
        Map<String, Object> resultMap = paperExerciseService.showPaper(configureId, id,
                                                                   user.getId(), isNew,
                                                                   limit, page);

        resultMap.put("id", id);

        return Result.ok().put("page", resultMap);
    }

    /**
     * 保存答题情况
     * @param formList
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/preserve/{type}", method = RequestMethod.POST)
    public Result preserve(@RequestBody List<ThemeAnswerForm> formList,
                           @PathVariable("type") Integer type){
        User user = getUser();

        if(CollectionUtils.isNotEmpty(formList)){
            paperExerciseService.preserve(formList, user.getId());
            if(type == 1){
                // 提交，更新任务状态
                paperExerciseService.updateStatus(formList.get(0).getTaskId(), PaperExerciseEntity.STATUS_OFF);
            }
        }

        return Result.ok();
    }

    /**
     * 提交
     * @param id
     * @return
     */
    @RequestMapping(value = "/commit/{id}", method = RequestMethod.POST)
    public Result commit(@PathVariable("id") String id){
        User user = getUser();

        // 提交，更新任务状态
        paperExerciseService.updateStatus(id, PaperExerciseEntity.STATUS_OFF);

        return Result.ok();
    }
}
