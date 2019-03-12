package com.lawschool.controller;

import com.lawschool.annotation.SysLog;
import com.lawschool.base.AbstractController;
import com.lawschool.base.Page;
import com.lawschool.beans.*;
import com.lawschool.form.CommonForm;
import com.lawschool.service.AnswerService;
import com.lawschool.service.ManuscriptService;
import com.lawschool.service.StuMediaService;
import com.lawschool.service.TestQuestionService;
import com.lawschool.util.Result;
import com.lawschool.util.UtilValidate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ManuscriptController
 * @Author wangtongye
 * @Date 2018/12/27 17:31
 * @Versiom 1.0
 **/
@RestController
@RequestMapping("/manuscript")
public class ManuscriptController extends AbstractController {

    @Autowired
    private ManuscriptService manuscriptService;

    @Autowired
    private TestQuestionService testQuestionService;

    @Autowired
    private StuMediaService StuMediaService;

    @Autowired
    private AnswerService answerService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(@RequestParam Map<String, Object> params){
        ManuscriptEntity manuscriptEntity=new ManuscriptEntity();
        if(UtilValidate.isNotEmpty(params.get("createUser"))){
            manuscriptEntity.setCreateUser((String)params.get("createUser"));
        }
        Page<ManuscriptEntity> page = manuscriptService.findPage(new Page<ManuscriptEntity>(params),
                manuscriptEntity);
        return Result.ok().put("page", page);
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Result info(@RequestParam("id") String id){
        ManuscriptEntity entity = manuscriptService.findOne(id);

        Integer type = entity.getType();
        String sourceId = entity.getSourceId();
        if(type == 0){
            TestQuestions test = testQuestionService.findOne(sourceId);
            // 获取习题的选项信息
            List<Answer> anList = answerService.getAnswerByQid(test.getId());
            String answerIds = test.getAnswerId();
            if(StringUtils.isBlank(answerIds)){
                throw new RuntimeException("习题信息错误...");
            }
            String[] arr = answerIds.split(",");

            for(Answer answer : anList){
                String key = answer.getId();
                if(Arrays.asList(arr).contains(key)){
                    answer.setIsAnswer(1);
                } else {
                    answer.setIsAnswer(0);
                }
            }
            test.setAnswerList(anList);
            entity.setTest(test);
        } else {
            StuMedia media = StuMediaService.findOne(sourceId);
            entity.setStu(media);
        }

        return Result.ok().put("data", entity);
    }

    /**
     * 保存
     * @param entity
     * @return
     */
    @SysLog("保存题库")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestBody ManuscriptEntity entity){
        User user = getUser();

        String author = user.getUserName();
        entity.setAuthor(author);

        boolean result = manuscriptService.mySave(entity);
        if(result){
            return Result.ok();
        } else {
            return Result.error("保存失败...");
        }
    }

    @SysLog("删除题库")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public Result delete(@PathVariable("id") String id){
        manuscriptService.myDelete(id);
        return Result.ok();
    }

    @SysLog("审核题库")
    @RequestMapping(value = "/examine", method = RequestMethod.POST)
    public Result examine(@RequestBody CommonForm form){

        String id = form.getKey();
        String type = form.getValue();
        String opinion = form.getOpinion();
        if(opinion==null){
            opinion="";
        }
        String userid=getUser().getId();
        boolean result = manuscriptService.examine(id, type,userid,opinion);
        if(result){
            return Result.ok();
        } else {
            return Result.error("审核失败，请重新审核...");
        }
    }
}
