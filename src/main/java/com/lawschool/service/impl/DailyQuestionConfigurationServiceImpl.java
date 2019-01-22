package com.lawschool.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.*;
import com.lawschool.dao.*;
import com.lawschool.form.AnswerForm;
import com.lawschool.form.QuestForm;
import com.lawschool.service.DailyQuestionConfigurationService;
import com.lawschool.service.DailyRecordService;
import com.lawschool.util.Result;
import com.lawschool.util.UtilValidate;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
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
    public int insertDailyConfig(DailyQuestionConfiguration dailyQuestionConfiguration) {
            int i=0;
//        保存前  先判断时间区间有没有重叠
         List<DailyQuestionConfiguration> list= this.selectList(new EntityWrapper<DailyQuestionConfiguration>());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Boolean b=true;
//        循环list的得到实体
        for(DailyQuestionConfiguration dailyQuestion:list)
        {
            try{
                String s1 = sdf.format( dailyQuestion.getBeginTime());
                Date beginTime =  sdf.parse(s1);
                String s2 = sdf.format( dailyQuestion.getEndTime());
                Date endTime =  sdf.parse(s2);

                if ( (sdf.parse(sdf.format(dailyQuestionConfiguration.getEndTime())).getTime()< beginTime.getTime()) || (sdf.parse(sdf.format(dailyQuestionConfiguration.getBeginTime())).getTime()> endTime.getTime()))
                {
                    //无交集
                }
                else
                {
                    //有交集

                    b=false;
                    break;
                }

            }catch (Exception e)
            {
                System.out.println(e);
            }
        }
        if(b==true)
        {
            //没交集 正常添加
            User u = (User) SecurityUtils.getSubject().getPrincipal();
            dailyQuestionConfiguration.setCreateUser(u.getId());
            dailyQuestionConfiguration.setId(IdWorker.getIdStr());
            dailyQuestionConfigurationDao.insert(dailyQuestionConfiguration);
           i=1;
        }
        else if(b==false)
        {
            //有交集  不能添加
           i=0;
        }
        return i;
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
    public Result dailyTestCreate() {
        User u = (User) SecurityUtils.getSubject().getPrincipal();
        QuestForm question = null;
        DailyQuestionConfiguration currentConfig = null;
        currentConfig = dailyQuestionConfigurationDao.findCurrentConfig();//查询当前每日一题的配置情况
        //查询当天记录表判断是否做过每日一题
        if(UtilValidate.isEmpty(dailyRecordDao.findCurrentRecord(u.getId())) && UtilValidate.isEmpty(dailySameDao.findCurrentSameTest())){

            //随机一道试题
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

            //随机不同
            if("0".equals(currentConfig.getCreateRule())){
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
                record.setUserId(u.getId());
                record.setOptTime(new Date());
                //record.setOptUser();
                dailyRecordDao.insertRecord(record);
            }
            //统一出题
            if("1".equals(currentConfig.getCreateRule())){
                //随机一题插入(统一试题记录表:LAW_DAILY_SAME)
                DailySame record = new DailySame();
                record.setId(IdWorker.getIdStr());
                record.setCreateTime(new Date());
                record.setComContent(question.getComContent());
                record.setLegalBasis(question.getLegalBasis());
                record.setAnswerDescrible(question.getAnswerDescrible());
                record.setAnswerId(question.getAnswerId());//答案ID
                record.setQuestionType(question.getQuestionType());
                record.setQuestionId(question.getId());
                record.setQuestionDifficulty(question.getQuestionDifficulty());
                dailySameDao.insert(record);
            }
       }else{
            if("1".equals(currentConfig.getCreateRule()))//统一出题
            {
                DailySame questionSame = dailySameDao.findCurrentSameTest();
                List<AnswerForm> listAnswer = answerDao.findByQuesId(questionSame.getQuestionId());//答案
                questionSame.setAnswer(listAnswer);
                question = new QuestForm();
                question.setId(IdWorker.getIdStr());
                question.setQuestionType(questionSame.getQuestionType());
                question.setAnswerId(questionSame.getAnswerId());
                question.setAnswer(questionSame.getAnswer());
                question.setQuestionDifficulty(questionSame.getQuestionDifficulty());
                question.setComContent(questionSame.getComContent());
                question.setLegalBasis(questionSame.getLegalBasis());
                question.setAnswerDescrible(questionSame.getAnswerDescrible());
            }
            else if("0".equals(currentConfig.getCreateRule()))//随机出题
            {
                DailyRecord questionSame = dailyRecordDao.findCurrentRecord(u.getId());
                List<AnswerForm> listAnswer = answerDao.findByQuesId(questionSame.getQuestionId());//答案
                questionSame.setAnswerList(listAnswer);
                question = new QuestForm();
                question.setId(IdWorker.getIdStr());
                question.setQuestionType(questionSame.getQuestionType());
                question.setAnswerId(questionSame.getAnswerId());
                question.setAnswer(questionSame.getAnswerList());
                question.setQuestionDifficulty(questionSame.getQuestionDifficulty());
                question.setComContent(questionSame.getComContent());
                question.setLegalBasis(questionSame.getLegalBasis());
                question.setAnswerDescrible(questionSame.getAnswerDescrible());
            }

        }
        return Result.ok().put("question",question).put("currentConfig",currentConfig);
    }
}
