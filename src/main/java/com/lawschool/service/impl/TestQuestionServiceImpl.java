package com.lawschool.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.beans.Dict;
import com.lawschool.beans.TestQuestions;
import com.lawschool.dao.DictDao;
import com.lawschool.dao.TestQuestionsMapper;
import com.lawschool.service.TestQuestionService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.UtilValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class TestQuestionServiceImpl implements TestQuestionService {

    @Autowired
    TestQuestionsMapper testQuestionsMapper;

    @Autowired
    DictDao dictDao;

    //我的收藏-重点试题（我收藏的题目）-zjw
    @Override
    public PageUtils listMyCollection(Map<String, Object> param) {
        int pageNo=1;
        long pageSize=10l;
        if(UtilValidate.isNotEmpty(param.get("pageNo"))){
            pageNo=Integer.parseInt((String) param.get("pageNo"));
        }
        if(UtilValidate.isNotEmpty(param.get("pageSize"))){
            pageSize=Long.parseLong((String) param.get("pageSize"));
        }

        //总个数
        int count=testQuestionsMapper.cntMyCollection(param);

        param.put("startPage",(pageNo-1)*pageSize);
        param.put("pageSize",pageNo*pageSize);

        List<TestQuestions> testQuestions = testQuestionsMapper.listMyCollection(param);

        PageUtils page=new PageUtils(testQuestions,count,pageSize, pageNo);

        return page;
    }

    //我的收藏-我的错题（获取我的所有的错题）-zjw
    @Override
    public PageUtils listMyErrorQuestion(Map<String, Object> param) {
        int pageNo=1;
        long pageSize=10l;
        if(UtilValidate.isNotEmpty(param.get("pageNo"))){
            pageNo=Integer.parseInt((String) param.get("pageNo"));
        }
        if(UtilValidate.isNotEmpty(param.get("pageSize"))){
            pageSize=Long.parseLong((String) param.get("pageSize"));
        }

        //总个数
        int count=testQuestionsMapper.cntMyError(param);

        param.put("startPage",(pageNo-1)*pageSize);
        param.put("pageSize",pageNo*pageSize);
        List<TestQuestions> testQuestions = testQuestionsMapper.listMyError(param);

        PageUtils page=new PageUtils(testQuestions,count,pageSize, pageNo);

        return page;
    }

    //详情-zjw
    @Override
    public TestQuestions getTestQuestions(TestQuestions testQuestions) {
        return testQuestionsMapper.selectById(testQuestions.getId());
    }

}
