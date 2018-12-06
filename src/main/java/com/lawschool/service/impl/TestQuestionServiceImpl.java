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


    //我的收藏-重点试题（我收藏的题目）-zjw
    @Override
    public PageUtils listMyCollection(Map<String, Object> param) {
        int pageNo = 1;
        long pageSize = 10l;
        if (UtilValidate.isNotEmpty(param.get("pageNo"))) {
            pageNo = parseInt((String) param.get("pageNo"));
        }
        if (UtilValidate.isNotEmpty(param.get("pageSize"))) {
            pageSize = Long.parseLong((String) param.get("pageSize"));
        }

        //总个数
        int count = testQuestionsMapper.cntMyCollection(param);

        param.put("startPage", (pageNo - 1) * pageSize);
        param.put("pageSize", pageNo * pageSize);

        List<TestQuestions> testQuestions = testQuestionsMapper.listMyCollection(param);

        PageUtils page=new PageUtils(testQuestions,count,pageSize, pageNo);

        return page;
    }


    //重点试题-组卷-zjw
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result randomQuestColl(Map<String, Object> param, User user) {
        int num=10;
        //1,生成题目
        Map<TestQuestions,List<Answer>> map=new HashedMap();
        if(UtilValidate.isNotEmpty(param.get("num"))){
            try{
                num= parseInt(param.get("num").toString());
            }catch(Exception e){
                return Result.error("题目个数要为整型");
            }
        }
        param.put("num",num);//获取组成10题
        param.put("userId",user.getId());
        param.put("userId",1);
        List<TestQuestions> testQuestions = testQuestionsMapper.randomQuestColl(param);//仅仅只有id,提高效率

        //2。生成练习
        String pid = GetUUID.getUUIDs("PP");
        PracticePaper practicePaper=new PracticePaper();
        practicePaper.setId(pid);

        practicePaper.setPracCreatUser(UtilValidate.isNotEmpty(user.getUserName())?user.getUserName():"");
        practicePaper.setPracCreatTime(new Date());

        practicePaper.setPracticeName(UtilValidate.isNotEmpty(param.get("pname"))? param.get("pname").toString():"收藏练习");

        if(UtilValidate.isNotEmpty(user.getOrgCode())){
            List<Org> org_code = orgDao.selectList(new EntityWrapper<Org>().setSqlSelect("DICTIONARY_NAME").eq("ORG_CODE", user.getOrgCode()));
            practicePaper.setPracCreatDepartment(UtilValidate.isNotEmpty(org_code)?org_code.get(0).getDictionaryName():"");
        }
        practicePaper.setOptuser(UtilValidate.isNotEmpty(user.getUserName())?user.getUserName():"");
        practicePaper.setOpttime(new Date());

        if(UtilValidate.isNotEmpty(param.get("knowledge"))){
            practicePaper.setStuKnowledge(param.get("knowledge").toString());
        }

        practicePaper.setCount(num);
        practicePaper.setPracPaperType("自定义");

        practicePaperMapper.insert(practicePaper);

        //3.生成练习-试题关联表，以及选项的获取
        testQuestions.stream().forEach(e->{
            String qid=e.getId();
            TestQuestions question = testQuestionsMapper.selectById(qid);//获取题目
            List<Answer> answers = answerMapper.selectList(new EntityWrapper<Answer>().eq("QUESTION_ID", qid));//选项

            //记录表
            String rid = GetUUID.getUUIDs("PR");
            PracticeRelevance relevance=new PracticeRelevance();
            relevance.setId(rid);
            relevance.setPracticeId(pid);
            relevance.setQuestionId(qid);

            practiceRelevanceMapper.insert(relevance);

            map.put(question,answers);
        });


        return Result.ok().put("pid",pid).put("data",map);
    }

    //我的收藏-我的错题（获取我的所有的错题）-zjw
    @Override
    public PageUtils listMyErrorQuestion(Map<String, Object> param) {
        int pageNo=1;
        long pageSize=10l;
        if(UtilValidate.isNotEmpty(param.get("pageNo"))){
            pageNo= parseInt((String) param.get("pageNo"));
        }
        if (UtilValidate.isNotEmpty(param.get("pageSize"))) {
            pageSize = Long.parseLong((String) param.get("pageSize"));
        }

        //总个数
        int count = testQuestionsMapper.cntMyError(param);

        param.put("startPage", (pageNo - 1) * pageSize);
        param.put("pageSize", pageNo * pageSize);
        List<TestQuestions> testQuestions = testQuestionsMapper.listMyError(param);

        PageUtils page = new PageUtils(testQuestions, count, pageSize, pageNo);

        return page;
    }

    //重点试题-组卷-zjw
    @Override
    @Transactional(rollbackFor = Exception.class)
    public  Result randomErrorColl(Map<String, Object> param,User user) {
        int num=10;
        //1,生成题目
        Map<TestQuestions,List<Answer>> map=new HashedMap();
        if(UtilValidate.isNotEmpty(param.get("num"))){
            try{
                num= parseInt(param.get("num").toString());
            }catch(Exception e){
                return Result.error("题目个数要为整型");
            }
        }
        param.put("num",num);//获取组成10题
        param.put("userId",user.getId());
        param.put("userId",1);

        List<TestQuestions> testQuestions = testQuestionsMapper.randomErrorColl(param);//仅仅只有id,提高效率

        //2。生成练习
        String pid = IdWorker.get32UUID();
        PracticePaper practicePaper=new PracticePaper();
        practicePaper.setId(pid);

        practicePaper.setPracCreatUser(UtilValidate.isNotEmpty(user.getUserName())?user.getUserName():"");
        practicePaper.setPracCreatTime(new Date());

        practicePaper.setPracticeName(UtilValidate.isNotEmpty(param.get("pname"))? param.get("pname").toString():"错题练习");

        if(UtilValidate.isNotEmpty(user.getOrgCode())){
            List<Org> org_code = orgDao.selectList(new EntityWrapper<Org>().setSqlSelect("DICTIONARY_NAME").eq("ORG_CODE", user.getOrgCode()));
            practicePaper.setPracCreatDepartment(UtilValidate.isNotEmpty(org_code)?org_code.get(0).getDictionaryName():"");
        }
        practicePaper.setOptuser(UtilValidate.isNotEmpty(user.getUserName())?user.getUserName():"");
        practicePaper.setOpttime(new Date());

        if(UtilValidate.isNotEmpty(param.get("knowledge"))){
            practicePaper.setStuKnowledge(param.get("knowledge").toString());
        }

        practicePaper.setCount(num);
        practicePaper.setPracPaperType("自定义");

        practicePaperMapper.insert(practicePaper);
        //3.生成练习-试题关联表，以及选项的获取
        testQuestions.stream().forEach(e->{
            String qid=e.getId();
            TestQuestions question = testQuestionsMapper.selectById(qid);//获取题目
            List<Answer> answers = answerMapper.selectList(new EntityWrapper<Answer>().eq("QUESTION_ID", qid));//答案
            //记录表
            String rid = IdWorker.get32UUID();
            PracticeRelevance relevance=new PracticeRelevance();
            relevance.setId(rid);
            relevance.setPracticeId(pid);
            relevance.setQuestionId(qid);

            practiceRelevanceMapper.insert(relevance);
            map.put(question,answers);
        });
        return Result.ok().put("pid",pid).put("data",map);
    }

    //详情-zjw
    @Override
    public TestQuestions getTestQuestions(TestQuestions testQuestions) {
        TestQuestions testQuestions1 = testQuestionsMapper.selectById(testQuestions.getId());
        return testQuestions1;
    }

}
