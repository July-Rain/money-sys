package com.lawschool.controller.practicecenter;

import com.lawschool.annotation.SysLog;
import com.lawschool.base.AbstractController;
import com.lawschool.base.Page;
import com.lawschool.beans.User;
import com.lawschool.beans.practicecenter.TaskExerciseConfigureEntity;
import com.lawschool.form.TaskConfigureForm;
import com.lawschool.service.practicecenter.TaskExerciseConfigureService;
import com.lawschool.util.RedisUtil;
import com.lawschool.util.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @Auther: Moon
 * @Date: 2018/12/29 14:07
 * @Description: 练习中心--练习任务配置--Controller
 */
@RestController
@RequestMapping("/taskConfigure")
public class TaskExerciseConfigureController extends AbstractController {

    @Autowired
    private TaskExerciseConfigureService service;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 分页列表
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(@RequestParam Map<String, Object> params){

        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");

        // 获取登录用户信息
        User user = getUser();

        // 初始化查询参数
        TaskExerciseConfigureEntity entity = new TaskExerciseConfigureEntity();
        entity.setCreateUser(user.getId());
        entity.setDelFlag(TaskExerciseConfigureEntity.DEL_NORMAL);
        if(params.get("source") != null){
            entity.setSource(Integer.parseInt(params.get("source").toString()));
        }

        if(params.get("name") != null){
            entity.setName(String.valueOf(params.get("name")));
        }

        try{
            if(params.get("kssj") != null && params.get("kssj") != ""){
                entity.setKssj(sim.parse(String.valueOf(params.get("kssj"))));
            }
            if(params.get("jssj") != null && params.get("jssj") != ""){
                entity.setJssj(sim.parse(String.valueOf(params.get("jssj"))));
            }
        } catch (Exception e){
            return Result.error("日期格式错误，请修正...");
        }

        Page<TaskExerciseConfigureEntity> page = service.findPage(
                new Page<TaskExerciseConfigureEntity>(params), entity
        );

        return Result.ok().put("page", page);
    }

    /**
     * 保存配置
     * @param form
     * @param source，来源 0个人、1部门
     * @return
     */
    @SysLog("保存练习配置")
    @RequestMapping(value = "/save/{source}", method = RequestMethod.POST)
    public Result save(@RequestBody TaskConfigureForm form,
                       @PathVariable("source") Integer source){

        User user = getUser();

        TaskExerciseConfigureEntity entity = new TaskExerciseConfigureEntity();

        entity.setName(form.getName());
        entity.setCreateUser(user.getId());
        entity.setSource(source);
        entity.setDifficulty(StringUtils.join(form.getDifficultys(), ","));
        entity.setClassify(StringUtils.join(form.getClassifys(), ","));
        entity.setType(StringUtils.join(form.getTypes(), ","));
        entity.setThemeId(StringUtils.join(form.getTopics(), ","));
        entity.setThemeName(form.getThemeName());
        entity.setCreateName(user.getUserName());
        entity.setId(form.getId());
        entity.setDeptNames(form.getDeptNames());
        entity.setUserNames(form.getUserNames());

        if(source == 0){
            // 个人设置
            entity.setUsers(user.getId());
            if(StringUtils.isBlank(form.getId())){
                entity.setNumbers(createNum("PS"));
            }

        } else {
            entity.setUsers(StringUtils.join(form.getUsers(), ","));
            entity.setDepts(StringUtils.join(form.getDepts(), ","));
            if(StringUtils.isBlank(form.getId())){
                entity.setNumbers(createNum("DT"));
            }
        }

        service.mySave(entity);

        return Result.ok();
    }

    /**
     * 删除，逻辑删除
     * @param id
     * @return
     */
    @SysLog("删除练习配置")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public Result delete(@PathVariable("id") String id){

        boolean result = service.logicDelete(id);
        if(result){
            return Result.ok().put("msg", "删除成功");
        } else {

            return Result.error("删除失败");
        }

    }

    /**
     * 获取单个设置信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public Result info(@PathVariable("id") String id){

        TaskExerciseConfigureEntity info = service.findOne(id);

        return Result.ok().put("info", info);
    }

    /**
     * 生成任务编号
     * @param prefix
     * @return
     */
    private String createNum(String prefix){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String key = prefix + sdf.format(date);
        String value = redisUtil.getNumber(key, 60*60*24, 4);

        return key + value;
    }
}
