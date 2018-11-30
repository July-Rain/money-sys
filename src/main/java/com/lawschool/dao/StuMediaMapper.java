package com.lawschool.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lawschool.beans.Collection;
import com.lawschool.beans.StuMedia;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface StuMediaMapper extends BaseMapper<StuMedia> {
    //int insert(StuMedia record);

    int insertSelective(StuMedia record);

    List<StuMedia> listMyCollection(Map<String,Object> param);

}