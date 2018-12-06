package com.lawschool.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lawschool.beans.Collection;
import com.lawschool.beans.StuMedia;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface StuMediaDao extends BaseMapper<StuMedia> {
    //int insert(StuMedia record);

    int insertSelective(StuMedia record);

    //我的收藏-重点课程-zjw
    List<StuMedia> listMyCollection(Map<String,Object> param);

    int cntMyCollection(Map<String,Object> param);

}