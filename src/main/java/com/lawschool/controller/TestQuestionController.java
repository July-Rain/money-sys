package com.lawschool.controller;


import com.lawschool.beans.Answer;
import com.lawschool.beans.StuMedia;
import com.lawschool.beans.TestQuestions;
import com.lawschool.service.AnswerService;
import com.lawschool.service.TestQuestionService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/testQuestion")
public class TestQuestionController extends AbstractController{

    @Autowired
    TestQuestionService testQuestionService;

    @Autowired
    AnswerService answerService;

    //我收藏的题目z
    @RequestMapping("/mycollection")
    public Result listMyCollection(@RequestParam Map<String,Object> params){
        //params.put("userId",getUser().getUserId());
        params.put("userId",1);
        PageUtils pageUtils = testQuestionService.listMyCollection(params);
        return Result.ok().put("result",pageUtils);
    }

    //一键组卷-我收藏的题目z
    @RequestMapping("/randomQuestColl")
    public Result randomQuestColl(@RequestParam Map<String,Object> params){
        //params.put("userId",getUser().getUserId());
        params.put("userId",1);
        Map<TestQuestions, List<Answer>> testQuestionsListMap = testQuestionService.randomQuestColl(params);
        return Result.ok().put("result",testQuestionsListMap);
    }


    //我做错的题目z
    @RequestMapping("/myerror")
    public Result listMyErrorQuestion(@RequestParam Map<String,Object> params){
        //params.put("userId",getUser().getUserId());
        params.put("userId",1);
        PageUtils pageUtils = testQuestionService.listMyErrorQuestion(params);
        return Result.ok().put("result",pageUtils);
    }

    //一键组卷-我做错的题目z
    @RequestMapping("/randomErrorColl")
    public Result randomErrorColl(@RequestParam Map<String,Object> params){
        //params.put("userId",getUser().getUserId());
        params.put("userId",1);
        Map<TestQuestions, List<Answer>> testQuestionsListMap = testQuestionService.randomErrorColl(params);
        return Result.ok().put("result",testQuestionsListMap);
    }

    //获取试题详情z
    @RequestMapping("/getTestQuestion")
    public Result getTestQuestion(@RequestBody TestQuestions testQuestions){
        TestQuestions info = testQuestionService.getTestQuestions(testQuestions);
        List<Answer> answerByQid = answerService.getAnswerByQid(testQuestions.getId());
        return Result.ok().put("qustion",info).put("answer",answerByQid);
    }
}
