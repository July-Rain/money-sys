package com.lawschool.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.annotation.SysLog;
import com.lawschool.base.AbstractServiceImpl;

import com.lawschool.beans.*;
import com.lawschool.beans.competition.CompetitionRecord;
import com.lawschool.constants.ConstantSets;
import com.lawschool.dao.*;
import com.lawschool.form.AnswerForm;
import com.lawschool.form.CommonDateForm;
import com.lawschool.form.DailyForm;
import com.lawschool.form.QuestForm;
import com.lawschool.service.*;
import com.lawschool.util.DateTimeUtils;
import com.lawschool.util.Result;
import com.lawschool.util.UtilValidate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DailyQuestionConfigurationServiceImpl extends AbstractServiceImpl<DailyQuestionConfigurationDao, DailyQuestionConfiguration>
        implements DailyQuestionConfigurationService{

    private static final String synFlag = "synchronized";

    @Autowired
    private DailyQuestionConfigurationDao dailyQuestionConfigurationDao;

    @Autowired
    private DailyRecordService dailyRecordService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private TestQuestionService testQuestionService;

    @Autowired
    private DailySameDao dailySameDao;

    @Override
    public DailyQuestionConfiguration selectByDailyId(String id) {
        DailyQuestionConfiguration dailyQuestionConfiguration = dailyQuestionConfigurationDao.selectByDailyId(id);
        if (UtilValidate.isNotEmpty(dailyQuestionConfiguration.getQuestionDifficulty())){
            dailyQuestionConfiguration.setDiffcs(dailyQuestionConfiguration.getQuestionDifficulty().split(","));
        }
        if (UtilValidate.isNotEmpty(dailyQuestionConfiguration.getSpecialKnowledgeId())){
            dailyQuestionConfiguration.setTopics(dailyQuestionConfiguration.getSpecialKnowledgeId().split(","));
        }
        return dailyQuestionConfiguration;
    }

    @Override
    public void deleteByDailyId(String id) {
        dailyQuestionConfigurationDao.deleteByDailyId(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean mySave(DailyForm form){

        // 题目难度多选
        if(CollectionUtils.isNotEmpty(form.getDiffcs())){
            form.setQuestionDifficulty(String.join(",", form.getDiffcs()));
        }
        if(CollectionUtils.isNotEmpty(form.getTopics())){
            form.setSpecialKnowledgeId(String.join(",", form.getTopics()));
        }

        Integer result = 0;
        if(StringUtils.isNotBlank(form.getId())){
            // 更新
            result = dailyQuestionConfigurationDao.myUpdate(form);

        } else {
            // 新增
            form.setId(IdWorker.getIdStr());
            result = dailyQuestionConfigurationDao.mySave(form);
        }

        if(result != 1){
            return false;
        }
        return true;
    }

    /**
     * 判断生效日期是否重复
     *
     * @param id
     * @param date
     * @return
     */
    public boolean doCheckDate(String id, Date date){
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sim.format(date);

        Integer result = dailyQuestionConfigurationDao.doCheckDate(id, dateStr);
        if(result > 0){
            return false;
        }
        return true;
    }

    /**
     * 获取使用中的每日设置ID
     * @return
     */
    @Override
    public String getSetIdInUse(){
        String result = dao.getSettingsInUse();

        return result;
    }

    @Override
    public DailyQuestionConfiguration getSetInUse(){
        DailyQuestionConfiguration configure = dao.getSetInUse();

        return configure;
    }
    /**
     * 获取每日一题
     * @param date
     * @param userId
     * @return
     */
    @Override
    public QuestForm getQuestion(String date, String userId){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // 定义返回结果集
        QuestForm form = null;

        Map<String, String> params = doCheckDate(date);

        // 处理后的日期参数
        String _date = params.get("date");
        String isToday = params.get("isToday");// 标识是否为今日

        // 为空，说明为未来日期，不做处理,直接返回null
        if(StringUtils.isNotBlank(_date)){

            // 1.直接拿日期和用户信息查询答题记录信息
            form = dailyRecordService.getRecord(_date, userId);

            if(form == null){
                // 如果是今日，则再根据每日一题设置尝试获取题目信息
                if(ConstantSets.CONSTANT_TRUE.equals(isToday)){
                    form = createByConfigure();
                }
            }

            if(form != null){
                List<QuestForm> resultList = new ArrayList<QuestForm>(1);
                resultList.add(form);

                List<String> ids = new ArrayList<String>();
                ids.add(form.getId());
                List<AnswerForm> answerForms = answerService.findByQuestionIds(ids);

                // 处理选项信息
                resultList = testQuestionService.handleAnswers(resultList, answerForms);
            }

        }

        return form;
    }

    /**
     * 判断参数与当前时间的比较
     * @param date 需比较的日期
     * @return 包含2个返回值：date: 日期、isToday: 是否是今日
     */
    protected Map<String, String> doCheckDate(String date){
        Map<String, String> resultMap = new HashMap<String, String>(2);

        // 标识是否为今日,1为今日、0非今日
        String isToday = ConstantSets.CONSTANT_FALSE;

        // 1.处理日期参数
        if(StringUtils.isBlank(date)){
            // 为空，则默认今日数据
            isToday = ConstantSets.CONSTANT_TRUE;
            date = DateTimeUtils.getToday();

        } else {
            // 不为空，则校验日期正确性、是否是未来日期
            String today = DateTimeUtils.getToday();// 今日日期，格式yyyy-MM-dd

            int compare = DateTimeUtils.compare(date, today);
            if(compare == 0){
                isToday = ConstantSets.CONSTANT_TRUE;
                date = today;

            } else if(compare > 0){
                date = null;

            } else {
                // 不做处理
            }
        }

        resultMap.put("date", date);
        resultMap.put("isToday", isToday);

        return resultMap;
    }

    /**
     * 根据题目规则生成题目
     * @return
     */
    protected QuestForm createByConfigure(){
        QuestForm result = null;

        // 1.获取今日使用中的规则
        DailyQuestionConfiguration configure = getSetInUse();

        if(configure != null){
            String createRule = configure.getCreateRule();
            Map<String, String> params = new HashMap<String, String>();
            params.put("topics", configure.getSpecialKnowledgeId());
            params.put("difficulty", configure.getQuestionDifficulty());

            if("1".equals(createRule)){
                // 统一试题
                result = dailySameDao.getDailyQuestion(configure.getId());

                // 有直接返回；无,则根据配置直接生成一题，并保存到统一试题表
                if(result == null){

                    synchronized (synFlag){
                        result = dailySameDao.getDailyQuestion(configure.getId());
                        if(result == null){
                            result = testQuestionService.obtainByRandom(params);

                            // 封装保存参数
                            Map<String, String> saveMap = new HashMap<String, String>(3);
                            saveMap.put("id", IdWorker.getIdStr());
                            saveMap.put("questionId", result.getId());
                            saveMap.put("configureId", configure.getId());

                            dailySameDao.mySave(saveMap);
                        }
                    }

                }

            } else {
                // 随机出题
                result = testQuestionService.obtainByRandom(params);
            }
        }


        return result;
    }
}
