package com.lawschool.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.annotation.SysLog;
import com.lawschool.base.Page;
import com.lawschool.beans.Answer;
import com.lawschool.beans.TestQuestions;
import com.lawschool.service.AnswerService;
import com.lawschool.service.TestQuestionService;
import com.lawschool.util.Result;
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
        String queContent = (String) params.get("comContent");
        String isEnble = (String) params.get("isEnble");
        String specialKnowledgeId = (String) params.get("specialKnowledgeId");


        TestQuestions testQuestions = new TestQuestions();
        testQuestions.setTypeId(typeId);
        testQuestions.setQuestionDifficulty(questionDifficulty);
        testQuestions.setQuestionType(questionType);
        testQuestions.setComContent(queContent);
        testQuestions.setIsEnble(isEnble);
        testQuestions.setSpecialKnowledgeId(specialKnowledgeId);

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
        List<Answer> answerList = answerService.getAnswerByQid(testQuestions.getId());
        String answerId = testQuestions.getAnswerId();

        List<String> list = new ArrayList<String>();
        if(StringUtils.isNotBlank(answerId)){
            list = Arrays.asList(answerId.split(","));
        }

        for(Answer answer : answerList){

            if(list.contains(answer.getId())){
                answer.setIsAnswer(1);
            } else {
                answer.setIsAnswer(0);
            }
        }
        testQuestions.setAnswerList(answerList);
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
        //假删除
//        List<String> idList = new ArrayList<>(1);
//        idList.add(id);
//
//        testQuestionService.delete(idList);
//        // 删除答案
//        answerService.deleteByQuestionIds(idList);

        TestQuestions tq= testQuestionService.selectById(id);
        tq.setStatus("0");
        testQuestionService.updateById(tq);
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




    /**
     * 修改试题状态   启用，禁用,发布
     */
    @RequestMapping(value = "/changisEnble", method = RequestMethod.POST)
    public Result changisEnble(String id,String isEnble) {

        TestQuestions tq=   testQuestionService.selectById(id);

        if(isEnble.equals("3")) {
            tq.setReleaseStatus("1");

        }
        else{
            tq.setIsEnble(isEnble);

        }
        testQuestionService.updateById(tq);
        return Result.ok();
    }




    //试题名称重复问题
    @RequestMapping("/tqComContent")
    public Result tqComContent(String comContent,String mytype,String id){
        String type="";

        TestQuestions tq=null;

        if(mytype.equals("1"))
        {
            tq= testQuestionService.selectOne(new EntityWrapper<TestQuestions>().eq("COM_CONTENT",comContent));

        }
        else if(mytype.equals("2"))
        {
            tq= testQuestionService.selectOne(new EntityWrapper<TestQuestions>().eq("COM_CONTENT",comContent).ne("ID",id));

        }

        if(tq==null)
        {
            type="0";
        }
        else
        {
            type="1";
        }
        return Result.ok().put("type",type);

    }
}
