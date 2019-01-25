package com.lawschool.controller.diagnosis;

import com.lawschool.base.AbstractController;
import com.lawschool.beans.User;
import com.lawschool.beans.diagnosis.DiagnosisEntity;
import com.lawschool.beans.diagnosis.OrgDiagnosisEntity;
import com.lawschool.service.diagnosis.ExamDiagnosisService;
import com.lawschool.service.diagnosis.StuDiagnosisService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ExamDiagnosisController
 * Description: TODO
 * date: 2019-1-18 10:07
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
@RestController
@RequestMapping("examstat")
public class ExamDiagnosisController extends AbstractController {
    @Autowired
    private ExamDiagnosisService diagnosisService;

    @RequestMapping("getDiaInfo")
    public Result getDiaInfo(Map<String,String> params){
        //获取学情分析echarts中的数据
        User user = getUser();
        params.put("userId",user.getId());
        List<DiagnosisEntity> diagnosisEntities = diagnosisService.getDiaInfo(params);
        return  Result.ok().put("data",diagnosisEntities);
    }

    @RequestMapping("DiaStat")
    public Result DiaStat(Map<String,String> params){
        //按照分数统计
        User user = getUser();
        params.put("userId",user.getId());
        List<DiagnosisEntity> diaList = diagnosisService.DiaStat(params);
        return  Result.ok().put("data",diaList);
    }
    @RequestMapping("orgExamStat")
    public Result orgExamStat(Map<String,String> params){
        //获取部门统计数据
        User user = getUser();
        params.put("userId",user.getId());
        params.put("orgId",user.getOrgId());
        List<OrgDiagnosisEntity> diaList = diagnosisService.orgDiaStat(params);
        return  Result.ok().put("data",diaList);
    }

    @RequestMapping("getAllExamDiagnosis")
    public Result getAllExamDiagnosis(@RequestParam Map<String,String> params){
        User user = getUser();
        params.put("orgId",user.getOrgId());
        PageUtils page = diagnosisService.allExamStatByOrgId(params);
        return  Result.ok().put("page",page);
    }
}
