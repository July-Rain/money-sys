package com.lawschool.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lawschool.beans.Collection;
import org.apache.ibatis.annotations.Param;

public interface CollectionDao extends BaseMapper<Collection> {

    boolean cancle(@Param("questionId") String questionId,
                   @Param("userId") String userId);

}