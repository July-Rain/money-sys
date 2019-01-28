package com.lawschool.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.lawschool.base.AbstractDao;
import com.lawschool.beans.StuMedia;
import com.lawschool.beans.law.ClassifyDesicEntity;
import com.lawschool.beans.law.TaskDesicEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface StuMediaDao extends AbstractDao<StuMedia> {
    //int insert(StuMedia record);

    int insertSelective(StuMedia record);

    //我的收藏-重点课程-zjw
    List<StuMedia> listMyCollection(Map<String,Object> param);

    /*@Select("select * from LAW_STU_MEDIA")
    List<StuMedia> selectListPage(Page<StuMedia> page);*/

    int cntMyCollection(Map<String,Object> param);

    /**
     * @Author MengyuWu
     * @Description 查询学习任务下的列表
     * @Date 10:28 2018-12-27
     * @Param [page, stuMedia]
     * @return java.util.List<com.lawschool.beans.StuMedia>
     **/
    

    List<StuMedia> listStuByTask(Page page, TaskDesicEntity desicEntity);
    /**
     * @Author MengyuWu
     * @Description 统计条数
     * @Date 10:14 2018-12-27
     * @Param [stuMedia]
     * @return int
     **/

    int countListStuByTask( TaskDesicEntity desicEntity);

    boolean updateStatus(@Param("id") String id, @Param("status") String status);
}