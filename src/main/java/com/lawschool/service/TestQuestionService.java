package com.lawschool.service;


import com.lawschool.base.AbstractService;
import com.lawschool.beans.TestQuestions;
import com.lawschool.form.QuestForm;

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
     * 启用禁用
     * @param id
     * @param isEnble
     */
    void updateStatus(String id, String isEnble);

    /**
     * 批量导入试题
     * @param testQuestionsList
     */
    void importTestQuestions(List<TestQuestions> testQuestionsList);

    /**
     * 查询某类型的试题id
     * @param param
     */
    List<String> findIdBySpecialKnowledgeId(Map<String, Object> param);

    /**
     * 查询某类型的试题
     * @param param
     */
    List<TestQuestions> findBySpecialKnowledgeId(Map<String, Object> param);

    /**
     * 根据题目的多个id查询题目
     * @param list
     * @return
     */
    List<QuestForm> findByIds(List<String> list);



    TestQuestions findByEntity(TestQuestions t);

}
