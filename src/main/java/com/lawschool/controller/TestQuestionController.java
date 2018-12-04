package com.lawschool.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.lawschool.beans.TestQuestions;
import com.lawschool.constants.Constant;
import com.lawschool.constants.StatusConstant;
import com.lawschool.service.TestQuestionService;
import com.lawschool.util.ResponseResult;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;
import org.omg.Dynamic.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
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
    /**
     * 查询所有的专项知识试题（模糊查询）
     */
    @ApiOperation(value = "分页查询", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping("/index")
    public String index(TestQuestions testQuestions, @RequestParam(value = "pn",defaultValue = "1")String pageNo, ModelMap map )
    {

        Page<TestQuestions> info = new Page<TestQuestions>(Integer.parseInt(pageNo), Constant.PAGE_SIZE);
        Parameter orderparameter = new Parameter(getService(), "getOrderlist").setParam(info,new String[]{});
//        PageInfo<TestQuestions> page = testQuestionService.findPage(testQuestions,pageNo);
        map.addAttribute("data",page);
        return "index";
    }
    /**
     * 查询专项知识试题
     */
    @RequestMapping("/findById")
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
    /**
     * 禁言启用
     */
    @RequestMapping("/modifyStatus")
    @ResponseBody
    public ResponseResult modifyStatus(String id,String releaseStatus) {

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




    //我收藏的题目
    @RequestMapping("/mycollection")
    public Result listMyCollection(Map<String,Object> params){
        //params.put("userId",getUser().getUserId());
        params.put("userId",1);
        PageUtils pageUtils = testQuestionService.listMyCollection(params);
        return Result.ok().put("result",pageUtils);
    }

    //我做错的题目
    @RequestMapping("/myerror")
    public Result listMyErrorQuestion(Map<String,Object> params){
        //params.put("userId",getUser().getUserId());
        params.put("userId",1);
        PageUtils pageUtils = testQuestionService.listMyErrorQuestion(params);
        return Result.ok().put("result",pageUtils);
    }

    //获取收藏详情
    @RequestMapping("/getTestQuestion")
    public Result getStuMedia(TestQuestions testQuestions){
        TestQuestions info = testQuestionService.getTestQuestions(testQuestions);
        return Result.ok().put("info",info);
    }
}
