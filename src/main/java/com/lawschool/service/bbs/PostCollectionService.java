package com.lawschool.service.bbs;

import com.lawschool.base.AbstractService;
import com.lawschool.base.Page;
import com.lawschool.beans.bbs.PostCollectionEntity;
import com.lawschool.form.CollectionPostForm;

import java.util.List;

public interface PostCollectionService extends AbstractService<PostCollectionEntity> {

    /**
     * 查询分页数据
     * @param page   分页对象
     * @param entity
     * @return
     */
    Page<CollectionPostForm> findPage(Page<CollectionPostForm> page, CollectionPostForm entity);

    Integer findMyCollection(String userId);

    Integer findByUser(PostCollectionEntity postCollectionEntity);

    /**
     * 获取所有我收藏的内容的ID
     * @param userId
     * @return
     */
    List<String> findMyCollIds(String userId);
}
