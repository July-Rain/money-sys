package com.lawschool.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.lawschool.base.AbstractDao;
import com.lawschool.beans.StuMedia;
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


}