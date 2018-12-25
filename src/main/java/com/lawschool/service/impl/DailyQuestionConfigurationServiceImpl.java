package com.lawschool.service.impl;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.*;
import com.lawschool.dao.*;
import com.lawschool.form.AnswerForm;
import com.lawschool.form.QuestForm;
import com.lawschool.service.DailyQuestionConfigurationService;
import com.lawschool.service.DailyRecordService;
import com.lawschool.util.UtilValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DailyQuestionConfigurationServiceImpl extends AbstractServiceImpl<DailyQuestionConfigurationDao, DailyQuestionConfiguration>
        implements DailyQuestionConfigurationService{

    @Autowired
    DailyQuestionConfigurationDao dailyQuestionConfigurationDao;

    @Autowired
    TestQuestionsDao testQuestionsDao;

    @Autowired
    AnswerDao answerDao;

    @Autowired
    DailyRecordDao dailyRecordDao;

    @Autowired
    DailySameDao dailySameDao;

    @Override
    public DailyQuestionConfiguration selectByDailyId(String id) {
        return dailyQuestionConfigurationDao.selectByDailyId(id);
    }

    @Override
    public void deleteByDailyId(String id) {
        dailyQuestionConfigurationDao.deleteByDailyId(id);
    }

    @Override
    public void insertDailyConfig(DailyQuestionConfiguration dailyQuestionConfiguration) {
        dailyQuestionConfiguration.setId(IdWorker.getIdStr());
        //一题对应多个知识点
            /*List<String>  specialKnowledgeIds= dailyQuestionConfiguration.getSpecialKnowledgeIds();
            String str=String.join(",", specialKnowledgeIds);
            dailyQuestionConfiguration.setSpecialKnowledgeId(str);*/
            dailyQuestionConfigurationDao.insert(dailyQuestionConfiguration);

//            dailyQuestionConfigurationDao.insertDailyConfig(dailyQuestionConfiguration);
    }

    @Override
    public void updateByDailyConfig(DailyQuestionConfiguration dailyQuestionConfiguration) {
        dailyQuestionConfigurationDao.update(dailyQuestionConfiguration);
    }

    /**
     * 根据条件随机一题
     * @return
     */
    @Override
    public QuestForm dailyTestCreate() {
        QuestForm question = null;
        //查询当天记录表判断是否做过每日一题
        if(UtilValidate.isEmpty(dailyRecordDao.findCurrentRecord())){
            DailyQuestionConfiguration currentConfig = dailyQuestionConfigurationDao.findCurrentConfig();//查询当前每日一题的配置情况
            //随机不同
            if(0==currentConfig.getCreateRule()){
                Map<String,Object> map = new HashMap<>();//传给sql
                if(UtilValidate.isNotEmpty(currentConfig.getSpecialKnowledgeId())){//知识点
                    map.put("specialKnowledgeId",currentConfig.getSpecialKnowledgeId());
                }
                if(UtilValidate.isNotEmpty(currentConfig.getQuestionType())){//题型
                    map.put("questionType",currentConfig.getQuestionType());
                }
                if(UtilValidate.isNotEmpty(currentConfig.getQuestionDifficulty())){//难度
                    map.put("questionDifficulty",currentConfig.getQuestionDifficulty());
                }
                String questionId = dailyQuestionConfigurationDao.dailyTest(map);//题目ID
                question = testQuestionsDao.findTestQuestionById(questionId);//试题
                List<AnswerForm> listAnswer = answerDao.findByQuesId(questionId);//答案
                question.setAnswer(listAnswer);

                //把做过题目插入记录表
                DailyRecord record = new DailyRecord();
                record.setId(IdWorker.getIdStr());
                record.setQuestionId(question.getId());
                record.setAnswerDescrible(question.getAnswerDescrible());
                record.setAnswerId(question.getAnswerId());
                record.setComContent(question.getComContent());
                record.setLegalBasis(question.getLegalBasis());
                record.setQuestionDifficulty(question.getQuestionDifficulty());
                record.setQuestionType(question.getQuestionType());
                record.setOptTime(new Date());
                //record.setOptUser();
                dailyRecordDao.insertRecord(record);
            }
            //统一出题
            if(1==currentConfig.getCreateRule()){
                Map<String,Object> map = new HashMap<>();//传给sql
                if(UtilValidate.isNotEmpty(currentConfig.getSpecialKnowledgeId())){//知识点
                    map.put("specialKnowledgeId",currentConfig.getSpecialKnowledgeId());
                }
                /*if(UtilValidate.isNotEmpty(currentConfig.getQuestionType())){//题型
                    map.put("questionType",currentConfig.getQuestionType());
                }*/
                if(UtilValidate.isNotEmpty(currentConfig.getQuestionDifficulty())){//难度
                    map.put("questionDifficulty",currentConfig.getQuestionDifficulty());
                }
                String questionId = dailyQuestionConfigurationDao.dailyTest(map);//题目ID
                question = testQuestionsDao.findTestQuestionById(questionId);//试题
                List<AnswerForm> listAnswer = answerDao.findByQuesId(questionId);//答案
                question.setAnswer(listAnswer);

                //随机一题插入统一试题记录表
                DailySame record = new DailySame();
                record.setId(IdWorker.getIdStr());
                record.setCreateTime(new Date());
                record.setComContent(question.getComContent());
                record.setLegalBasis(question.getLegalBasis());
                record.setAnswerDescrible(question.getAnswerDescrible());
                record.setAnswerId(question.getAnswerId());
                record.setQuestionType(question.getQuestionType());
                record.setQuestionId(question.getId());
                record.setQuestionDifficulty(question.getQuestionDifficulty());
                dailySameDao.insert(record);
                //从记录表中取当天统一试题记录，并转化为QuestionForm类型
                DailySame questionSame = dailySameDao.findCurrentSameTest();
                QuestForm questionSame02 = new QuestForm();
                questionSame02.setAnswerId(questionSame.getAnswerId());
                questionSame02.setAnswerId(questionSame.getAnswerId());
                questionSame02.setAnswerDescrible(questionSame.getAnswerDescrible());
                questionSame02.setComContent(questionSame.getComContent());
                questionSame02.setLegalBasis(questionSame.getLegalBasis());
                questionSame02.setQuestionDifficulty(questionSame.getQuestionDifficulty());
                questionSame02.setQuestionType(questionSame.getQuestionType());
                questionSame02.setAnswer(answerDao.findByQuesId(questionSame.getQuestionId()));
                question = questionSame02;

                //把做过题目插入记录表
                DailyRecord re = new DailyRecord();
                re.setId(IdWorker.getIdStr());
                re.setQuestionId(questionSame02.getId());
                re.setAnswerDescrible(questionSame02.getAnswerDescrible());
                re.setAnswerId(questionSame02.getAnswerId());
                re.setComContent(questionSame02.getComContent());
                re.setLegalBasis(questionSame02.getLegalBasis());
                re.setQuestionDifficulty(questionSame02.getQuestionDifficulty());
                re.setQuestionType(questionSame02.getQuestionType());
                re.setOptTime(new Date());
                //record.setOptUser();
                dailyRecordDao.insertRecord(re);

            }
        }
        return question;
    }
}
