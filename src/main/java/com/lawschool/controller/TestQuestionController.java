package com.lawschool.controller;


import com.lawschool.annotation.SysLog;
import com.lawschool.base.AbstractController;
import com.lawschool.base.Page;
import com.lawschool.beans.Answer;
import com.lawschool.beans.TestQuestions;
import com.lawschool.service.AnswerService;
import com.lawschool.service.TestQuestionService;
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
        List<Answer> answerList = testQuestions.getAnswerList();
        if(CollectionUtils.isEmpty(answerList)){
            return Result.error("请设置选项信息...");
        } else {
            testQuestions.setAnswerChoiceNumber(answerList.size()+"");
        }

        testQuestionService.save(testQuestions);

        // 先删除问题下的答案
        answerService.deleteByQuestionId(testQuestions.getId());

        String answerId = "";// 正确答案ID
        // 重新保存答案
        for (Answer answer : answerList){
            answer.setQuestionId(testQuestions.getId());
            answerService.save(answer);
            if(answer.getIsAnswer() == 1){
                answerId += answer.getId() + ",";
            }
        }
        if(StringUtils.isNotBlank(answerId)){
            // 更新实体信息
            testQuestionService.updateAnswerId(testQuestions.getId(), answerId.substring(0, answerId.length()-1));
        } else {
            throw new RuntimeException("请设置正确答案...");
        }

        return Result.ok();
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
