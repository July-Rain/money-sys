package com.lawschool.controller;


import com.lawschool.beans.StuMedia;
import com.lawschool.beans.TestQuestions;
import com.lawschool.dao.TestQuestionsMapper;
import com.lawschool.service.TestQuestionService;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/testQuestion")
public class TestQuestionController extends AbstractController{

    @Autowired
    private TestQuestionService testQuestionService;
    /**
     * 查询所有的专项知识试题（模糊查询）
     */
    @RequestMapping("/index")
    public String index(TestQuestions testQuestions, @RequestParam(value = "pn",defaultValue = "1")String pageNo, ModelMap map )
    {
        PageInfo<TestQuestions> page = testQuestionService.findPage(testQuestions,pageNo);
        map.addAttribute("data",page);
        return "index";
    }
    /**
     * 查询专项知识试题
     */
    @RequestMapping("findById")
    public String findById(String id,Model model)
    {
        TestQuestions testQuestions = testQuestionService.findById(id);
        model.addAttribute("data",testQuestions);
        return "modify";
    }
    /**
     * 编辑试题
     */
    @RequestMapping("/modify")
    public String modify(TestQuestions testQuestions){
        testQuestionService.modify(testQuestions);
        return "redirect:/testQuestions/index";
    }
    /* *//**
     * 禁用启用（回头看看）
     *//*
    @RequestMapping("/modifyStatus")
    public String modifyStatus(String id,String disableStatus);
    return "redirect:/testQuestions/index";*/
    /**
     * 删除专项知识试题
     */
    @RequestMapping("deleteById")
    public String deleteById(String id){
        testQuestionService.deleteById(id);
        return "redirect:/testQuestions/index";
    }
    /**
     * 新增专项知识试题
     */
    @RequestMapping("/add")
    public String add(TestQuestions testQuestions)
    {
        testQuestionService.add(testQuestions);
        return "redirect:/testQuestions/index";
    }
    /**
     * 批量导入试题并查询所有
     */
    @RequestMapping("/addBatch")
    @ResponseBody
    public Map addBatch(@RequestBody List<TestQuestions> ids)
    {
        List<TestQuestions> all = testQuestionService.addBatch(ids);

        Map ajaxResult = new HashMap();
        ajaxResult.put("success",true);
        ajaxResult.put("all",all);
        return ajaxResult;
    }

    /**
     * 树形
     */
    @RequestMapping("/queryParents")
    @ResponseBody
    public List<TestQuestions> queryParents(){
        return testQuestionService.queryParents();
    }








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
