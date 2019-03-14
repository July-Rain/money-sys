package com.lawschool.controller.common;

import com.lawschool.base.AbstractController;
import com.lawschool.form.CommonForm;
import com.lawschool.service.TestQuestionService;
import com.lawschool.service.system.DictionService;
import com.lawschool.service.system.TopicTypeService;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Moon
 * @Date: 2019/3/7 17:16
 * @Description: 一些公共请求方法
 */
@RestController
@RequestMapping("/common")
public class CommonController extends AbstractController {

    @Autowired private DictionService dictionService;

    @Autowired private TestQuestionService testQuestionService;
    /**
     * 根据父类code获取字典信息，支持一次请求多个
     * @param codes
     * @return 返回map，key为父类code，value为字典list
     */
    @RequestMapping(value = "/dicts", method = RequestMethod.GET)
    public Result getDictByParentCode(@RequestParam String codes){
        Map<String, List<CommonForm>> results = new HashMap<String, List<CommonForm>>();

        results = dictionService.getDictByParentCode(Arrays.asList(codes.split(",")));

        return Result.ok().put("results", results);
    }

    /**
     * 获取主题对应的题目数量，不包含主观题
     * @return
     */
    @RequestMapping(value = "/topicNums", method = RequestMethod.GET)
    public Result getTopicNum(){
        // 定义返回结果集
        Map<String, Integer[]> result = new HashMap<String, Integer[]>();

        // 查询主题、难度对应的题量，除主观题
        List<CommonForm> list = testQuestionService.countTopicNum();

        for(CommonForm form : list){
            String key = form.getKey();// 主题ID
            String diff = form.getOpinion();// 难度CODE
            Integer num = Integer.parseInt(form.getValue());

            Integer[] tempArr = new Integer[]{0, 0, 0};

            if(result.get(key) != null){
                tempArr = result.get(key);
            }

            if("10001".equals(diff)){
                tempArr[0] = num;

            } else if("10002".equals(diff)){
                tempArr[1] = num;

            } else {
                tempArr[2] = num;
            }

            result.put(key, tempArr);
        }

        return Result.ok().put("numArr", result);
    }

}
