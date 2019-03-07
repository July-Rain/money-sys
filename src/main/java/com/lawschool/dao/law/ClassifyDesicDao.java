package com.lawschool.dao.law;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lawschool.beans.law.ClassifyDesicEntity;
import com.lawschool.beans.law.TaskDesicEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author MengyuWu
 * @Description 法律法规dao
 * @Date 11:22 2018-12-25
 * @Param 
 * @return 
 **/

public interface ClassifyDesicDao extends BaseMapper<ClassifyDesicEntity> {
    /**
     * @Author MengyuWu
     * @Description 查询学习任务下的列表
     * @Date 10:14 2018-12-27
     * @Param [page, desicEntity]
     * @return java.util.List<com.lawschool.beans.law.ClassifyDesicEntity>
     **/
    
    List<ClassifyDesicEntity> queryListByTask(Page page,TaskDesicEntity desicEntity);
    /**
     * @Author MengyuWu
     * @Description 统计条数
     * @Date 10:14 2018-12-27
     * @Param [desicEntity]
     * @return int
     **/
    
    int countListByTask(TaskDesicEntity desicEntity);
    /**
     * @Author MengyuWu
     * @Description 根据当前用户  查询对应的学习内容信息（管理员维护学习任务时）
     * @Date 10:14 2018-12-27
     * @Param [page, desicEntity]
     * @return java.util.List<com.lawschool.beans.law.ClassifyDesicEntity>
     **/

    List<TaskDesicEntity> queryListByUser(Page page,TaskDesicEntity desicEntity);
    /**
     * @Author MengyuWu
     * @Description 根据当前用户  查询对应的学习内容信息（管理员维护学习任务时）
     * @Date 10:14 2018-12-27
     * @Param [desicEntity]
     * @return int
     **/

    int countListByUser(TaskDesicEntity desicEntity);
}