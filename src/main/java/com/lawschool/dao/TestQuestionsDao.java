package com.lawschool.dao;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.TestQuestions;
import com.lawschool.beans.practicecenter.TaskExerciseConfigureEntity;
import com.lawschool.form.CommonForm;
import com.lawschool.form.QuestForm;
import org.apache.ibatis.annotations.Param;

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

    List<CommonForm> selectByTopicAndNum(@Param("topicId") String topicId,
                                         @Param("num") Integer num);

    /**
     * 根据专项知识ID和题目类型查询指定数量的题目
     * @param param
     * @return
     */
    List<String> findByNum(Map<String, Object> param);

    /**
     * 通过id查询题目
     * @Author liuhuan
     * @param id
     * @return
     */
    QuestForm findTestQuestionById(String id);

    Integer getNumByConditions(Map<String, String> params);

    /**
     * 根据查询条件查询满足的题目ID（带分页效果）
     * 请勿随便改动
     * @param params
     * @return
     */
    List<String> selectIdsForPage(Map<String, Object> params);

    /**
     * 根据主题统计题目数量
     * @return
     */
    List<CommonForm> countByThemeId();
}