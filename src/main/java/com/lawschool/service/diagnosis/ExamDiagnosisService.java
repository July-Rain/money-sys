package com.lawschool.service.diagnosis;

import com.lawschool.beans.diagnosis.DiagnosisEntity;
import com.lawschool.beans.diagnosis.OrgDiagnosisEntity;

import java.util.List;
import java.util.Map;

/**
 * InterfaceName: ExamDiagnosisService
 * Description: TODO
 * date: 2019-1-18 10:05
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
public interface ExamDiagnosisService {
    /**
     * @Author MengyuWu
     * @Description 按照考试类型统计
     * @Date 9:49 2019-1-18
     * @Param [param]
     * @return java.util.List<com.lawschool.beans.diagnosis.DiagnosisEntity>
     **/

    List<DiagnosisEntity> getDiaInfo(Map<String,String> param);

    /**
     * @Author MengyuWu
     * @Description 按照分数统计
     * @Date 10:03 2019-1-18
     * @Param [param]
     * @return java.util.List<com.lawschool.beans.diagnosis.DiagnosisEntity>
     **/

    List<DiagnosisEntity> DiaStat(Map<String,String> param);

    /**
     * @Author MengyuWu
     * @Description 部门统计数据
     * @Date 16:54 2019-1-18
     * @Param [param]
     * @return java.util.List<com.lawschool.beans.diagnosis.OrgDiagnosisEntity>
     **/

    List<OrgDiagnosisEntity> orgDiaStat(Map<String,String> param);
}
