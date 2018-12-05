package com.lawschool.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.lawschool.beans.Answer;
import com.lawschool.beans.TestQuestions;
import com.lawschool.util.PageUtils;

import java.math.BigDecimal;
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
     * 查询所有的专项知识试题（模糊查询）
     */
    public Page<TestQuestions> findPage(TestQuestions testQuestions, String pageNo);
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
    public void modifyStatus(String id,BigDecimal releaseStatus);

    /**
     * 删除专项知识试题
     */
    public void deleteById(String id);

    /**
     * 新增专项知识试题
     */
    public void add(TestQuestions testQuestions);
    /**
     * 批量导入试题
     */
    public List<TestQuestions> addBatch(List<TestQuestions> TestQuestions);
    /**
     * 树形
     */
    public List<TestQuestions> queryParents();


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
