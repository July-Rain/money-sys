package com.lawschool.controller;

import com.lawschool.base.AbstractController;
import com.lawschool.beans.PracticeConfiguration;
import com.lawschool.beans.User;
import com.lawschool.service.OrgService;
import com.lawschool.service.PracticeConfigurationService;
import com.lawschool.service.UserService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Author liuhuan
 * @Descriptin 练习卷配置
 * @Time 2018/12/04
 */
@Controller
@RequestMapping("/pracConfiguration")
public class PracticeConfigurationController extends AbstractController {

    @Autowired
    PracticeConfigurationService practiceConfigurationService;

    @Autowired
    OrgService orgService;

    @Autowired
    UserService userService;

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
     * 保存编辑的练习卷配置
     */
    @RequestMapping("/save")
    public Result save(PracticeConfiguration practiceConfiguration){
        practiceConfigurationService.save(practiceConfiguration);
        return Result.ok();
    }

    /**
     * 部门展示
     */
    @RequestMapping("/deptInfo")
    public Result showDept(HttpServletResponse response){
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        //根据当前用户权限查询
        User user = getUser();
        String orgCode = user.getOrgCode();
        List<Map<String,Object>> orgList = orgService.findUserByOrg(orgCode);

        return Result.ok().put("orglist",orgList);
    }

    /**
     * 生成卷名
     */
    @RequestMapping("/createPracName")
    public Result createPracName(@RequestParam String prefix){

        Result paperName = practiceConfigurationService.createPaperName(prefix);
        return Result.ok().put("paperName",paperName);
    }

    /**
     * 生成练习卷
     * @param paramsList,user
     * @return
     */
    public Result createPracPaper(@RequestParam List<PracticeConfiguration>paramsList,User user){

        Result paper = practiceConfigurationService.createPracticePaper(paramsList,user);
        return Result.ok().put("paper",paper);
    }

    /**
     * 信息
     * @param id
     */
    @RequestMapping("/info/{ids}")
    public Result info(String id){
        PracticeConfiguration config = (PracticeConfiguration) practiceConfigurationService.selectById(id);
        return Result.ok().put("pracConfig",config);
    }
}
