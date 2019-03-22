package com.lawschool.service.bbs;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.bbs.PostEntity;
import com.lawschool.util.Result;

import java.util.Map;

public interface PostService extends AbstractService<PostEntity> {

    Integer updateNum(PostEntity postEntity);

    Integer findMyPost(String userId);

    /**
     * 获取个人评论收藏以及发表内容列表
     * @param params
     * @return
     */
    Result mineList(Map<String, Object> params) ;

    /**
     * 举报
     * @param id
     *
     */
    void report(String id );

    /**
     * 举报审核
     * @param id
     * @param status
     */
    void auditReport(String id,String status);
}
