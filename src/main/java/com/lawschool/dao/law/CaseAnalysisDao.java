package com.lawschool.dao.law;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lawschool.beans.StuMedia;
import com.lawschool.beans.law.CaseAnalysisEntity;
import com.lawschool.beans.law.TaskDesicEntity;

import java.util.List;

/**
 * @Author MengyuWu
 * @Description 案例分析的dao
 * @Date 15:17 2018-12-22
 * @Param 
 * @return 
 **/

public interface CaseAnalysisDao extends BaseMapper<CaseAnalysisEntity> {
    /**
     * @Author MengyuWu
     * @Description 查询学习任务下的列表
     * @Date 10:47 2018-12-27
     * @Param [page, desicEntity]
     * @return java.util.List<com.lawschool.beans.law.CaseAnalysisEntity>
     **/
    


    List<CaseAnalysisEntity> listCaseAnaByTask(Page page, TaskDesicEntity desicEntity);
    /**
     * @Author MengyuWu
     * @Description 统计条数
     * @Date 10:47 2018-12-27
     * @Param [desicEntity]
     * @return int
     **/
    

    int countListCaseAnaByTask( TaskDesicEntity desicEntity);
}