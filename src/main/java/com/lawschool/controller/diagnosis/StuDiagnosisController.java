package com.lawschool.controller.diagnosis;

import com.lawschool.base.AbstractController;
import com.lawschool.base.Page;
import com.lawschool.beans.User;
import com.lawschool.beans.diagnosis.DiagnosisEntity;
import com.lawschool.beans.diagnosis.OrgDiagnosisEntity;
import com.lawschool.beans.diagnosis.StuDiagnosisEntity;
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
 * ClassName: StuDiagnosisController
 * Description: 学情统计controller
 * date: 2019-1-7 13:37
 *
 * @author MengyuWu
 * @since JDK 1.8
 */

@RestController
@RequestMapping("/diagnosis")
public class StuDiagnosisController extends AbstractController {
    @Autowired
    private StuDiagnosisService diagnosisService;

    @RequestMapping("getStuDiagnosis")
    public Result getStuDiagnosis(@RequestParam Map<String,String> params){
        //获取学情分析echarts中的数据
        User user = getUser();
        params.put("userId",user.getId());
        return  diagnosisService.getDiaInfo(params);
    }
    @RequestMapping("getAllStuDiagnosis")
    public Result getAllStuDiagnosis(@RequestParam Map<String,String> params){
        User user = getUser();
        params.put("orgId",user.getOrgId());
        PageUtils page = diagnosisService.allStuStatByOrgId(params);
        return  Result.ok().put("page",page);
    }

    @RequestMapping("getOrgDiaStat")
    public Result getOrgDiaStat(@RequestParam  Map<String,String> params){
        //获取部门统计数据
        User user = getUser();
        params.put("userId",user.getId());
        params.put("orgid",user.getOrgId());
        List<OrgDiagnosisEntity> orgDiaList = diagnosisService.orgDiaStat(params);
        return  Result.ok().put("data",orgDiaList);
    }
}
