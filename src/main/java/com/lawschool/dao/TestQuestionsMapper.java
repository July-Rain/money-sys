package com.lawschool.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lawschool.beans.TestQuestions;

import java.util.List;
import java.util.Map;

public interface TestQuestionsMapper extends BaseMapper<TestQuestions> {
    //int insert(TestQuestions record);

    int insertSelective(TestQuestions record);

    List<TestQuestions> listMyCollection(Map<String, Object> param);
}