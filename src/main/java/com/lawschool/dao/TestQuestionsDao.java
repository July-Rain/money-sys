package com.lawschool.dao;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.TestQuestions;
import com.lawschool.form.QuestForm;

import java.util.List;
import java.util.Map;

public interface TestQuestionsDao extends AbstractDao<TestQuestions> {

    /**
     * 启用禁用
     */
    void updateStatus(String id, String isEnble);

    //我的收藏-重点试题（我收藏的题目）-zjw
    List<TestQuestions> listMyCollection(Map<String, Object> param);

    //我的收藏-zjw;
    int cntMyCollection(Map<String, Object> param);

    //我收藏的题目-组卷 zjw
    List<TestQuestions> randomQuestColl(Map<String, Object> param);


    TestQuestions getInfoById(TestQuestions testQuestions);



//    //我的收藏-我的错题（获取我的所有的错题）z
//    List<TestQuestions> listMyError(Map<String, Object> param);
//
//    //我的错题-组卷 z
//    List<TestQuestions> randomErrorColl(Map<String, Object> param);
//
//
//    int cntMyError(Map<String, Object> param);






    /**
     * 查询某类型的试题id
     * @param param
     * @return
     */
    List<String> findIdBySpecialKnowledgeId(Map<String, Object> param);

    /**
     * 查询某类型的试题id
     * @param param
     * @return
     */
    List<TestQuestions> findBySpecialKnowledgeId(Map<String, Object> param);

    /**
     * 根据题目的多个id查询题目
     * @param list
     * @return
     */
    List<QuestForm> findByIds(List<String> list);

    /**
     * 根据专项知识ID和题目类型查询指定数量的题目
     * @param param
     * @return
     */
    List<TestQuestions> findByNum(Map<String, Object> param);

}