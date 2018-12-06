package com.lawschool.controller;


import com.lawschool.beans.Answer;
import com.lawschool.beans.StuMedia;
import com.lawschool.beans.TestQuestions;
import com.lawschool.beans.User;
import com.lawschool.constants.StatusConstant;
import com.lawschool.service.AnswerService;
import com.lawschool.service.TestQuestionService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.ResponseResult;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/testQuestion")
public class TestQuestionController extends AbstractController{

    @Autowired
    private TestQuestionService testQuestionService;

    @Autowired
    AnswerService answerService;

    /**
     * 查询所有的专项知识试题（模糊查询）
     */

    @RequestMapping("/index")
    public Result index(@RequestParam Map<String, Object> params) {

//        Page<TestQuestions> page = testQuestionService.findPage(params);
        return Result.ok();
    }

    /**
     * 查询专项知识试题
     */
    @RequestMapping("/findById")
    public String findById(String id) {
        TestQuestions testQuestions = testQuestionService.findById(id);
        return "modify";
    }

    /**
     * 编辑试题
     */
    @RequestMapping("/modify")
    public String modify(TestQuestions testQuestions) {
        testQuestionService.modify(testQuestions);
        return "redirect:/testQuestions/index";
    }

    /**
     * 禁言启用
     */
    @RequestMapping("/modifyStatus")
    @ResponseBody
    public ResponseResult modifyStatus(String id, String releaseStatus) {

        ResponseResult result = new ResponseResult();

        BigDecimal typeStatus = new BigDecimal(releaseStatus);
        testQuestionService.modifyStatus(id, typeStatus);
        result.setResponseCode(StatusConstant.RESPONSE_CODE_SUCCESS);
        result.setMessage("成功");
        return result;
    }

    /**
     * 删除专项知识试题
     */
    @RequestMapping("deleteById")
    public String deleteById(String id) {
        testQuestionService.deleteById(id);
        return "redirect:/testQuestions/index";
    }

    /**
     * 新增专项知识试题
     */
    @RequestMapping("/add")
    public String add(TestQuestions testQuestions) {
        testQuestionService.add(testQuestions);
        return "redirect:/testQuestions/index";
    }

    /**
     * 批量导入试题并查询所有
     */
    @RequestMapping("/addBatch")
    @ResponseBody
    public Map addBatch(@RequestBody List<TestQuestions> ids) {
        List<TestQuestions> all = testQuestionService.addBatch(ids);

        Map ajaxResult = new HashMap();
        ajaxResult.put("success", true);
        ajaxResult.put("all", all);
        return ajaxResult;
    }

    /**
     * 树形
     */
    @RequestMapping("/queryParents")
    public List<TestQuestions> queryParents() {
        return testQuestionService.queryParents();
    }


}
