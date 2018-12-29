package com.lawschool.controller.learn;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.annotation.SysLog;
import com.lawschool.base.AbstractController;
import com.lawschool.base.Page;
import com.lawschool.beans.User;
import com.lawschool.beans.law.TaskDesicEntity;
import com.lawschool.beans.learn.LearnTasksEntity;
import com.lawschool.service.law.TaskDesicService;
import com.lawschool.service.learn.LearnTasksService;
import com.lawschool.service.learn.StuRecordService;
import com.lawschool.util.GetUUID;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
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

    @Autowired
    private TaskDesicService desicService;

    @Autowired
    private StuRecordService recordService;
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
    /**
     * @Author MengyuWu
     * @Description 继续学习页面
     * @Date 16:34 2018-12-26
     * @Param [id]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    
    @RequestMapping(value = "/continueStudy", method = RequestMethod.GET)
    public ModelAndView answer(@RequestParam String id){
        ModelAndView mv = new ModelAndView("/learnCen/continuestudy");

        mv.addObject("id", id);
        return mv;
    }
    /**
     * @Author MengyuWu
     * @Description 根据学习任务的id获取学习任务数据
     * @Date 16:36 2018-12-26
     * @Param [id]
     * @return com.lawschool.util.Result
     **/
    
    @RequestMapping("/zTree")
    public Result getZtree(@RequestParam String id){
        List<TaskDesicEntity> desicEntities = desicService.selectList(
                new EntityWrapper<TaskDesicEntity>()
        .eq("task_id",id));
        return  Result.ok().put("data",desicEntities);
    }

    /**
     * @Author MengyuWu
     * @Description 继续学习页面  查询节点下具体的数据
     * @Date 16:53 2018-12-26
     * @Param [params]
     * @return com.lawschool.util.Result
     **/
    
    @RequestMapping("/allInfo")
    public Result allInfo(@RequestParam Map<String, Object> params){

        PageUtils page = tasksService.queryContentByTask(params);
        return Result.ok().put("page", page);
    }

    @RequestMapping("/insertRecord")
    public Result updateCount(String stuId,String stuType,String stuFrom,String taskId){
        //获取当前登陆人
        User user=  getUser();
        //插入学习记录
        recordService.insertStuRecord(user,stuId,stuType,stuFrom,taskId);
        return Result.ok();
    }
}
