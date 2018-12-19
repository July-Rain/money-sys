package com.lawschool.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import com.lawschool.beans.*;
import com.lawschool.dao.*;
import com.lawschool.form.AnswerForm;
import com.lawschool.form.DiyPracPaperForm;
import com.lawschool.form.QuestForm;
import com.lawschool.service.AnswerService;
import com.lawschool.service.PracticeConfigurationSonService;
import com.lawschool.service.PracticeConfigurationService;
import com.lawschool.service.TestQuestionService;
import com.lawschool.util.GetUUID;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;
import com.lawschool.util.UtilValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author liuhuan
 * @version 1.0
 * @Descriptin 练习卷配置
 * @Time 2018/12/04
 */
@Service
public class PracticeConfigurationServiceImpl extends ServiceImpl<PracticeConfigurationDao,PracticeConfiguration> implements PracticeConfigurationService {

    @Autowired
    private PracticeConfigurationDao practiceConfigurationDao;

    @Autowired
    private AnswerDao answerDao;

    @Autowired
    private TestQuestionsDao testQuestionsDao;

    @Autowired
    PracticeRelevanceDao practiceRelevanceDao;

    @Autowired
    PracticePaperDao practicePaperDao;
    @Autowired
    private PracticeConfigurationSonService practiceConfigurationSonService;
    @Autowired
    private TestQuestionService testQuestionService;
    @Autowired
    private AnswerService answerService;

    /**
     * 展示全部配置列表信息
     */
    @Override
    public List<PracticeConfiguration> list() {

        List<PracticeConfiguration> list = practiceConfigurationDao.selectList(new EntityWrapper<>());

        return list;

    }

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
//        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
//        String date = sdf.format(new Date());
//        //去点日期中间的“-”
//        String[] array = date.split("-");
//        StringBuffer sb = new StringBuffer();
//        for(int i=1;i<array.length;i++){
//            sb.append(array[i]);
//        }

        return Result.ok().put("paperName",  sysNum );
    }

    @Override
    public PracticeConfiguration selectById(String id) {
        return practiceConfigurationDao.selectById(id);
    }

    @Override
    public void insertDiy(PracticeConfiguration practiceConfiguration) {
        practiceConfigurationDao.insertDiy(practiceConfiguration);
    }

    /**
     * 自定义练习卷
     *
     * @param configId
     * @return
     */
    @Override
//    @Transactional(rollbackFor = Exception.class)
    public List<QuestForm> createPracticePaper(String configId) {
        PracticeConfiguration params = practiceConfigurationDao.selectById(configId);
        //根据主表id 找从表数据


        List<PracticeConfiguration02> listSon = practiceConfigurationSonService.selectList(new EntityWrapper<PracticeConfiguration02>().eq("CONFIGURATION_ID",configId));




        DiyPracPaperForm recieve = new DiyPracPaperForm();
        List<DiyPracPaperForm> recieveList = new ArrayList<DiyPracPaperForm>();
        //存储数据传给sql
        Map map = new HashMap();
        PracticePaper newPaper = new PracticePaper();
        newPaper.setId(configId);//从前台获取练习卷id
        newPaper.setPracCreatTime(new Date());
        newPaper.setPracPaperType("自定义");
        //newPaper.setOptuser(UtilValidate.isNotEmpty(user.getUserName())?user.getUserName():"");
        //存储试题id
        List<String> questionsIdList = new ArrayList<>();
//            List<PracticeConfiguration02> listSon = params.getList();
            for(PracticeConfiguration02 son:listSon){
                String knowledgeID = son.getSpecialKnowledgeId();
                String easyCount = String.valueOf(son.getPrimaryCount());
                String midCount = String.valueOf(son.getIntermediateCount());
                String hardCount = String.valueOf(son.getSeniorCount());
                newPaper.setCount(Integer.valueOf(easyCount+midCount+hardCount));
                if(UtilValidate.isNotEmpty(easyCount)){//只要easyCount存在，则难点，知识点就会存在,只判断一个
                    map.put("knowledgeID", knowledgeID);
                    map.put("difficulty", 1);
                    map.put("row_count", easyCount);
                    //获取题目ids
                    List<String> list01 = practiceConfigurationDao.queryRandom(map);
                    questionsIdList.addAll(list01);
                }
                if(UtilValidate.isNotEmpty(midCount)){
                    map.put("knowledgeID", knowledgeID);
                    map.put("difficulty", 2);
                    map.put("row_count", midCount);
                    List<String> list02 = practiceConfigurationDao.queryRandom(map);
                    questionsIdList.addAll(list02);
                }
                if(UtilValidate.isNotEmpty(hardCount)){
                    map.put("knowledgeID", knowledgeID);
                    map.put("difficulty", 3);
                    map.put("row_count", hardCount);
                    List<String> list03 = practiceConfigurationDao.queryRandom(map);
                    questionsIdList.addAll(list03);
                }
            }
        //插入练习卷表
        practicePaperDao.insert(newPaper);


        List<QuestForm> Questlist=testQuestionService.findByIds(questionsIdList);
        List<AnswerForm> answerForms = answerService.findByQuestionIds(questionsIdList);
// 遍历处理选项信息
        for(QuestForm quest : Questlist){//遍历题目   我要得到答案啊
            String questid = quest.getId();
            List<AnswerForm> tempList = new ArrayList<>();
            for(AnswerForm af : answerForms){
                String aqid = af.getQuestionId();
                if(questid.equals(aqid)){
                    tempList.add(af);
                }
            }
            quest.setAnswer(tempList);
            answerForms.removeAll(tempList);
        }

        System.out.println(Questlist);
//        return Questlist;
//        for (TestQuestions e : questionsIdList) {
//            //存储ID
//            map.put("id",e.getId());
//            TestQuestions testQuestions = testQuestionsDao.selectById(e.getId());
//            List<Answer> answerList = answerDao.selectList(new EntityWrapper<Answer>().eq("QUESTION_ID", e.getId()));
//            //resultMap.put(testQuestions, answerList);
//            //存入试卷题目关联表
//            PracticeRelevance relevance = new PracticeRelevance();
//            relevance.setId(IdWorker.getIdStr());
//            relevance.setPracticeId(configId);//练习卷ID
//            relevance.setQuestionId(e.getId());
//            practiceRelevanceDao.insert(relevance);
//
//            //将数据存储到自定义的返回类型中
//            recieve.setId(e.getId());//题目id
//            recieve.setComContent(testQuestions.getComContent());//题目内容
//            recieve.setQuestionDifficulty(testQuestions.getQuestionDifficulty());//题目难度
//            recieve.setQuestionType(testQuestions.getQuestionType());//题目类型
//            recieve.setAnswer(answerList);//答案list
//            recieve.setAnswerChoiceNumber(testQuestions.getAnswerChoiceNumber());//选项数
//            recieve.setAnswerDescrible(testQuestions.getAnswerDescrible());//答案描述
//            recieve.setLegalBasis(testQuestions.getLegalBasis());//法律依据
//            recieveList.add(recieve);
//        }
        return Questlist;
    }

}
