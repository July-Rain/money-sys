package com.lawschool.controller;

import com.lawschool.annotation.SysLog;
import com.lawschool.base.AbstractController;
import com.lawschool.base.Page;
import com.lawschool.beans.DailyQuestionConfiguration;
import com.lawschool.beans.TestQuestions;
import com.lawschool.beans.User;
import com.lawschool.beans.practicecenter.TaskExerciseEntity;
import com.lawschool.form.DailyForm;
import com.lawschool.form.QuestForm;
import com.lawschool.service.AnswerService;
import com.lawschool.service.DailyQuestionConfigurationService;
import com.lawschool.util.Result;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 每日一题管理
 * @author liuhuan
 */
@RestController
@RequestMapping("/dailyQuestion")
public class DailyQuestionConfigurationController extends AbstractController {
    @Autowired
    private DailyQuestionConfigurationService dailyQuestionConfigurationService;

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
            String currentId = dailyQuestionConfigurationService.getSettingsInUse();
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

    /**
     * 修改配置
     */
    @SysLog("修改配置")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result updateDailyConfig(@RequestBody DailyQuestionConfiguration dailyQuestionConfiguration){
      int i=  dailyQuestionConfigurationService.updateByDailyConfig(dailyQuestionConfiguration);
        if(i==0)
        {
            return Result.ok().put("code",1).put("msg","修改失败，输入的时间区间和已添加的时间段有交集");
        }
        return Result.ok().put("id",dailyQuestionConfiguration.getId());
    }

    /**
     * 每日一题题目展示
     */
    @RequestMapping(value = "/showDailyTest",method = RequestMethod.POST)
    public Result showTest(){
        Result r=  dailyQuestionConfigurationService.dailyTestCreate();
        return r;
    }
    /**
     * 每日一题题目展示
     */
    @RequestMapping(value = "/newshowDailyTest",method = RequestMethod.POST)
    public Result newshowDailyTest(){
        Result r=  dailyQuestionConfigurationService.newDailyTestCreate();
        return r;
    }

    //答过的题目入库保存
    @SysLog("答过的题目入库保存")
    @RequestMapping("/saveQuestion")
    public void saveQuestion(@RequestBody TestQuestions testQuestions, String myanswer){

        dailyQuestionConfigurationService.saveQuestion(testQuestions,myanswer);

    }

    //保存分数
    @SysLog("保存分数")
    @RequestMapping("/recordScore")
    public Result recordScore(String sorce){
        User u = (User) SecurityUtils.getSubject().getPrincipal();
        dailyQuestionConfigurationService.recordScore(u,sorce);
        return Result.ok();
    }

}
