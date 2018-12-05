package com.lawschool.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lawschool.beans.PracticePaper;

public interface PracticePaperMapper extends BaseMapper<PracticePaper> {
    int deleteByPrimaryKey(String id);

    //int insert(PracticePaper record);

    int insertSelective(PracticePaper record);

    PracticePaper selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PracticePaper record);

    int updateByPrimaryKey(PracticePaper record);
}