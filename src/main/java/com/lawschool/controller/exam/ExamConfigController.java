package com.lawschool.controller.exam;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.lawschool.annotation.SysLog;
import com.lawschool.base.AbstractController;
import com.lawschool.base.Page;
import com.lawschool.beans.Answer;
import com.lawschool.beans.TestQuestions;
import com.lawschool.beans.exam.ExamQueConfig;
import com.lawschool.form.*;
import com.lawschool.service.AnswerService;
import com.lawschool.service.TestQuestionService;
import com.lawschool.service.exam.ExamQueConfigService;
import com.lawschool.service.exam.NewExamConfigService;
import com.lawschool.service.system.DictionService;
import com.lawschool.service.system.TopicTypeService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lawschool.beans.exam.ExamConfig;
import com.lawschool.service.exam.ExamConfigService;
import com.lawschool.util.Result;

/**
 * @Title:ExamConfigController.java
 * @Description: 考试配置
 * @author: 王帅奇
 * @date 2018年12月10日
 */
@RestController
@RequestMapping("/exam/config")
public class ExamConfigController extends AbstractController {

    @Autowired
    private ExamConfigService examConfigService;

    @Autowired
    private DictionService dictionService;

    @Autowired
    private TopicTypeService topicTypeService;

    @Autowired
    private NewExamConfigService newExamConfigService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private TestQuestionService testQuestionService;

    @RequestMapping("/list")
    public Result list(@RequestParam Map<String, Object> params) {
        Result result;
        try {
            result = examConfigService.getExamList(params);
        } catch (ParseException e) {
            return Result.error(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/examConfig/preview", method = RequestMethod.POST)
    private Result examConfigPre(@RequestBody ExamConfigForm examConfigForm) {

        Result res ;
        try {
            res = newExamConfigService.preview(examConfigForm);

        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
        return res;
    }


    @SysLog("阅卷设置")
    @RequestMapping(value = "/checkset", method = RequestMethod.POST)
    private Result checkset(@RequestBody CheckSetForm checkSetForm) {

        System.out.println(checkSetForm.toString());
        examConfigService.checkset(checkSetForm);
        return Result.ok();
    }

    @RequestMapping(value = "/getCheckSetting", method = RequestMethod.GET)
    private Result getCheckSetting(String id) {

        CheckSetForm checkSetForm = examConfigService.getCheckSetting(id);
        return Result.ok().put("checkSetForm", checkSetForm);
    }

    @RequestMapping(value = "/getExamDetail", method = RequestMethod.GET)
    private Result getExamDetail(String id) {

        Result result = examConfigService.getOneExam(id);

        return result;
    }

    @SysLog("保存考试配置")
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    private Result saveOrUpdate(
            @RequestBody ExamConfig examConfig) {

        Result res = newExamConfigService.saveOrUpdate(examConfig, getUser());

        return res;
    }

    @SysLog("删除考试配置")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    private Result delete(
            @RequestParam("id") String id) {

        Result res = examConfigService.deleteExamConfig(id);

        return res;
    }


    @SysLog("生成自主考试配置")
    @RequestMapping(value = "/examConfig/genAutoQue", method = RequestMethod.POST)
    private Result genAutoQue(@RequestBody ExamConfigForm examConfigForm) {
        Result result = newExamConfigService.genAutoQue(examConfigForm);
        return result;
    }

    @SysLog("生成随机考试配置")
    @RequestMapping(value = "/examConfig/genRandomQue", method = RequestMethod.POST)
    private Result genRandomQue(@RequestBody ExamConfigForm examConfigForm) {
        Result result = null;
        try {
            result = newExamConfigService.genRandomQue(examConfigForm);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/examConfig/genRanQueAfterPreview", method = RequestMethod.POST)
    private Result genRanQueAfterPreview(@RequestBody ExamConfigForm examConfigForm) {
        Result result;
        try {
            result = newExamConfigService.genRanQueAfterPreview(examConfigForm);

        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/dict", method = RequestMethod.GET)
    private Result dict() {
        //examType考试类型字典
        List<CommonForm> etOption = dictionService.findCodeByType("EXAM_TYPE");
        //groupForm组卷方式字典
        List<CommonForm> gfOption = dictionService.findCodeByType("GROUP_FORM");
        //isMustTest是否必考字典
        List<CommonForm> imtOption = dictionService.findCodeByType("IS_MUST_TEST");
        //questionWay出题方式字典
        List<CommonForm> qwOption = dictionService.findCodeByType("QUESTION_WAY");
        //topicOrderType题目/选项顺序字典
        List<CommonForm> otOption = dictionService.findCodeByType("ORDER_TYPE");
        //answerShowRule答案显示规则
        List<CommonForm> asuOption = dictionService.findCodeByType("ANSWER_SHOW_RULE");
        //reachRewardType达标奖励类型
        List<CommonForm> rrtOption = dictionService.findCodeByType("REACH_REWARD_TYPE");
        //checkType阅卷方式
        List<CommonForm> ctOption = dictionService.findCodeByType("CHECK_TYPE");
        //specilKnowledge专项知识
        List<CommonForm> skOption = topicTypeService.findAll(null);
        //题目类型
        List<CommonForm> qtOption = dictionService.findCodeByType("QUESTION_TYPE");
        return Result.ok().put("etOption", etOption)
                .put("gfOption", gfOption)
                .put("imtOption", imtOption)
                .put("qwOption", qwOption)
                .put("otOption", otOption)
                .put("asuOption", asuOption)
                .put("rrtOption", rrtOption)
                .put("ctOption", ctOption)
                .put("skOption", skOption)
                .put("qtOption", qtOption);
    }

    /**
     * 查询专项知识试题
     */
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public Result info(@PathVariable("id") String id) {
        List<Answer> list = answerService.getAnswerByQid(id);
        return Result.ok().put("data", list);
    }

    @RequestMapping(value = "/getQueList", method = RequestMethod.GET)
    public Result getQueList(@RequestParam Map<String, Object> params, @RequestParam("ids[]") List lists) {

        String typeId = (String) params.get("typeId");
        String questionDifficulty = (String) params.get("questionDifficulty");
        String questionType = (String) params.get("questionType");
        String queContent = (String) params.get("comContent");
        String isEnble = (String) params.get("isEnble");
        String specialKnowledgeId = (String) params.get("specialKnowledgeId");
        // List<String> ids = (List<String>) params.get("ids");

        TestQuestions testQuestions = new TestQuestions();
        testQuestions.setTypeId(typeId);
        testQuestions.setQuestionDifficulty(questionDifficulty);
        testQuestions.setQuestionType(questionType);
        testQuestions.setComContent(queContent);
        testQuestions.setIsEnble(isEnble);
        testQuestions.setIds(lists);
        testQuestions.setSpecialKnowledgeId(specialKnowledgeId);

        Page<TestQuestions> page = testQuestionService.findPage(new Page<TestQuestions>(params), testQuestions);
        List<TestQuestions> list = page.getList();
        for (TestQuestions tes : list) {
            tes.setAnswerList(answerService.getAnswerByQid(tes.getId()));
        }
        page.setList(list);
        return Result.ok().put("page", page);
    }
}