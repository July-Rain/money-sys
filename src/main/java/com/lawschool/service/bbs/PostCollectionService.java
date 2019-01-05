package com.lawschool.service.bbs;

import com.lawschool.base.AbstractService;
import com.lawschool.base.Page;
import com.lawschool.beans.bbs.PostCollectionEntity;
import com.lawschool.form.CollectionPostForm;

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
}
