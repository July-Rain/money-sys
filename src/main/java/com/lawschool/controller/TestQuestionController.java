package com.lawschool.controller;


import com.lawschool.beans.StuMedia;
import com.lawschool.beans.TestQuestions;
import com.lawschool.service.TestQuestionService;
import com.lawschool.util.PageUtils;
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
    private TestQuestionService testQuestionService;
    /**
     * 查询所有的专项知识试题（模糊查询）
     */

    @RequestMapping("/index")
    public String index(TestQuestions testQuestions, ModelMap map )
    {

        Page<TestQuestions> info = new Page<TestQuestions>(Integer.parseInt(pageNo), Constant.PAGE_SIZE);
        Parameter orderparameter = new Parameter(getService(), "getOrderlist").setParam(info,new String[]{});
        Page<TestQuestions> page = testQuestionService.findPage(testQuestions,pageNo);
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
