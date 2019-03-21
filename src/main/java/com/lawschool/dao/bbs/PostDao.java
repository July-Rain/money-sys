package com.lawschool.dao.bbs;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.bbs.PostEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PostDao extends AbstractDao<PostEntity> {

    Integer updateNum(PostEntity postEntity);

    Integer findMyPost(@Param("userId") String userId);

    /**
     * 获取所有我发表的内容
     * @param userId
     * @return
     */
    List<String> findMineList(@Param("userId") String userId);


}
