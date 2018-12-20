package com.lawschool.service.impl;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.TestQuestions;
import com.lawschool.constants.StatusConstant;
import com.lawschool.dao.TestQuestionsDao;
import com.lawschool.form.QuestForm;
import com.lawschool.service.TestQuestionService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;


@Service
public class TestQuestionServiceImpl extends AbstractServiceImpl<TestQuestionsDao,TestQuestions> implements TestQuestionService {

    /**
     * 启用禁用
     * @param id
     * @param isEnble
     */
    public void updateStatus(String id, String isEnble) {

        if (isEnble == StatusConstant.PRODUCT_TYPE_STATUS_DISABLE) {
            isEnble = StatusConstant.PRODUCT_TYPE_STATUS_ENABLE;
        } else {
            isEnble = StatusConstant.PRODUCT_TYPE_STATUS_DISABLE;
        }
        dao.updateStatus(id, isEnble);
    }

    /**
     * 批量导入试题
     * @param testQuestionsList
     */
    public void importTestQuestions(List<TestQuestions> testQuestionsList) {
        if (!CollectionUtils.isEmpty(testQuestionsList)) {
            for (TestQuestions testQuestion : testQuestionsList) {
                dao.insert(testQuestion);
            }
        }
    }

    /**
     * 查询某类型的试题id
     * @param param
     */
    public List<String> findIdBySpecialKnowledgeId(Map<String, Object> param) {
        return dao.findIdBySpecialKnowledgeId(param);
    }

    /**
     * 查询某类型的试题id
     * @param param
     */
    public List<TestQuestions> findBySpecialKnowledgeId(Map<String, Object> param) {
        return dao.findBySpecialKnowledgeId(param);
    }

    /**
     * 根据题目的多个id查询题目
     * @param list
     * @return
     */
    public List<QuestForm> findByIds(List<String> list){
        return dao.findByIds(list);
    }


    @Override
    public TestQuestions findByEntity(TestQuestions entity) {
        return dao.findByEntity(entity);
    }
}
