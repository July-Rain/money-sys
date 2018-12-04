package com.lawschool.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lawschool.beans.Answer;

public interface AnswerMapper extends BaseMapper<Answer> {
    //int insert(Answer record);

    int insertSelective(Answer record);
}