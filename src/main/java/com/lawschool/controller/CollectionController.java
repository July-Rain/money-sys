package com.lawschool.controller;

import com.lawschool.annotation.SysLog;
import com.lawschool.base.AbstractController;
import com.lawschool.beans.Answer;
import com.lawschool.beans.Collection;
import com.lawschool.beans.TestQuestions;
import com.lawschool.beans.User;
import com.lawschool.service.AnswerService;
import com.lawschool.service.CollectionService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;
import com.lawschool.util.UtilValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static com.lawschool.util.Constant.SUCCESS;


@RestController
@RequestMapping("/coll")

public class CollectionController extends AbstractController {


    @Autowired
    CollectionService collectionService;

    @Autowired
    AnswerService answerService;

    /**
     * @Author zjw
     * @Description 删除收藏
     * @Date 9:40 2018-12-6
     * @Param [collection]
     * @return com.lawschool.util.Result
    **/
    @SysLog("删除收藏")
    @RequestMapping("/delColl")
    public Result delCollection(@RequestBody Collection collection){
        int rest = collectionService.delCollection(collection, getUser());
        return rest==SUCCESS?Result.ok():Result.error("取消收藏失败");
    }

    /**
     * @Author zjw
     * @Description 添加收藏
     * @Date 9:40 2018-12-6
     * @Param [collection]
     * @return com.lawschool.util.Result
    **/
    @SysLog("添加收藏")
    @RequestMapping("/addColl")
    public Result addCollection(@RequestBody Collection collection){
        int rest = collectionService.addCollection(collection,getUser());
        return rest== SUCCESS ?Result.ok():Result.error("收藏失败");
    }



    /**
     * @Author zjw
     * @Description 我收藏的题目
     * @Date 9:41 2018-12-6
     * @Param [params]
     * @return com.lawschool.util.Result
    **/
    @RequestMapping("/testQuestion")
    public Result listMyCollection(@RequestParam Map<String,Object> params){
        params.put("userId",getUser().getId());
        PageUtils pageUtils = collectionService.listMyCollection(params);
        return Result.ok().put("result",pageUtils);
    }

    /**
     * @Author zjw
     * @Description 一键组卷（收藏的题目）
     * @Date 9:41 2018-12-6
     * @Param [params]
     * @return com.lawschool.util.Result
    **/
    @SysLog("一键组卷（收藏的题目）")
    @RequestMapping("/randomQuestColl")
    public Result randomQuestColl(@RequestParam Map<String,Object> params){

        if(UtilValidate.isEmpty(params.get("pname"))){
            return Result.error("试卷名称不能为空");
        }else if (UtilValidate.isEmpty(params.get("num"))) {
            return Result.error("题目数量不能为空");
        }else {
            Result result = collectionService.randomQuestColl(params, getUser());

            return result;
        }
    }


    /**
     * @Author zjw
     * @Description 获取我做错的题目
     * @Date 9:41 2018-12-6
     * @Param [params]
     * @return com.lawschool.util.Result
    **/
    @RequestMapping("/errorTestQuestion")
    public Result listMyErrorQuestion(@RequestParam Map<String,Object> params){
        params.put("userId",getUser().getId());
        PageUtils pageUtils = collectionService.listMyErrorQuestion(params);
        return Result.ok().put("result",pageUtils);
    }


    /**
     * @Author zjw
     * @Description 组卷（错题）
     * @Date 9:42 2018-12-6
     * @Param [params]
     * @return com.lawschool.util.Result
    **/
    @RequestMapping("/randomErrorColl")
    public Result randomErrorColl(@RequestParam Map<String,Object> params){
        Result result=collectionService.randomErrorColl(params,getUser());
        return result;
    }

    //获取试题详情z
    @RequestMapping("/getTestQuestion")
    public Result getTestQuestion(@RequestBody TestQuestions testQuestions){
        TestQuestions info = collectionService.getTestQuestions(testQuestions);
        List<Answer> answerByQid = answerService.getAnswerByQid(testQuestions.getId());
        return Result.ok().put("qustion",info).put("answer",answerByQid);
    }

}
