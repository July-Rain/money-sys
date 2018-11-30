package com.lawschool.service.impl;

import com.lawschool.beans.TestQuestions;
import com.lawschool.dao.TestQuestionsMapper;
import com.lawschool.service.TestQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *
 * @Descriptin
 * @author      张奇
 * @version     v1.0
 * @Time        2018/11/28
 *
 */
@Service
public class TestQuestionServiceImpl implements TestQuestionService {

    @Autowired
    TestQuestionsMapper testQuestionsMapper;
    /**
     * 查询所有的专项知识试题
     */
    @Override
    public List<TestQuestions> findAll() {
        return testQuestionsMapper.selectAll();
    }
    /**
     * 查询专项知识试题
     */
    @Override
    public TestQuestions findById(String id) {
        return testQuestionsMapper.selectById(id);
    }
    /**
     * 编辑试题
     */
    @Override
    public void modify(TestQuestions testQuestions) {
        testQuestionsMapper.update(testQuestions);
    }

    @Override
    public void modifyStatus(String id, String disableStatus) {

    }

    /**
     * 删除专项知识试题
     */
    @Override
    public void deleteById(String id) {
        testQuestionsMapper.deleteById(id);
    }
    /**
     * 新增专项知识试题
     */
    @Override
    public void add(TestQuestions testQuestions) {
        testQuestionsMapper.insert(testQuestions);
    }



}
