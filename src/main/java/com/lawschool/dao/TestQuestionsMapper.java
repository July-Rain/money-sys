package com.lawschool.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lawschool.beans.TestQuestions;

import java.util.List;
import java.util.Map;

public interface TestQuestionsMapper extends BaseMapper<TestQuestions> {
    //int insert(TestQuestions record);
    /**
     * 查询所有的专项知识试题
     */
    public List<TestQuestions> selectAll();
    /**
     * 查询专项知识试题
     */
    public TestQuestions selectById(String id);
    /**
     * 编辑试题
     */
    public void update(TestQuestions testQuestions);
    /**
     * 删除专项知识试题
     */
    public void deleteById(String id);


    int insertSelective(TestQuestions record);

    List<TestQuestions> listMyCollection(Map<String, Object> param);
}