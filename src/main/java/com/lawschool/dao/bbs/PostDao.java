package com.lawschool.dao.bbs;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.bbs.PostEntity;
import org.apache.ibatis.annotations.Param;

public interface PostDao extends AbstractDao<PostEntity> {

    Integer findMyPost(@Param("userId") String userId);
}
