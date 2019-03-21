package com.lawschool.service.bbs;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.bbs.ReplyEntity;

import java.util.List;

public interface ReplyService extends AbstractService<ReplyEntity> {


    Integer findMyReply(String userId);

    /**
     * 获取所有我评论的内容的ids
     * @param userId
     * @return
     */
    List<String> findMyReplyIds (String userId);
}
