package com.lawschool.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lawschool.beans.PracticePaper;
import com.lawschool.beans.PracticeRelevance;

public interface PracticeRelevanceMapper extends BaseMapper<PracticeRelevance> {
    int deleteByPrimaryKey(String id);

    //int insert(PracticeRelevance record);

    int insertSelective(PracticeRelevance record);

    PracticeRelevance selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PracticeRelevance record);

    int updateByPrimaryKey(PracticeRelevance record);
}