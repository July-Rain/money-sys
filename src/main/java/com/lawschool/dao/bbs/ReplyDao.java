package com.lawschool.dao.bbs;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.bbs.ReplyEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReplyDao extends AbstractDao<ReplyEntity> {

    void deleteByPostId(List<String> ids);

    Integer findMyReply(@Param("userId") String userId);

    /**
     * 获取所有我评论的内容的ids
     * @param userId
     * @return
     */
    List<String> findMyReplyIds (@Param("userId")String userId);
}
