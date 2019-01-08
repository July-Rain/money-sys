package com.lawschool.dao.diagnosis;

import com.lawschool.beans.diagnosis.DiagnosisEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * InterfaceName: StuDiagnosisDao
 * Description: 学情统计
 * date: 2019-1-7 12:43
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
public interface StuDiagnosisDao {
    /**
     * @Author MengyuWu
     * @Description 获取学情统计
     * @Date 12:58 2019-1-7
     * @Param [param]
     * @return java.util.Map<java.lang.String,java.lang.Integer>
     **/

    List<DiagnosisEntity> getDiaInfo(Map<String,String> param);

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
     * @Description 获取学习各个模块的个数统计
     * @Date 14:36 2019-1-8
     * @Param [param]
     * @return java.util.List<com.lawschool.beans.diagnosis.DiagnosisEntity>
     **/
    

    List<DiagnosisEntity> DiaStat(Map<String,String> param);
}
