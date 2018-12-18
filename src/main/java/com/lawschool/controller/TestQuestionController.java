package com.lawschool.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.base.AbstractController;
import com.lawschool.base.Page;
import com.lawschool.beans.TestQuestions;
import com.lawschool.service.TestQuestionService;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/testQuestion")
public class TestQuestionController extends AbstractController {

    @Autowired
    private TestQuestionService testQuestionService;


    /**
     * 查询所有的专项知识试题
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(@RequestParam Map<String, Object> params) {

        String typeId = (String) params.get("typeId");
        String questionDifficulty = (String) params.get("questionDifficulty");
        String questionType = (String) params.get("questionType");

        TestQuestions testQuestions = new TestQuestions();
        testQuestions.setTypeId(typeId);
        testQuestions.setQuestionDifficulty(questionDifficulty);
        testQuestions.setQuestionType(questionType);

        Page<TestQuestions> page = testQuestionService.findPage(new Page<TestQuestions>(params), testQuestions);
        return Result.ok().put("page", page);
    }

    /**
     * 查询专项知识试题
     */
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public Result info(@PathVariable("id") String id) {
       // TestQuestions testQuestions = testQuestionService.findOne(id);
        return Result.ok().put("testQuestions", null);
    }

    /**
     * 保存试题
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestBody TestQuestions testQuestions) {
        testQuestionService.save(testQuestions);
        return Result.ok();
    }

    /**
     * 禁用启用
     */
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    public Result updateStatus(@RequestBody String id, @RequestBody String isEnble) {
        testQuestionService.updateStatus(id, isEnble);
        return Result.ok();
    }

    /**
     * 删除专项知识试题
     */
    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public Result deleteById(@RequestBody List<String> idList) {
        //testQuestionService.delete(idList);
        return Result.ok();
    }

    /**
     * 批量导入试题并查询所有
     */
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public Result importTestQuestions(@RequestBody List<TestQuestions> list) {
        //TODO
        testQuestionService.importTestQuestions(list);
        return Result.ok();
    }

}
