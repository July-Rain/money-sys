package com.lawschool.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lawschool.beans.TestQuestions;

import java.util.List;
import java.util.Map;


public interface TestQuestionsMapper extends BaseMapper<TestQuestions> {
    //int insert(TestQuestions record);

    int insertSelective(TestQuestions record);

    //我的收藏-重点试题（我收藏的题目）z
    List<TestQuestions> listMyCollection(Map<String, Object> param);

    int cntMyCollection(Map<String, Object> param);

    //我收藏的题目-组卷 z
    List<TestQuestions> randomQuestColl(Map<String, Object> param);

    //我的收藏-我的错题（获取我的所有的错题）z
    List<TestQuestions> listMyError(Map<String, Object> param);

    //我的错题-组卷 z
    List<TestQuestions> randomErrorColl(Map<String, Object> param);


    int cntMyError(Map<String, Object> param);



}