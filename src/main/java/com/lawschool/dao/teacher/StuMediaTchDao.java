package com.lawschool.dao.teacher;

import com.baomidou.mybatisplus.plugins.Page;
import com.lawschool.base.AbstractDao;
import com.lawschool.beans.law.TaskDesicEntity;
import com.lawschool.beans.teacher.StuMediaTch;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface StuMediaTchDao extends AbstractDao<StuMediaTch> {
    //int insert(StuMediaTch record);

    int insertSelective(StuMediaTch record);

    //我的收藏-重点课程-zjw
    List<StuMediaTch> listMyCollection(Map<String, Object> param);

    /*@Select("select * from LAW_STU_MEDIA")
    List<StuMediaTch> selectListPage(Page<StuMediaTch> page);*/

    int cntMyCollection(Map<String, Object> param);

    /**
     * @Author MengyuWu
     * @Description 查询学习任务下的列表
     * @Date 10:28 2018-12-27
     * @Param [page, stuMedia]
     * @return java.util.List<com.lawschool.beans.StuMediaTch>
     **/


    List<StuMediaTch> listStuByTask(Page page, TaskDesicEntity desicEntity);
    /**
     * @Author MengyuWu
     * @Description 统计条数
     * @Date 10:14 2018-12-27
     * @Param [stuMedia]
     * @return int
     **/

    int countListStuByTask(TaskDesicEntity desicEntity);

    boolean updateStatus(@Param("id") String id, @Param("status") String status);
}