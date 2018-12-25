package com.lawschool.controller.learn;

import com.lawschool.annotation.SysLog;
import com.lawschool.base.AbstractController;
import com.lawschool.base.Page;
import com.lawschool.beans.User;
import com.lawschool.beans.learn.LearnTasksEntity;
import com.lawschool.service.learn.LearnTasksService;
import com.lawschool.util.GetUUID;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * ClassName: LearnTasksController
 * Description: 学习任务controller
 * date: 2018-12-18 16:19
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/learntasks")
public class LearnTasksController extends AbstractController {
    @Autowired
    private LearnTasksService tasksService;
    /**
     * @Author MengyuWu
     * @Description 查询列表
     * @Date 16:23 2018-12-18
     * @Param [params]
     * @return com.lawschool.util.Result
     **/
    

    @RequestMapping("/list")
    public Result list(@RequestParam Map<String, Object> params){
        PageUtils page = tasksService.queryPage(params);
        return Result.ok().put("page", page);
    }

    /**
     * @Author MengyuWu
     * @Description 查看详情
     * @Date 16:23 2018-12-18
     * @Param [id]
     * @return com.lawschool.util.Result
     **/
    

    @RequestMapping("/info")
    public Result info(String id){
        LearnTasksEntity tasksEntity = tasksService.selectById(id);
        return Result.ok().put("data", tasksEntity);
    }
    /**
     * @Author MengyuWu
     * @Description 添加学习任务
     * @Date 16:24 2018-12-18
     * @Param [tasksEntity]
     * @return com.lawschool.util.Result
     **/
    
    @SysLog("添加学习任务")
    @RequestMapping("/insert")
    public Result insert(@RequestBody LearnTasksEntity tasksEntity){
        User user =getUser();
        tasksEntity.setId(GetUUID.getUUIDs("SC"));
        tasksService.insertLearnTask(tasksEntity,user);
        return Result.ok().put("id",tasksEntity.getId());
    }

    /**
     * @Author MengyuWu
     * @Description 更新学习任务
     * @Date 16:25 2018-12-18
     * @Param [config]
     * @return com.lawschool.util.Result
     **/
    
    @SysLog("更新学习任务")
    @RequestMapping("/update")
    public Result update(@RequestBody LearnTasksEntity tasksEntity){
        User user =getUser();
        tasksService.updateLearnTask(tasksEntity,user);
        return Result.ok().put("id",tasksEntity.getId());
    }

    /**
     * @Author MengyuWu
     * @Description 删除学习任务
     * @Date 16:26 2018-12-18
     * @Param [ids]
     * @return com.lawschool.util.Result
     **/
    

    @SysLog("删除学习任务")
    @RequestMapping("/delete")
    public Result delete(@RequestBody String[] ids){
        tasksService.deleteBatchIds(Arrays.asList(ids));
        return Result.ok();
    }
}
