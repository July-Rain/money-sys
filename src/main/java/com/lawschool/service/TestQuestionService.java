package com.lawschool.service;


import com.baomidou.mybatisplus.service.IService;
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
public interface TestQuestionService extends IService<TestQuestions> {
    /**
     * 查询所有的专项知识试题（模糊查询）
     */
//    Page<TestQuestions> findPage(Map<String, Object> params);
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
