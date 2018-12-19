package com.lawschool.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.annotation.SysLog;
import com.lawschool.beans.PracticeConfiguration;
import com.lawschool.beans.PracticeConfiguration02;
import com.lawschool.beans.system.TopicTypeEntity;
import com.lawschool.form.DiyPracPaperForm;
import com.lawschool.form.QuestForm;
import com.lawschool.service.OrgService;
import com.lawschool.service.PracticeConfiguration02Service;
import com.lawschool.service.PracticeConfigurationService;
import com.lawschool.service.UserService;
import com.lawschool.service.system.TopicTypeService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.*;

/**
 * @Author liuhuan
 * @Descriptin 练习卷配置
 * @Time 2018/12/04
 */
@RestController
@RequestMapping("/pracConfiguration")
public class PracticeConfigurationController{

    @Autowired
    PracticeConfigurationService practiceConfigurationService;

    @Autowired
    PracticeConfiguration02Service practiceConfiguration02Service;

    @Autowired
    OrgService orgService;

    @Autowired
    UserService userService;

    @Autowired
    private TopicTypeService topicTypeService;

    /**
     * 查看配置数据
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    public Result lookInfo(@PathVariable(value = "id") String id){
        PracticeConfiguration practiceConfiguration = practiceConfigurationService.selectById(id);
        return Result.ok().put("data", practiceConfiguration);
    }

    /**
     * 删除信息
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public Result deletePracticeConfiguration(@PathVariable(value = "id") String id){
        practiceConfigurationService.deleteById(id);
        return Result.ok();
    }

    /**
     * 展示所有练习卷配置信息
     */
    @RequestMapping(value="/list",method = RequestMethod.POST)
    public Result list(){
        List<PracticeConfiguration> list = practiceConfigurationService.list();

        return Result.ok().put("list",list);
    }

    /**
     * 展示所有练习卷配置信息
     */
    @RequestMapping("/listAll")
    public Result selectListAll(){
        Result result = Result.ok();
        PageUtils list = practiceConfigurationService.listAll();
        return result.put("list",list);
    }

    /**
     * 保存主表配置，并保存主表里的从表配置到从表数据库(传入主表配置)
     */
    @RequestMapping(value = "/saveAndShow", method = RequestMethod.POST)
    public Result save(@RequestBody PracticeConfiguration practiceConfiguration){
        //保存主表配置
        practiceConfiguration.setId(IdWorker.getIdStr());
        practiceConfiguration.setCreateTime(new Date());
        practiceConfigurationService.insert(practiceConfiguration);
        //保存从表配置
        List<PracticeConfiguration02> listSon = practiceConfiguration.getList();//从表配置数据
        for(PracticeConfiguration02 config : listSon){
            PracticeConfiguration02 configSon = new PracticeConfiguration02();
            configSon.setId(IdWorker.getIdStr());
            configSon.setConfigurationId(practiceConfiguration.getId());//获取主表ID
            configSon.setSpecialKnowledgeId(config.getSpecialKnowledgeId());
            configSon.setPrimaryCount(config.getPrimaryCount());
            configSon.setIntermediateCount(config.getIntermediateCount());
            configSon.setSeniorCount(config.getSeniorCount());
            practiceConfiguration02Service.insertConfig(configSon);
        }
        return Result.ok().put("id",practiceConfiguration.getId());//把该条记录返回到前端--根据id查询展示试题
    }


    /**
     * 部门展示
     */
    /*@RequestMapping("/deptInfo")
    public Result showDept(HttpServletResponse response){
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        //根据当前用户权限查询
        User user = getUser();
        String orgCode = user.getOrgCode();
        List<Map<String,Object>> orgList = orgService.findUserByOrg(orgCode);

        return Result.ok().put("orglist",orgList);
    }*/

    /**
     * 生成卷名
     */
    @RequestMapping(value = "/createPracName",method = RequestMethod.GET)
    public Result createPracName(String prefix){
        Result paperName = practiceConfigurationService.createPaperName(prefix);
        return Result.ok().put("paperName",paperName);
    }

    /**
     * 展示法律类别
     */
    @RequestMapping(value="/listTopic",method = RequestMethod.GET)
    public Result listTopic(){
        List<TopicTypeEntity> listTopic = topicTypeService.selectList(new EntityWrapper<TopicTypeEntity>());
        return Result.ok().put("listTopic",listTopic);
    }

    /**
     * 更新练习卷配置参数
     */
    @SysLog("更新配置参数")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result update(@RequestBody PracticeConfiguration practiceConfiguration){
        practiceConfigurationService.updateById(practiceConfiguration);
        return Result.ok().put("id",practiceConfiguration.getId());
    }

    /**
     * 练习卷跳转
     */
    @RequestMapping(value = "/pracPaper",method = RequestMethod.GET)
    public ModelAndView paper(){
        ModelAndView mv = new ModelAndView("/practiceCen/pracPaper");
        return mv;
    }

    /**
     * 练习卷生成
     */
    @SysLog("练习卷生成")
    @RequestMapping(value = "/createPaper",method = RequestMethod.POST)
    public Result createPaper02(String id){

        List<QuestForm> practicePaper = practiceConfigurationService.createPracticePaper(id);
        return Result.ok().put("practicePaper",practicePaper);
    }
}
