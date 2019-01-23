package com.lawschool.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.annotation.SysLog;
import com.lawschool.base.AbstractServiceImpl;

import com.lawschool.beans.*;
import com.lawschool.beans.competition.CompetitionRecord;
import com.lawschool.dao.*;
import com.lawschool.form.AnswerForm;
import com.lawschool.form.QuestForm;
import com.lawschool.service.DailyQuestionConfigurationService;
import com.lawschool.service.DailyRecordService;
import com.lawschool.service.IntegralService;
import com.lawschool.service.UserQuestRecordService;
import com.lawschool.util.Result;
import com.lawschool.util.UtilValidate;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    UserQuestRecordService userQuestRecordService;
    @Autowired
    private IntegralService integralService;
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
    public int updateByDailyConfig(DailyQuestionConfiguration dailyQuestionConfiguration) {

        int i=0;
//        保存前  先判断时间区间有没有重叠
        List<DailyQuestionConfiguration> list= this.selectList(new EntityWrapper<DailyQuestionConfiguration>());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Boolean b=true;
//        循环list的得到实体
        for(DailyQuestionConfiguration dailyQuestion:list)
        {
            //前提 不比较自己
            if(dailyQuestion.getId().equals(dailyQuestionConfiguration.getId()))
            {

            }
            else{

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

        }
        if(b==true)
        {
            //没交集 正常修改
            dailyQuestionConfigurationDao.updateById(dailyQuestionConfiguration);
            i=1;
        }
        else if(b==false)
        {
            //有交集  不能添加
            i=0;
        }
        return i;
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
       }

       else
           {
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




    /**
     * 根据条件随机一题  (新的)
     * @return
     */
    @Override
    public Result newDailyTestCreate() {
        String s=null;
        String myaswer=null;
        User u = (User) SecurityUtils.getSubject().getPrincipal();
        QuestForm question = null;
        DailyQuestionConfiguration  currentConfig = dailyQuestionConfigurationDao.findCurrentConfig();//查询当前每日一题的配置情况


        //去答题记录表中 找数据，看有没有做过每日一题，因为做过的每日一题都会在答题记录表中有记录的
        //先处理下今天的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
             s = sdf.format(new Date());
        }catch (Exception e)
        {
            System.out.println(e);
        }
        UserQuestRecord userQuestRecord=userQuestRecordService.selectOne(new EntityWrapper<UserQuestRecord>().eq("TO_CHAR(OPT_TIME,'YYYY-MM-DD')",s).eq("USER_ID",u.getId()).eq("SOURCE","everyDay"));
        if(userQuestRecord==null)//没找到数据,说明今天没有做过 每日一题 。。。然而我要上题目  1，是每个人的题目都不一样   2.是每个人的题目都一样  先判断是哪种
        {


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

            //随机不同  ,  不用想   dailyRecord 这张表里是没数据的
            if("2".equals(currentConfig.getCreateRule())){

                // 先看这张表里有没有数据
                List<DailyRecord> dailyRecordList=dailyRecordDao.selectList(new EntityWrapper<DailyRecord>().eq("TO_CHAR(OPT_TIME,'YYYY-MM-DD')",s).eq("USER_ID",u.getId()));
                if(dailyRecordList.size()==0)//说明今天的题目还没有人添加过  那么你就是第一个来添加
                {
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
                    dailyRecordDao.insertRecord(record);//将这个人 做什么题目  查到表中
                }
                else if(dailyRecordList.size()>0)//说明里面今天有题目了  直接拿来就用
                {
                    questionId = dailyRecordList.get(0).getQuestionId();//题目ID
                    question = testQuestionsDao.findTestQuestionById(questionId);//试题
                    listAnswer = answerDao.findByQuesId(questionId);//答案
                    question.setAnswer(listAnswer);
                }
            }
            //统一出题
            else if("1".equals(currentConfig.getCreateRule())){

                //大家今天共用一道题 不分你我  一个人添加了 后面的人就不用加了
                //先判断今天里面有没有题目

                   List<DailySame> dailySameList=dailySameDao.selectList(new EntityWrapper<DailySame>().eq("TO_CHAR(CREATE_TIME,'YYYY-MM-DD')",s));
                   if(dailySameList.size()==0)//说明今天的题目还没有人添加过  那么你就是第一个来添加
                   {
                        //一题插入(统一试题记录表:LAW_DAILY_SAME)
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
                  else if(dailySameList.size()>0)//说明里面今天有题目了  直接拿来就用
                  {
                       questionId = dailySameList.get(0).getQuestionId();//题目ID
                       question = testQuestionsDao.findTestQuestionById(questionId);//试题
                       listAnswer = answerDao.findByQuesId(questionId);//答案
                       question.setAnswer(listAnswer);
                  }
            }

        }
        else//找到了做过题目的数据了
        {
            String  questionId =  userQuestRecord.getQuestId();//得到了做的题目编号
            question = testQuestionsDao.findTestQuestionById(questionId);//试题
            List<AnswerForm> listAnswer = answerDao.findByQuesId(questionId);//答案
            question.setAnswer(listAnswer);
            myaswer=userQuestRecord.getMyAswerId();//我的答案
        }
        return Result.ok().put("question",question).put("currentConfig",currentConfig).put("myaswerid",myaswer);
//        lastMap.put("question",question);
//        lastMap.put("currentConfig",currentConfig);
//        return lastMap;

    }


    @Override
    @SysLog("保存做过的每日一题")
    @Transactional(rollbackFor = Exception.class)
    public void saveQuestion(TestQuestions testQuestions, String myanswer) {
        User u = (User) SecurityUtils.getSubject().getPrincipal();
        UserQuestRecord userQuestRecord=new UserQuestRecord();
        userQuestRecord.setId(IdWorker.getIdStr());//id
        userQuestRecord.setUserId(u.getId());//userid
        userQuestRecord.setQuestId(testQuestions.getId());//题目id
        userQuestRecord.setOptTime(new Date());//保存时间
        userQuestRecord.setMyAswerId(myanswer);//对应题目答案表的id
        userQuestRecord.setRightAnswerId(testQuestions.getAnswerId());//这题的正确答案，对应题目答案表的id
        userQuestRecord.setQuestionDifficulty(testQuestions.getQuestionDifficulty());//难度
        userQuestRecord.setQuestionType(testQuestions.getQuestionType());//题目类型
        userQuestRecord.setSpecialKnowledgeId(testQuestions.getSpecialKnowledgeId());//知识点
        userQuestRecord.setSource("everyDay");//添加来源
        userQuestRecordService.insert(userQuestRecord);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recordScore(User u, String sorce) {
        //加完后在添加 另外的积分表
        Integral integral=new Integral();
        integral.setType("1");
        integral.setPoint(Integer.parseInt(sorce));
        integral.setSrc("everyDay");
        integralService.addIntegralRecord(integral,u);
    }
}
