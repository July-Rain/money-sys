package com.lawschool.controller;


import com.lawschool.beans.StuMedia;
import com.lawschool.beans.TestQuestions;
import com.lawschool.service.TestQuestionService;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/testQuestion")
public class TestQuestionController extends AbstractController{

    @Autowired
    TestQuestionService testQuestionService;

    @RequestMapping("/mycollection")
    public Result listMyCollection(Map<String,Object> params){
        //params.put("userId",getUser().getUserId());
        params.put("userId",1);
        List<TestQuestions> stuMedias = testQuestionService.listMyCollection(params);
        return Result.ok().put("result",stuMedias);
    }

    //获取课件信息信息
    @RequestMapping("/getTestQuestion")
    public Result getStuMedia(TestQuestions testQuestions){
        TestQuestions info = testQuestionService.getTestQuestions(testQuestions);
        return Result.ok().put("info",info);
    }
}
