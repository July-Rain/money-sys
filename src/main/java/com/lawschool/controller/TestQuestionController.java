package com.lawschool.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.base.Page;
import com.lawschool.beans.TestQuestions;
import com.lawschool.constants.StatusConstant;
import com.lawschool.service.AnswerService;
import com.lawschool.service.TestQuestionService;
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
public class TestQuestionController extends AbstractController {

    @Autowired
    private TestQuestionService testQuestionService;

    @Autowired
    AnswerService answerService;

    /**
     * 查询所有的专项知识试题（模糊查询）
     */

    @RequestMapping("/index")
    public Result index(@RequestParam Map<String, Object> params) {

        String typeId = (String) params.get("typeId");
        String questionDifficulty = (String) params.get("questionDifficulty");
        String questionType = (String) params.get("questionType");
        String disableStatus = (String) params.get("disableStatus");

        Page<TestQuestions> page = testQuestionService.findPage(new Page<TestQuestions>(params),
                new EntityWrapper<TestQuestions>()
                        .like("TYPEID", typeId)
                        .like("QUESTIONDIFFICULTY", questionDifficulty)
                        .like("QUESTIONTYPE", questionType)
                        .like("DISABLESTATUS", disableStatus));
        return Result.ok().put("page", page);
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
    public Result add(TestQuestions testQuestions) {

        testQuestionService.add(testQuestions);

        return Result.ok();
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
