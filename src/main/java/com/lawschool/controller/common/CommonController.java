package com.lawschool.controller.common;

import com.lawschool.base.AbstractController;
import com.lawschool.form.CommonForm;
import com.lawschool.form.TopicForm;
import com.lawschool.service.TestQuestionService;
import com.lawschool.service.system.DictionService;
import com.lawschool.service.system.TopicTypeService;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

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
     * 获取主题对应的题目数量，不包含主观题，且去除无题目的主题
     * @return
     */
    @RequestMapping(value = "/topicNums", method = RequestMethod.GET)
    public Result getTopicNum(){
        // 定义返回结果集
        List<TopicForm> result = new ArrayList<TopicForm>();

        // 查询主题、难度对应的题量，除主观题
        List<TopicForm> list = testQuestionService.countTopicNum();

        // 合并处理结果
        for(int i=0; i<list.size(); i++){
            TopicForm current = list.get(i);
            String id = current.getKey();// 主键
            String diff = current.getOpinion();


            List<String> tempList = new ArrayList<>(Arrays.asList("0", "0", "0"));
            if("10001".equals(diff)){
                // 初级
                tempList.set(0, list.get(i).getValue());

            } else if("10002".equals(diff)){
                // 中级
                tempList.set(1, list.get(i).getValue());

            } else {
                // 高级
                tempList.set(2, list.get(i).getValue());
            }

            for(int j=i+1; j<list.size(); j++){

                if(list.get(j).getKey().equals(current.getKey())){
                    String diffNew = list.get(j).getOpinion();
                    if("10001".equals(diffNew)){
                        tempList.set(0, list.get(j).getValue());// 初级

                    } else if("10002".equals(diffNew)){
                        tempList.set(1, list.get(j).getValue());// 中级

                    } else {
                        tempList.set(2, list.get(j).getValue());// 高级
                    }
                } else {
                    i = j-1;
                    break;
                }
            }
            current.setValue(current.getName());
            current.setNums(tempList);
            result.add(current);
        }

        return Result.ok().put("topicList", result);
    }

}
