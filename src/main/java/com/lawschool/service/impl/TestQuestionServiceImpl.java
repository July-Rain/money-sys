package com.lawschool.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.beans.*;
import com.lawschool.constants.StatusConstant;
import com.lawschool.dao.*;
import com.lawschool.service.TestQuestionService;
import com.lawschool.util.GetUUID;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;
import com.lawschool.util.UtilValidate;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;


@Service
public class TestQuestionServiceImpl extends ServiceImpl<TestQuestionsMapper,TestQuestions> implements TestQuestionService {

    @Autowired
    TestQuestionsMapper testQuestionsMapper;

    @Autowired
    AnswerMapper answerMapper;

    @Autowired
    PracticeRelevanceMapper practiceRelevanceMapper;

    @Autowired
    PracticePaperMapper practicePaperMapper;

    @Autowired
    DictDao dictDao;


    /**
     * 查询所有的专项知识试题（模糊查询）
     */

    @Transactional(readOnly = true)
    public Page<TestQuestions> findPage(Map<String, Object> params) {
        return null;
    }

    /**
     * 查询专项知识试题
     */
    @Override
    @Transactional(readOnly = true)
    public TestQuestions findById(String id) {

        return testQuestionsMapper.selectById(id);
    }

    /**
     * 编辑试题
     */
    @Override
    public void modify(TestQuestions testQuestions) {

        testQuestionsMapper.update(testQuestions);
    }

    /**
     * 禁用启用
     */
    public void modifyStatus(String id, BigDecimal typeStatus) {

        String status = typeStatus.toString();
        if (status == StatusConstant.PRODUCT_TYPE_STATUS_DISABLE) {
            status = StatusConstant.PRODUCT_TYPE_STATUS_ENABLE;
        } else {
            status = StatusConstant.PRODUCT_TYPE_STATUS_DISABLE;
        }
        TestQuestions tq = new TestQuestions();
        tq.setId(id);
        tq.setDisableStatus(new BigDecimal(status));
        testQuestionsMapper.updateStatus(tq);
    }

    /**
     * 删除专项知识试题
     */
    @Override
    public void deleteById(String id) {

        testQuestionsMapper.deleteById(id);
    }

    /**
     * 新增专项知识试题
     */
    @Override
    public void add(TestQuestions testQuestions) {

        testQuestionsMapper.insert(testQuestions);
    }

    /**
     * 批量导入试题并且查询出所有
     */
    @Override
    public List<TestQuestions> addBatch(List<TestQuestions> testQuestions) {
//        testQuestionsMapper.insertBatch(testQuestions);
        if (testQuestions != null && !testQuestions.isEmpty()) {
            for (TestQuestions testQuestion : testQuestions) {
                testQuestionsMapper.insertTestQuestions(testQuestion);
            }
            return testQuestionsMapper.selectAllTestQuestions();
        }
        return null;
    }

    /**
     * 树形
     */
    @Override
    @Transactional(readOnly = true)
    public List<TestQuestions> queryParents() {
        return testQuestionsMapper.selectParent();
    }


    @Autowired
    OrgDao orgDao;




}
