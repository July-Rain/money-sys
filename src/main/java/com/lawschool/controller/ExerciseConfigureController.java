package com.lawschool.controller;

import com.lawschool.annotation.SysLog;
import com.lawschool.base.AbstractController;
import com.lawschool.base.Page;
import com.lawschool.beans.ExerciseConfigureEntity;
import com.lawschool.beans.User;
import com.lawschool.form.QuestForm;
import com.lawschool.service.ExerciseConfigureService;
import com.lawschool.util.RedisUtil;
import com.lawschool.util.Result;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Moon
 * @Date: 2018/12/20 15:22
 * @Description:
 */
@RestController
@RequestMapping("/exercise/configure")
public class ExerciseConfigureController extends AbstractController{
    @Autowired
    private ExerciseConfigureService exerciseConfigureService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 分页列表数据
     * @param params
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(@RequestParam Map<String, Object> params){
        User user = getUser();

        // 查询当前用户创建的数据
        ExerciseConfigureEntity entity = new ExerciseConfigureEntity();
        entity.setCreateUser(user.getId());
        entity.setDelFlag(0);

        Page<ExerciseConfigureEntity> page = exerciseConfigureService.findPage(
            new Page<ExerciseConfigureEntity>(params), entity
        );

        return Result.ok().put("page", page);
    }

    /**
     * 新增练习配置,并生成练习卷
     * @param entity
     * @return
     */
    @SysLog("新增练习配置,并生成练习卷")
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestBody ExerciseConfigureEntity entity){
        User user = getUser();

        entity.preInsert(user.getId());
        entity.setDelFlag(0);
        entity.setUserName(user.getUserName());

        if("0".equals(entity.getRangeType())){
            // 个人
            entity.setUsers(user.getId());
            entity.setDepts(null);
        } else if("1".equals(entity.getRangeType())){
            // 公开
            entity.setUsers("-1");
            entity.setDepts("-1");
        } else if("2".equals(entity.getRangeType())){
            // 部门
            if(CollectionUtils.isEmpty(entity.getDeptIds()) && CollectionUtils.isEmpty(entity.getUserIds())){
                return Result.error("请设置使用部门或使用人员信息...");
            }

            if(CollectionUtils.isNotEmpty(entity.getDeptIds())){
                entity.setDepts(String.join(",", entity.getDeptIds()));
            }

            if(CollectionUtils.isNotEmpty(entity.getUserIds())){
                entity.setUsers(String.join(",", entity.getUserIds()));
            }
        }

        exerciseConfigureService.saveConfigure(entity);

        return Result.ok().put("id", entity.getId());
    }

    /**
     * 展示试卷信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
    public Result showPaper(@PathVariable("id") String id){

        List<QuestForm> formList = exerciseConfigureService.showPaper(id);

        return Result.ok().put("list", formList);
    }

    /**
     * 生成练习卷名称
     * @param prefix
     * @return
     */
    @SysLog("生成练习卷名称")
    @RequestMapping(value = "/createName", method = RequestMethod.GET)
    public Result createName(@RequestParam String prefix){

        Date date = new Date();
        SimpleDateFormat sim = new SimpleDateFormat("yyyyMMdd");
        String time = sim.format(date);
        String name = prefix + "" + time;

        String key = "NUMBER" + time;
        String value = redisUtil.getNumber(key, 24*60*60, 4);

        name += value;

        return Result.ok().put("name", name);
    }

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    @SysLog("删除练习配置")
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public Result delete(@PathVariable("id") String id){

        int result = exerciseConfigureService.updateDelflag(id);
        if(result != 1){
            throw new RuntimeException("删除失败");
        }
        return Result.ok();
    }

}
