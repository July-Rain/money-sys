package com.lawschool.service.impl;

import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.lawschool.beans.TestQuestions;
import com.lawschool.dao.TestQuestionsMapper;
import com.lawschool.service.TestQuestionService;
import org.aspectj.apache.bcel.classfile.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *
 * @Descriptin
 * @author      张奇
 * @version     v1.0
 * @Time        2018/11/28
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
public class TestQuestionServiceImpl implements TestQuestionService {

    @Autowired
    private TestQuestionsMapper testQuestionsMapper;
    /**
     * 查询所有的专项知识试题（模糊查询）
     */

    @Override
    @Transactional(readOnly = true)
    public PageInfo<TestQuestions> findPage(TestQuestions testQuestions, String pageNo) {
        PageHelper.startPage(pageNo,Constant.PAGE_SIZE);
        List<TestQuestions> testQuestionsList = testQuestionsMapper.selectByFuzzy(testQuestions);
        PageInfo<TestQuestions> info = new PageInfo<>(userList);
        return info;
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
    @Override
    public void modifyStatus(String id, String disableStatus) {
        TestQuestions t=new TestQuestions();
        t.setId(id);
        t.setDisableStatus(new BigDecimal(disableStatus));
        testQuestionsMapper.updateStatus(t);

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
     *
     */
    @Override
    public List<TestQuestions> addBatch(List<TestQuestions> testQuestions) {
//        testQuestionsMapper.insertBatch(testQuestions);
        if(testQuestions !=null && !testQuestions.isEmpty()){
            for (TestQuestions testQuestion : testQuestions) {
                testQuestionsMapper.insertTestQuestions(testQuestion);
            }
            return testQuestionsMapper.selectAllTestQuestions();
        }
    }
    /**
     * 树形
     */
    @Override
    @Transactional(readOnly = true)
    public List<TestQuestions> queryParents() {
        return testQuestionsMapper.selectParent();
    }







    @Override
    public List<TestQuestions> listMyCollection(Map<String, Object> param) {
        return testQuestionsMapper.listMyCollection(param);
    }

    @Override
    public TestQuestions getTestQuestions(TestQuestions testQuestions) {
        return null;
    }


}
