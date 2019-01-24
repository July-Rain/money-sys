package com.lawschool.service.diagnosis;

import com.lawschool.beans.diagnosis.DiagnosisEntity;
import com.lawschool.beans.diagnosis.OrgDiagnosisEntity;
import com.lawschool.beans.diagnosis.StuDiagnosisEntity;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * InterfaceName: StuDiagnosisService
 * Description: 学情统计service
 * date: 2019-1-7 12:47
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
public interface StuDiagnosisService {
    /**
     * @Author MengyuWu
     * @Description 获取学情统计
     * @Date 12:58 2019-1-7
     * @Param [param]
     * @return java.util.Map<java.lang.String,java.lang.Integer>
     **/

    Result getDiaInfo(Map<String,String> param);

    /**
     * @Author MengyuWu
     * @Description 统计学习时长
     * @Date 14:45 2019-1-7
     * @Param [param]
     * @return BigDecimal
     **/

    BigDecimal countStuTime(Map<String,String> param);
    /**
     * @Author MengyuWu
     * @Description 获取排名
     * @Date 15:24 2019-1-7
     * @Param [param]
     * @return int
     **/

    int getRankNo(Map<String,String> param);

    /**
     * @Author MengyuWu
     * @Description 统计学习各个模块的个数
     * @Date 14:53 2019-1-8
     * @Param 
     * @return 
     **/

    List<DiagnosisEntity> DiaStat(Map<String,String> param);

    /**
     * @Author MengyuWu
     * @Description 部门统计数据
     * @Date 17:07 2019-1-9
     * @Param [param]
     * @return java.util.List<com.lawschool.beans.diagnosis.OrgDiagnosisEntity>
     **/

    List<OrgDiagnosisEntity> orgDiaStat(Map<String,String> param);

    /**
     * @Author MengyuWu
     * @Description 部门下人员统计数据
     * @Date 14:44 2019-1-24
     * @Param [param]
     * @return java.util.List<com.lawschool.beans.diagnosis.StuDiagnosisEntity>
     **/

    PageUtils allStuStatByOrgId(Map<String,String> param);

}
