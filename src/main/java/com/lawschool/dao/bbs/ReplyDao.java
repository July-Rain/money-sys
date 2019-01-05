package com.lawschool.dao.bbs;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.bbs.ReplyEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReplyDao extends AbstractDao<ReplyEntity> {

    void deleteByPostId(List<String> ids);

    Integer findMyReply(@Param("userId") String userId);
}
