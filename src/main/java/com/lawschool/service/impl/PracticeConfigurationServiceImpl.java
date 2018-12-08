package com.lawschool.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.*;
import com.lawschool.dao.*;
import com.lawschool.service.PracticeConfigurationService;
import com.lawschool.util.GetUUID;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;
import com.lawschool.util.UtilValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author liuhuan
 * @version 1.0
 * @Descriptin 练习卷配置
 * @Time 2018/12/04
 */
@Service
public class PracticeConfigurationServiceImpl implements PracticeConfigurationService {

    @Autowired
    PracticeConfigurationDao practiceConfigurationDao;

    @Autowired
    AnswerDao answerDao;

    @Autowired
    TestQuestionsDao testQuestionsDao;

    @Autowired
    PracticeRelevanceDao practiceRelevanceDao;

    @Autowired
    PracticePaperDao practicePaperDao;

    /**
     * 展示全部练习配置
     */
    @Override
    public PageUtils listAll() {
        int count = practiceConfigurationDao.selectCount(new EntityWrapper<PracticeConfiguration>());
        long pageSize = 15;
        int pageNo = 1;
        List<PracticeConfiguration> configurationList = practiceConfigurationDao.selectList(new EntityWrapper<PracticeConfiguration>());
        PageUtils page = new PageUtils(configurationList,count,pageSize,pageNo);
        return page;
    }

    /**
     * 保存联系配置
     *
     * @param practiceConfiguration
     */
    @Override
    public void save(PracticeConfiguration practiceConfiguration) {
        practiceConfigurationDao.insert(practiceConfiguration);
    }

    /**
     * 根据ID查询练习卷配置
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<TestQuestions> selectQuestionsByKnowledgeId(String id) {
        return practiceConfigurationDao.selectQuestionsByKnowledgeId(id);
    }

    /**
     * 随机出题
     *
     * @param map
     * @return
     */
    @Override
    public List queryRandom(Map map) {
        return practiceConfigurationDao.queryRandom(map);
    }

    /**
     * 生成练习卷名称
     *
     * @param prefix 前缀
     */
    public Result createPaperName(String prefix) {
        String sysNum = GetUUID.getUUIDs(prefix);//根据前缀生成系统编号
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        String date = sdf.format(new Date());
        //去点日期中间的“-”
        String[] array = date.split("-");
        StringBuffer sb = new StringBuffer();
        for(int i=1;i<array.length;i++){
            sb.append(array[i]);
        }

        return Result.ok().put("paperName", sb+ sysNum );
    }

    /**
     * 自定义练习卷
     *
     * @param paramsList
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result createPracticePaper(@RequestParam List<PracticeConfiguration> paramsList,User user) {
        //存储数据传给sql
        Map map = new HashMap();
        //存储生成试题
        Map<TestQuestions, List<Answer>> resultMap = new HashMap<>();
        //生成练习id
        String practiceId = GetUUID.getUUIDs("Prac");
        PracticePaper newPaper = new PracticePaper();
        newPaper.setId(practiceId);
        newPaper.setPracCreatTime(new Date());
        newPaper.setPracPaperType("自定义");
        newPaper.setOptuser(UtilValidate.isNotEmpty(user.getUserName())?user.getUserName():"");
        //存储试题id
        List<TestQuestions> questionsIdList = new ArrayList<TestQuestions>();
        for (PracticeConfiguration params : paramsList) {
            String knowledgeID = params.getSpecialKnowledgeId();
            Integer easyCount = params.getPrimaryCount();
            Integer midCount = params.getIntermediateCount();
            Integer hardCount = params.getSeniorCount();
            newPaper.setCount(easyCount+midCount+hardCount);
            map.put("knowledgeID", knowledgeID);
            map.put("difficulty", 1);
            map.put("row_count", easyCount);
            //获取题目ids
            List list01 = practiceConfigurationDao.queryRandom(map);
            questionsIdList.addAll(list01);

            map.put("knowledgeID", knowledgeID);
            map.put("difficulty", 2);
            map.put("row_count", midCount);
            List list02 = practiceConfigurationDao.queryRandom(map);
            questionsIdList.addAll(list02);

            map.put("knowledgeID", knowledgeID);
            map.put("difficulty", 3);
            map.put("row_count", hardCount);
            List list03 = practiceConfigurationDao.queryRandom(map);
            questionsIdList.addAll(list03);
        }
        //插入练习卷表
        practicePaperDao.insert(newPaper);
        for (TestQuestions e : questionsIdList) {
            map.put("id",e.getId());
            TestQuestions testQuestions = testQuestionsDao.selectById(e.getId());
            List<Answer> answerList = answerDao.selectList(new EntityWrapper<Answer>().eq("QUESTION_ID", e.getId()));
            resultMap.put(testQuestions, answerList);
            //存入试卷题目关联表
            PracticeRelevance relevance = new PracticeRelevance();
            relevance.setId(IdWorker.getIdStr());
            relevance.setPracticeId(practiceId);
            relevance.setQuestionId(e.getId());
            practiceRelevanceDao.insert(relevance);
        }
        return Result.ok().put("data", resultMap);
    }

}
