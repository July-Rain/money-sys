package com.lawschool.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lawschool.beans.Collection;
import org.apache.ibatis.annotations.Param;

public interface CollectionDao extends BaseMapper<Collection> {

    boolean cancle(@Param("questionId") String questionId,
                   @Param("userId") String userId);

    /**
     * 获取收藏信息
     * @param type 收藏类型：10课件收藏、20重点试题收藏、30错题收藏
     * @param comStuCode 课件/试题ID
     * @return
     */
    Collection findOne(@Param("type")String type,
                       @Param("comStuCode") String comStuCode,
                       @Param("userId") String userId);

    /**
     * 收藏/取消收藏
     * @param id 主键
     * @param stauts 0收藏，1取消收藏
     * @return
     */
    boolean doAgain(@Param("id") String id, @Param("status") Integer stauts);
}