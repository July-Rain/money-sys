package com.lawschool.service.impl.diagnosis;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lawschool.beans.User;
import com.lawschool.beans.diagnosis.DiagnosisEntity;
import com.lawschool.beans.diagnosis.OrgDiagnosisEntity;
import com.lawschool.beans.diagnosis.StuDiagnosisEntity;
import com.lawschool.beans.law.CaseAnalysisEntity;
import com.lawschool.beans.law.TaskDesicEntity;
import com.lawschool.dao.diagnosis.StuDiagnosisDao;
import com.lawschool.service.UserService;
import com.lawschool.service.diagnosis.StuDiagnosisService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;
import com.lawschool.util.UtilValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: StuDiagnosisServiceImpl
 * Description: 学情统计impl
 * date: 2019-1-7 12:47
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
@Service
public class StuDiagnosisServiceImpl implements StuDiagnosisService {
    @Autowired
    private StuDiagnosisDao diagnosisDao;
    @Autowired
    private UserService userService;
    @Override
    public Result getDiaInfo(Map<String, String> param) {
        Result result= Result.ok();
        //返回学情数据
        result.put("data",diagnosisDao.getDiaInfo(param));

        //学习模块的各个个数统计
        result.put("stuCount",DiaStat(param));

        //学情统计数据封装
        List<Map<String,Object>> stuInfo = new ArrayList<>();
        //视频学习
        Map<String, Object> videoMap = new HashMap<String, Object>();
        param.put("stuFrom","videocen");
        videoMap.put("type","videocen");
        videoMap.put("name","视频中心");
        videoMap.put("countTime",countStuTime(param));
        videoMap.put("ratio",getRatio(param));
        stuInfo.add(videoMap);

        //学习任务
        Map<String, Object> learntaskMap = new HashMap<String, Object>();
        param.put("stuFrom","learntask");
        learntaskMap.put("type","learntask");
        learntaskMap.put("name","学习任务");
        learntaskMap.put("countTime",countStuTime(param));
        learntaskMap.put("ratio",getRatio(param));
        stuInfo.add(learntaskMap);


        //案例分析
        Map<String, Object> caseanaMap = new HashMap<String, Object>();
        param.put("stuFrom","caseana");
        caseanaMap.put("type","caseana");
        caseanaMap.put("name","案例分析");
        caseanaMap.put("countTime",countStuTime(param));
        caseanaMap.put("ratio",getRatio(param));
        stuInfo.add(caseanaMap);


        //音频学习
        Map<String, Object> audioMap = new HashMap<String, Object>();
        param.put("stuFrom","audiocen");
        audioMap.put("type","audiocen");
        audioMap.put("name","音频中心");
        audioMap.put("countTime",countStuTime(param));
        audioMap.put("ratio",getRatio(param));
        stuInfo.add(audioMap);

        result.put("stuInfo",stuInfo);


        return result;
    }

    @Override
    public BigDecimal countStuTime(Map<String, String> param) {
        BigDecimal countSec = diagnosisDao.countStuTime(param);
        if(UtilValidate.isEmpty(countSec)){
            countSec=new BigDecimal(0);
        }
        BigDecimal countH=countSec.divide(new BigDecimal(60*60),2,BigDecimal.ROUND_CEILING);
        return countH;
    }
    /**
     * @Author MengyuWu
     * @Description 获取打败多少人
     * @Date 15:54 2019-1-7
     * @Param [param]
     * @return java.lang.String
     **/
    
    public String getRatio(Map<String, String> param){
        int rank=getRankNo(param);
        int userCount=userService.selectCount(new EntityWrapper<User>());
        double ratio=1;
        if(userCount>=rank&&userCount!=0){
            int others=userCount-rank;
            ratio=others*1.0/userCount;
 //           ratio= 100/others;//考虑被除数为0
        }
        //保留两位小数
        return  String.format("%.2f", ratio*100)+"%";
    }
    @Override
    public int getRankNo(Map<String, String> param) {
        return diagnosisDao.getRankNo(param);
    }

    @Override
    public List<DiagnosisEntity> DiaStat(Map<String, String> param) {
        return diagnosisDao.DiaStat(param);
    }

    @Override
    public List<OrgDiagnosisEntity> orgDiaStat(Map<String, String> param) {
        return diagnosisDao.orgDiaStat(param);
    }

    @Override
    public PageUtils allStuStatByOrgId(Map<String, String> param) {
        Page<StuDiagnosisEntity> page = new Page<StuDiagnosisEntity>(Integer.parseInt(param.get("currPage")),Integer.parseInt(param.get("pageSize")));
        page.setRecords(diagnosisDao.allStuStatByOrgId(page,param));
        int userNum =diagnosisDao.countUser(param.get("orgId"));
        page.setTotal(userNum);
        return new PageUtils(page);
    }
}
