package com.lawschool.service;


import com.lawschool.beans.TestQuestions;

import java.util.List;
import java.util.Map;

/**
 *
 * @Descriptin  试题service
 * @author      zjw
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
public interface TestQuestionService {


    /**
     * 我的收藏-重点试题
     * @param param
     * @return
     */
    List<TestQuestions>  listMyCollection(Map<String,Object> param);

    /**
     * 详情
     * @param testQuestions
     * @return
     */
    TestQuestions getTestQuestions(TestQuestions testQuestions);
}
