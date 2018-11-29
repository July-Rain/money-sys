package com.lawschool.service;


import com.lawschool.beans.TestQuestions;

import java.util.List;
import java.util.Map;

public interface TestQuestionService {

    List<TestQuestions>  listMyCollection(Map<String,Object> param);

    TestQuestions getTestQuestions(TestQuestions testQuestions);
}
