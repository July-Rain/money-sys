package com.lawschool.dao.diagnosis;

import com.baomidou.mybatisplus.plugins.Page;
import com.lawschool.beans.diagnosis.DiagnosisEntity;
import com.lawschool.beans.diagnosis.OrgDiagnosisEntity;
import com.lawschool.beans.diagnosis.StuDiagnosisEntity;
import com.lawschool.util.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ExamDiagnosisDao
 * Description: 考情统计
 * date: 2019-1-18 9:48
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
public interface ExamDiagnosisDao {

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
    /**
     * @Author MengyuWu
     * @Description 部门下的人员统计数据
     * @Date 16:39 2019-1-24
     * @Param [param]
     * @return com.lawschool.util.PageUtils
     **/

    List<OrgDiagnosisEntity> allExamStatByOrgId(Page page, Map<String,String> param);

}
