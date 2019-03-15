package com.lawschool.controller.practicecenter;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.annotation.SysLog;
import com.lawschool.base.AbstractController;
import com.lawschool.base.Page;
import com.lawschool.beans.ExerciseConfigureEntity;
import com.lawschool.beans.User;
import com.lawschool.beans.practicecenter.PaperExerciseEntity;
import com.lawschool.form.QuestionForm;
import com.lawschool.form.ThemeAnswerForm;
import com.lawschool.service.ExerciseConfigureService;
import com.lawschool.service.practicecenter.PaperExerciseService;
import com.lawschool.util.Result;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
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

    @Autowired private ExerciseConfigureService exerciseConfigureService;

    @Autowired private PaperExerciseService paperExerciseService;

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
     * @return
     */
    @RequestMapping(value = "/paper", method = RequestMethod.GET)
    public Result showPaper(@RequestParam String configureId,
                            @RequestParam(required = false) String id){

        // 标识是否为新的练习，默认否
        boolean isNew= false;

        if(StringUtils.isBlank(id) || "null".equals(id)){
            // 新的练习，尚无任务记录
            isNew = true;
            id = IdWorker.getIdStr();
        }

        // 结果集按题型排序，单选、多选、判断
        List<QuestionForm> list = paperExerciseService.showQuestions(configureId, id, isNew);

        // 根据题目类型统计题型对应的题目数量
        int pdNum = 0;// 判断题
        int singleNum = 0;// 单选
        int multiNum = 0;// 多选

        for(QuestionForm form : list){
            String type = form.getType();

            if("10004".equals(type)){
                // 单选
                singleNum++;
                form.setUserAnswerStr(form.getUserAnswer());
                form.setUserAnswerList(new ArrayList<>());

            } else if("10005".equals(type)){
                // 多选
                multiNum++;
                List<String> answers = new ArrayList<>();
                if(StringUtils.isNotBlank(form.getUserAnswer())){
                    answers = Arrays.asList(form.getUserAnswer().split(","));
                }
                form.setUserAnswerList(answers);

            } else {
                pdNum++;
                form.setUserAnswerStr(form.getUserAnswer());
                form.setUserAnswerList(new ArrayList<>());
            }
        }

        return Result.ok()
                .put("pdNum", pdNum).put("singleNum", singleNum)
                .put("multiNum", multiNum).put("id", id).put("questionList", list);
    }
}
