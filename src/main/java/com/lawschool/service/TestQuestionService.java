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
