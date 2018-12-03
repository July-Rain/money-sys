package com.lawschool.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lawschool.beans.TestQuestions;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
/**
 *
 * @Descriptin
 * @author      张奇
 * @version     v1.0
 * @Time        2018/11/29
 *
 */

@Repository
public interface TestQuestionsMapper extends BaseMapper<TestQuestions> {
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

    List<TestQuestions> listMyCollection(Map<String, Object> param);
}