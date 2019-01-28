package com.lawschool.service;


import com.lawschool.base.AbstractService;
import com.lawschool.beans.TestQuestions;
import com.lawschool.beans.practicecenter.TaskExerciseConfigureEntity;
import com.lawschool.form.AnswerForm;
import com.lawschool.form.CommonForm;
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

    List<CommonForm> selectByTopicAndNum(String topic, Integer num);

    /**
     * 根据题目的多个id查询题目（包含题目选项）
     * @param ids
     * @return
     */
    List<QuestForm> getQuestions(List<String> ids);

    /**
     * 根据专项知识ID和题目类型查询指定数量的题目
     * @param param
     * @return
     */
    List<String> findByNum(Map<String, Object> param);

    /**
     * 根据题目ID查询题目
     * liuhuan
     */
    QuestForm findTestQuestionById(String id);

    List<QuestForm> handleAnswers(List<QuestForm> questForms, List<AnswerForm> answerList);

    Integer getNumByConditions(Map<String, String> params);

    /**
     * 根据查询条件查询题目IDs(带分页效果)
     * @param params
     * @return
     */
    List<String> selectIdsForPage(Map<String, Object> params);

    /**
     *
     * @return
     */
    Map<String, String> countByThemeId();

    boolean updateAnswerId(String id, String answerId);

    boolean mySave(TestQuestions entity);
}
