package com.lawschool.service;


import com.lawschool.beans.TestQuestions;

import java.util.List;
import java.util.Map;

public interface TestQuestionService {
    /**
     * 查询所有的专项知识试题
     */
    public List<TestQuestions> findAll();
    /**
     * 查询专项知识试题
     */
    public TestQuestions findById(String id);
    /**
     * 编辑试题
     */
    public void modify(TestQuestions testQuestions);
    /**
     * 启用禁用
     */
    public void modifyStatus(String id,String disableStatus);

    /**
     * 删除专项知识试题
     */
    public void deleteById(String id);
    /**
     * 新增专项知识试题
     */
    public void add(TestQuestions testQuestions);


}
