package com.lawschool.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.beans.*;
import com.lawschool.dao.*;
import com.lawschool.service.TestQuestionService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.UtilValidate;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class TestQuestionServiceImpl implements TestQuestionService {

    @Autowired
    TestQuestionsMapper testQuestionsMapper;

    @Autowired
    AnswerMapper answerMapper;

    @Autowired
    PracticeRelevanceMapper practiceRelevanceMapper;

    @Autowired
    PracticePaperMapper practicePaperMapper;

    @Autowired
    DictDao dictDao;

    //我的收藏-重点试题（我收藏的题目）z
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
        param.put("endPage",pageNo*pageSize);

        List<TestQuestions> testQuestions = testQuestionsMapper.listMyCollection(param);

        PageUtils page=new PageUtils(testQuestions,count,pageSize, pageNo);

        return page;
    }


    //重点试题-组卷z
    @Override
    public  Map<TestQuestions,List<Answer>> randomQuestColl(Map<String, Object> param) {
        //1,生成题目
        Map<TestQuestions,List<Answer>> map=new HashedMap();
        if(UtilValidate.isEmpty(param.get("num"))){
            param.put("num",10);//获取组成10题
        }
        List<TestQuestions> testQuestions = testQuestionsMapper.randomQuestColl(param);//仅仅只有id,提高效率

        //2。生成练习
        String uuid = IdWorker.get32UUID();
        PracticePaper practicePaper=new PracticePaper();
        practicePaper.setId(uuid);
        practicePaper.setOpttime(new Date());


        testQuestions.stream().forEach(e->{
            String id=e.getId();
            TestQuestions question = testQuestionsMapper.selectById(id);//获取题目
            List<Answer> answers = answerMapper.selectList(new EntityWrapper<Answer>().eq("QUESTION_ID", id));//答案
            map.put(question,answers);
        });


        return map;
    }

    //我的收藏-我的错题（获取我的所有的错题）z
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
        param.put("endPage",pageNo*pageSize);
        List<TestQuestions> testQuestions = testQuestionsMapper.listMyError(param);

        PageUtils page=new PageUtils(testQuestions,count,pageSize, pageNo);

        return page;
    }

    //重点试题-组卷z
    @Override
    public  Map<TestQuestions,List<Answer>> randomErrorColl(Map<String, Object> param) {
        Map<TestQuestions,List<Answer>> map=new HashedMap();
        if(UtilValidate.isEmpty(param.get("num"))){
            param.put("num",10);//获取组成10题
        }
        List<TestQuestions> testQuestions = testQuestionsMapper.randomErrorColl(param);//仅仅只有id,提高效率
        testQuestions.stream().forEach(e->{
            String id=e.getId();
            TestQuestions question = testQuestionsMapper.selectById(id);//获取题目
            List<Answer> answers = answerMapper.selectList(new EntityWrapper<Answer>().eq("QUESTION_ID", id));//答案
            map.put(question,answers);
        });
        return map;
    }

    //详情z
    @Override
    public TestQuestions getTestQuestions(TestQuestions testQuestions) {
        TestQuestions testQuestions1 = testQuestionsMapper.selectById(testQuestions.getId());
        return testQuestions1;
    }

}
