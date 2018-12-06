package com.lawschool.dao;

import com.lawschool.beans.TestQuestions;
import com.lawschool.base.AbstractDao;

import java.util.List;
import java.util.Map;

public interface TestQuestionsDao extends AbstractDao<TestQuestions> {
    //int insert(TestQuestions record);
    /**
     * 查询所有的专项知识试题（模糊查询）
     */
    public List<TestQuestions> selectByFuzzy(TestQuestions testQuestions);
    /**
     * 查询专项知识试题
     */
    public TestQuestions selectById(String id);
    /**
     * 编辑试题
     */
    public void update(TestQuestions testQuestions);
    /**
     * 启用禁用
     */
    public void updateStatus(TestQuestions testQuestions);

    /**
     * 删除专项知识试题
     */
    public void deleteById(String id);


    /**
     * 批量导入试题
     */
    public void insertTestQuestions(TestQuestions testQuestions);
    /**
     * 批量导入后查所有
     */
    public List<TestQuestions> selectAllTestQuestions();
    /**
     * 树形
     */
    public List<TestQuestions> selectParent();




    int insertSelective(TestQuestions record);

    //我的收藏-重点试题（我收藏的题目）-zjw
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