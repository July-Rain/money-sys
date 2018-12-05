package com.lawschool.controller;

import com.lawschool.beans.PracticeConfiguration;
import com.lawschool.beans.TestQuestions;
import com.lawschool.beans.User;
import com.lawschool.service.OrgService;
import com.lawschool.service.PracticeConfigurationService;
import com.lawschool.service.UserService;
import com.lawschool.util.GetUUID;
import com.lawschool.util.Result;
import com.lawschool.util.UtilValidate;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Author liuhuan
 * @Descriptin 练习卷配置
 * @Time 2018/12/04
 */
@Controller
@RequestMapping("/pracConfiguration")
public class PracticeConfigurationController extends  AbstractController{

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
        List<PracticeConfiguration> list = practiceConfigurationService.listAll();
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
    public Result showDept(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        Result result = Result.ok();

        //根据当前用户权限查询
        User user = getUser();
        String orgCode = user.getOrgCode();
        List<Map<String,Object>> orgList = orgService.findUserByOrg(orgCode);
        /*String jsonStr = JSONArray.toJSONString(orgList);
        PrintWriter out = response.getWriter();
        out.print(jsonStr);
        out.flush();
        out.close();*/
        return result.put("orglist",orgList);
    }

    /**
     * 生成卷名
     */
    @RequestMapping("/createPracName")
    public Result createPracName(@Param(value = "prefix") String prefix){
        String sysNum = GetUUID.getRom(5);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd");
        String date = sdf.format(new Date());

        return Result.ok().put("pracName",sysNum+date+prefix);
    }

    /**
     * 自定义练习配置
     */
    @RequestMapping("/createPracPaper")
    public Result createPaper(@PathVariable(value="PracticeConfiguration")PracticeConfiguration params){
        Result result = Result.ok();
        Map map = new HashMap();
        String questionType = params.getQuestionType();//题目类型
        String specialKnowledgeId = params.getSpecialKnowledgeId();//知识点ID

        Integer countEasy = params.getPrimaryCount();
        Integer countMid = params.getIntermediateCount();
        Integer countHard = params.getSeniorCount();
        if(UtilValidate.isNotEmpty(countEasy)||!countEasy.equals(0)){
            map.put("countEasy",countEasy);
            map.put("questionDifficulty1",1);
        }
        if(UtilValidate.isNotEmpty(countMid)||!countMid.equals(0)){
            map.put("countMid",countMid);
            map.put("questionDifficulty2",2);
        }
        if(UtilValidate.isNotEmpty(countHard)||!countHard.equals(0)){
            map.put("countHard",countHard);
            map.put("questionDifficulty3",3);
        }

        //查询专项知识点ID下的所有题目
        List<TestQuestions> list = practiceConfigurationService.selectByKnowledgeId(map);

        return Result.ok();
    }


}
