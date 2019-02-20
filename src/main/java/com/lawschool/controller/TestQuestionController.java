package com.lawschool.controller;


import com.lawschool.annotation.SysLog;
import com.lawschool.base.Page;
import com.lawschool.beans.TestQuestions;
import com.lawschool.service.AnswerService;
import com.lawschool.service.TestQuestionService;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/testQuestion")
public class TestQuestionController {

    @Autowired
    private TestQuestionService testQuestionService;

    @Autowired
    private AnswerService answerService;

    /**
     * 查询所有的专项知识试题
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(@RequestParam Map<String, Object> params) {

        String typeId = (String) params.get("typeId");
        String questionDifficulty = (String) params.get("questionDifficulty");
        String questionType = (String) params.get("questionType");
        String queContent = (String) params.get("queContent");

        TestQuestions testQuestions = new TestQuestions();
        testQuestions.setTypeId(typeId);
        testQuestions.setQuestionDifficulty(questionDifficulty);
        testQuestions.setQuestionType(questionType);
        testQuestions.setComContent(queContent);
        Page<TestQuestions> page = testQuestionService.findPage(new Page<TestQuestions>(params), testQuestions);
        List<TestQuestions> list = page.getList();
        for(TestQuestions tes : list){
            tes.setAnswerList(answerService.getAnswerByQid(tes.getId()));
        }
        page.setList(list);
        return Result.ok().put("page", page);
    }

    /**
     * 查询专项知识试题
     */
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public Result info(@PathVariable("id") String id) {
        TestQuestions testQuestions = testQuestionService.findOne(id);
        testQuestions.setAnswerList(answerService.getAnswerByQid(testQuestions.getId()));
        return Result.ok().put("data", testQuestions);
    }

    /**
     * 保存试题
     */
    @SysLog("保存试题")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public Result save(@RequestBody TestQuestions testQuestions) {

        boolean result = testQuestionService.mySave(testQuestions);
        if(result){
            return Result.ok();
        } else {
            return Result.error("保存失败");
        }
    }

    /**
     * 禁用启用
     */
    @SysLog("禁用启用试题")
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    public Result updateStatus(@RequestBody String id, @RequestBody String isEnble) {
        testQuestionService.updateStatus(id, isEnble);
        return Result.ok();
    }

    /**
     * 删除试题
     */
    @SysLog("删除试题")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public Result deleteById(@PathVariable("id") String id) {
        List<String> idList = new ArrayList<>(1);
        idList.add(id);

        testQuestionService.delete(idList);
        // 删除答案
        answerService.deleteByQuestionIds(idList);
        return Result.ok();
    }

    /**
     * 批量导入试题并查询所有
     */
    @SysLog("导入试题")
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public Result importTestQuestions(@RequestBody List<TestQuestions> list) {
        //TODO
        testQuestionService.importTestQuestions(list);
        return Result.ok();
    }

}
