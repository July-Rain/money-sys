package com.lawschool.service;


import com.lawschool.base.AbstractService;
import com.lawschool.base.Page;
import com.lawschool.beans.TestQuestions;
import com.lawschool.beans.User;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *
 * @Descriptin  试题service
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
public interface TestQuestionService extends AbstractService<TestQuestions> {
    /**
     * 查询所有的专项知识试题（模糊查询）
     */
    Page<TestQuestions> findPage(Map<String, Object> params);
    /**
     * 查询专项知识试题
     */
    TestQuestions findById(String id);
    /**
     * 编辑试题
     */
    void modify(TestQuestions testQuestions);
    /**
     * 启用禁用
     */
    void modifyStatus(String id,BigDecimal releaseStatus);

    /**
     * 删除专项知识试题
     */
    void deleteById(String id);

    /**
     * 新增专项知识试题
     */
    void add(TestQuestions testQuestions);
    /**
     * 批量导入试题
     */
    List<TestQuestions> addBatch(List<TestQuestions> TestQuestions);
    /**
     * 树形
     */
    List<TestQuestions> queryParents();



}
