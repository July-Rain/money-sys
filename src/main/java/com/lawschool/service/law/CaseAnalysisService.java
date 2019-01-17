package com.lawschool.service.law;

import com.baomidou.mybatisplus.service.IService;
import com.lawschool.beans.User;
import com.lawschool.beans.law.CaseAnalysisEntity;
import com.lawschool.util.PageUtils;

import java.util.Map;

/**
 * InterfaceName: CaseAnalysisService
 * Description: 案例分析的Service
 * date: 2018-12-22 14:54
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
public interface CaseAnalysisService extends IService<CaseAnalysisEntity> {
    /**
     * @Author MengyuWu
     * @Description 查询分页
     * @Date 14:56 2018-12-22
     * @Param [param]
     * @return com.lawschool.util.PageUtils
     **/
    
    PageUtils queryPage(Map<String,Object> param,User user);

    /**
     * @Author MengyuWu
     * @Description  插入案例分析
     * @Date 15:07 2018-12-22
     * @Param [analysisEntity, user]
     * @return void
     **/
    

    void insertCaseAnaly(CaseAnalysisEntity analysisEntity,User user);

    /**
     * @Author MengyuWu
     * @Description 根据id查看详情页面 包括权限内数据
     * @Date 15:07 2018-12-22
     * @Param [id]
     * @return com.lawschool.beans.law.CaseAnalysisEntity
     **/

    CaseAnalysisEntity selectCaseAnalyInfo(String id);


    /**
     * @Author MengyuWu
     * @Description 案例分析修改
     * @Date 15:07 2018-12-22
     * @Param [analysisEntity, user]
     * @return void
     **/
    


    void updateCaseAnaly(CaseAnalysisEntity analysisEntity, User user);


    /**
     * @Author MengyuWu
     * @Description 根据任务信息查询相关的图文 视频 音频信息
     * @Date 10:19 2018-12-27
     * @Param [params]
     * @return com.lawschool.util.PageUtils
     **/

    PageUtils listCaseAnaByTask(Map<String, Object> params);
    /**
     * @Author MengyuWu
     * @Description 根据id更新对应的数据
     * @Date 10:32 2018-12-28
     * @Param [accId]
     * @return int
     **/

    int updateCount(String stuId);
}
