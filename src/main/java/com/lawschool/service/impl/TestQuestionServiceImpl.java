package com.lawschool.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.beans.Dict;
import com.lawschool.beans.TestQuestions;
import com.lawschool.dao.DictDao;
import com.lawschool.dao.TestQuestionsMapper;
import com.lawschool.service.TestQuestionService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.UtilValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
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
    AnswerMapper answerMapper;

    @Autowired
    PracticeRelevanceMapper practiceRelevanceMapper;

    @Autowired
    PracticePaperMapper practicePaperMapper;

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


    //重点试题-组卷z
    @Override
    public  Map<TestQuestions,List<Answer>> randomQuestColl(Map<String, Object> param) {
        //1,生成题目
        Map<TestQuestions,List<Answer>> map=new HashedMap();
        if(UtilValidate.isEmpty(param.get("num"))){
            param.put("num",10);//获取组成10题
        }
        List<TestQuestions> testQuestions = testQuestionsMapper.randomQuestColl(param);//仅仅只有id,提高效率

        //2。生成练习
        String uuid = IdWorker.get32UUID();
        PracticePaper practicePaper=new PracticePaper();
        practicePaper.setId(uuid);
        practicePaper.setOpttime(new Date());


        testQuestions.stream().forEach(e->{
            String id=e.getId();
            TestQuestions question = testQuestionsMapper.selectById(id);//获取题目
            List<Answer> answers = answerMapper.selectList(new EntityWrapper<Answer>().eq("QUESTION_ID", id));//答案
            map.put(question,answers);
        });


        return map;
    }

    //我的收藏-我的错题（获取我的所有的错题）z
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

    //重点试题-组卷z
    @Override
    public  Map<TestQuestions,List<Answer>> randomErrorColl(Map<String, Object> param) {
        Map<TestQuestions,List<Answer>> map=new HashedMap();
        if(UtilValidate.isEmpty(param.get("num"))){
            param.put("num",10);//获取组成10题
        }
        List<TestQuestions> testQuestions = testQuestionsMapper.randomErrorColl(param);//仅仅只有id,提高效率
        testQuestions.stream().forEach(e->{
            String id=e.getId();
            TestQuestions question = testQuestionsMapper.selectById(id);//获取题目
            List<Answer> answers = answerMapper.selectList(new EntityWrapper<Answer>().eq("QUESTION_ID", id));//答案
            map.put(question,answers);
        });
        return map;
    }

    //详情z
    @Override
    public TestQuestions getTestQuestions(TestQuestions testQuestions) {
        TestQuestions testQuestions1 = testQuestionsMapper.selectById(testQuestions.getId());
        return testQuestions1;
    }

}
