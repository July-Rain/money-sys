package com.lawschool.service;


import com.lawschool.beans.Answer;
import com.lawschool.beans.TestQuestions;
import com.lawschool.util.PageUtils;

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
     * 我的收藏-重点试题（我收藏的题目）
     * @author      zjw
     * @param param
     * @return
     */
    PageUtils  listMyCollection(Map<String,Object> param);

    /**
     * 重点试题-组卷
     * @author      zjw
     * @param param
     * @return
     */
    Map<TestQuestions,List<Answer>>  randomQuestColl(Map<String,Object> param);

    /**
     * 我的收藏-我的错题（获取所有的错题）
     * @author      zjw
     * @param param
     * @return
     */
    PageUtils listMyErrorQuestion(Map<String,Object> param);

    /**
     * 错题-组卷
     * @author      zjw
     * @param param
     * @return
     */
    Map<TestQuestions,List<Answer>>  randomErrorColl(Map<String,Object> param);

    /**
     * 详情
     * @author      zjw
     * @param testQuestions
     * @return
     */
    TestQuestions getTestQuestions(TestQuestions testQuestions);
}
