package com.lawschool.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.beans.Dict;
import com.lawschool.beans.TestQuestions;
import com.lawschool.constants.Constant;
import com.lawschool.constants.StatusConstant;
import com.lawschool.dao.DictDao;
import com.lawschool.dao.TestQuestionsMapper;
import com.lawschool.service.TestQuestionService;
import com.lawschool.util.ParameterUtil;

import com.lawschool.util.PageUtils;
import com.lawschool.util.UtilValidate;
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
    public Page<TestQuestions> findPage(TestQuestions testQuestions, String pageNo) {
     PageHelper.startPage(pageNo, Constant.PAGE_SIZE);
        List<TestQuestions> testQuestionsList = testQuestionsMapper.selectByFuzzy(testQuestions);
//        Page<TestQuestions> info = new PageInfo<>(userList);
        return ;
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
     *
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
    DictDao dictDao;

    //我的收藏-重点试题（我收藏的题目）-zjw
    @Override
    public PageUtils listMyCollection(Map<String, Object> param) {
        int pageNo=1;
        long pageSize=10l;
        if(UtilValidate.isNotEmpty(param.get("pageNo"))){
            pageNo=Integer.parseInt((String) param.get("pageNo"));
        }
        if(UtilValidate.isNotEmpty(param.get("pageSize"))){
            pageSize=Long.parseLong((String) param.get("pageSize"));
        }

        //总个数
        int count=testQuestionsMapper.cntMyCollection(param);

        param.put("startPage",(pageNo-1)*pageSize);
        param.put("pageSize",pageNo*pageSize);

        List<TestQuestions> testQuestions = testQuestionsMapper.listMyCollection(param);

        PageUtils page=new PageUtils(testQuestions,count,pageSize, pageNo);

        return page;
    }

    //我的收藏-我的错题（获取我的所有的错题）-zjw
    @Override
    public PageUtils listMyErrorQuestion(Map<String, Object> param) {
        int pageNo=1;
        long pageSize=10l;
        if(UtilValidate.isNotEmpty(param.get("pageNo"))){
            pageNo=Integer.parseInt((String) param.get("pageNo"));
        }
        if(UtilValidate.isNotEmpty(param.get("pageSize"))){
            pageSize=Long.parseLong((String) param.get("pageSize"));
        }

        //总个数
        int count=testQuestionsMapper.cntMyError(param);

        param.put("startPage",(pageNo-1)*pageSize);
        param.put("pageSize",pageNo*pageSize);
        List<TestQuestions> testQuestions = testQuestionsMapper.listMyError(param);

        PageUtils page=new PageUtils(testQuestions,count,pageSize, pageNo);

        return page;
    }

    //详情-zjw
    @Override
    public TestQuestions getTestQuestions(TestQuestions testQuestions) {
        return testQuestionsMapper.selectById(testQuestions.getId());
    }


}
