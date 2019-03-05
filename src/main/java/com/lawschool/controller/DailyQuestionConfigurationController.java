package com.lawschool.controller;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.annotation.SysLog;
import com.lawschool.base.AbstractController;
import com.lawschool.base.Page;
import com.lawschool.beans.DailyQuestionConfiguration;
import com.lawschool.beans.TestQuestions;
import com.lawschool.beans.User;
import com.lawschool.form.*;
import com.lawschool.service.DailyQuestionConfigurationService;
import com.lawschool.service.DailyRecordService;
import com.lawschool.util.DateTimeUtils;
import com.lawschool.util.Result;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 */
@RestController
@RequestMapping("/dailyQuestion")
public class DailyQuestionConfigurationController extends AbstractController {
    @Autowired
    private DailyQuestionConfigurationService dailyQuestionConfigurationService;

    @Autowired
    private DailyRecordService dailyRecordService;

    /**
     * 展示配置列表
     * @param params
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Result findPage(@RequestParam Map<String, Object> params){
        // 获取登录用户信息
        User user = getUser();

        // 初始化查询参数
        DailyQuestionConfiguration entity = new DailyQuestionConfiguration();
        entity.setCreateUser(user.getId());

        Page<DailyQuestionConfiguration> page = dailyQuestionConfigurationService.findPage(
                new Page<DailyQuestionConfiguration>(params), entity
        );

        List<DailyQuestionConfiguration> list = page.getList();
        if(CollectionUtils.isNotEmpty(list)){
            // 获取当前使用中的设置ID
            String currentId = dailyQuestionConfigurationService.getSetIdInUse();
            if(StringUtils.isBlank(currentId)){
                currentId = "-1";
            }

            Date now = new Date();

            for(DailyQuestionConfiguration daily : list){
                Date time = daily.getBeginTime();
                if(currentId.equals(daily.getId())){// 当前使用设置
                    daily.setStatus("使用中");
                } else {
                    int compare = this.compareDate(time, now);
                    if(compare < 0){
                        daily.setStatus("已失效");
                    } else {

                        daily.setStatus("未生效");
                    }
                }

            }
        }

        return Result.ok().put("page", page);
    }

    protected int compareDate(Date param1, Date param2){
        int result = 0;
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        String str1 = sim.format(param1);
        String str2 = sim.format(param2);

        try {
            Date temp1 = sim.parse(str1);
            Date temp2 = sim.parse(str2);

            result = temp1.compareTo(temp2);
        } catch (Exception e){
            result = -1;
        }

        return result;
    }

    /**
     * 查询配置
     */
    @RequestMapping("/info")
    public Result info(String id){
        DailyQuestionConfiguration entity = dailyQuestionConfigurationService.selectByDailyId(id);
        return Result.ok().put("data",entity);
    }

    /**
     * 删除配置
     */
    @SysLog("删除每日一题规则")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Result deleteById(@RequestParam String id){
        dailyQuestionConfigurationService.deleteByDailyId(id);
        return Result.ok();
    }

    /**
     * 新增配置
     */
    @SysLog("新增每日一题规则")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestBody DailyForm form){

        boolean check = dailyQuestionConfigurationService.doCheckDate(form.getId(), form.getBeginTime());
        if(!check){
            return Result.error("当前生效时间已存在，请重新设置");
        }

        User user = getUser();
        form.setCreateUserName(user.getFullName());
        form.setUserId(user.getId());

        boolean result = dailyQuestionConfigurationService.mySave(form);
        if(result){
            return Result.ok();

        } else {
            return Result.error("保存失败...");
        }
    }

//    /**
//     * 每日一题题目展示
//     */
//    @RequestMapping(value = "/showDailyTest",method = RequestMethod.POST)
//    public Result showTest(){
//        Result r=  dailyQuestionConfigurationService.dailyTestCreate();
//        return r;
//    }
    /**
     * 每日一题题目展示
     */
//    @RequestMapping(value = "/newshowDailyTest",method = RequestMethod.POST)
//    public Result newshowDailyTest(){
//        Result r=  dailyQuestionConfigurationService.newDailyTestCreate();
//        return r;
//    }

    //答过的题目入库保存
//    @SysLog("答过的题目入库保存")
//    @RequestMapping("/saveQuestion")
//    public void saveQuestion(@RequestBody TestQuestions testQuestions, String myanswer){
//
//        dailyQuestionConfigurationService.saveQuestion(testQuestions,myanswer);
//
//    }

    // 保存分数
//    @SysLog("保存分数")
//    @RequestMapping("/recordScore")
//    public Result recordScore(String sorce){
//        User u = (User) SecurityUtils.getSubject().getPrincipal();
//        dailyQuestionConfigurationService.recordScore(u,sorce);
//        return Result.ok();
//    }

    /**
     * 获取本周日期
     * @return
     */
    @RequestMapping(value = "/dateList", method = RequestMethod.GET)
    public Result getDateList(){

        // 获取本周所有日期
        List<String> days = DateTimeUtils.getWeekDays();

        // 获取当天为第几天
        int todayIndex = DateTimeUtils.getDayOfWeek();
        if(todayIndex == 0){// 周日
            todayIndex = 6;
        } else {
            // 原本应该-1,但是下标从0开始
            todayIndex = todayIndex -2;
        }

        // 定义返回结果集
        List<DailyDateForm> result = new ArrayList<>(todayIndex + 1);

        String[] weeks = new String[]{"周一", "周二", "周三", "周四", "周五", "周六", "周日"};

        // 用于接收今日日期，用于返回
        String today = "";

        for(int i=0; i<=todayIndex; i++){
            DailyDateForm form = new DailyDateForm();
            form.setDate(days.get(i));
            if(i == todayIndex){
                form.setDay("今日");
                form.setActive(true);
                today = days.get(i);
            } else {
                form.setDay(weeks[i]);
                form.setActive(false);
            }

            result.add(form);
        }

        return Result.ok().put("dateList", result).put("today", today);
    }

    /**
     * 获取题目信息
     * @param date 格式yyyy-MM-dd
     * @return
     */
    @RequestMapping(value = "/getQuestion", method = RequestMethod.GET)
    public Result getQuestion(@RequestParam(required = false) String date){
        User user = getUser();

        if("-1".equals(date)){
            Date now = new Date();
            date = DateTimeUtils.getToday();
        }

        QuestForm question = dailyQuestionConfigurationService.getQuestion(date, user.getId());

        // 是否显示答案信息，默认为否
        String isShowAnswer = "0";

        // 获取设置相关信息
        DailyQuestionConfiguration config = dailyQuestionConfigurationService.getSetInUse();

        if(config != null){
            // 有设置信息
            isShowAnswer = config.getIsShowAnswer();
        }

        return Result.ok().put("question", question).put("isShowAnswer", isShowAnswer);
    }

    /**
     * 保存每日一题答题记录
     * @param form
     * @return
     */
    @RequestMapping(value = "/saveAnswer", method = RequestMethod.POST)
    public Result saveAnswer(@RequestBody ThemeAnswerForm form){
        User user = getUser();
        form.setId(IdWorker.getIdStr());
        form.setCreateUser(user.getId());
        form.setCreateTime(new Date());

        dailyRecordService.mySave(form);

        return Result.ok().put("recordId", form.getId());
    }

    @SysLog("收藏题目")
    @RequestMapping(value = "/doCollect/{type}", method = RequestMethod.POST)
    public Result doCollect(@RequestBody CommonForm params, @PathVariable("type") Integer type){

        String id = params.getKey();// 题目ID
        String recordId = params.getValue();// 记录ID

        dailyRecordService.doCollect(id, recordId, type);

        return Result.ok();
    }
}
