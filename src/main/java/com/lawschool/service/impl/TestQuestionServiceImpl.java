package com.lawschool.service.impl;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.TestQuestions;
import com.lawschool.constants.StatusConstant;
import com.lawschool.dao.TestQuestionsDao;
import com.lawschool.service.TestQuestionService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;


@Service
public class TestQuestionServiceImpl extends AbstractServiceImpl<TestQuestionsDao,TestQuestions> implements TestQuestionService {

    /**
     * 禁用启用
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
     * 批量导入试题并且查询出所有
     */
    public void importTestQuestions(List<TestQuestions> testQuestionsList) {
        if (!CollectionUtils.isEmpty(testQuestionsList)) {
            for (TestQuestions testQuestion : testQuestionsList) {
                dao.insert(testQuestion);
            }
        }
    }
}
