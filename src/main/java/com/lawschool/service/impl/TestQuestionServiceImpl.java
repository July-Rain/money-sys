package com.lawschool.service.impl;

import com.lawschool.beans.TestQuestions;
import com.lawschool.dao.TestQuestionsMapper;
import com.lawschool.service.TestQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class TestQuestionServiceImpl implements TestQuestionService {

    @Autowired
    TestQuestionsMapper testQuestionsMapper;

    @Override
    public List<TestQuestions> listMyCollection(Map<String, Object> param) {
        return testQuestionsMapper.listMyCollection(param);
    }

    @Override
    public TestQuestions getTestQuestions(TestQuestions testQuestions) {
        return null;
    }

}
