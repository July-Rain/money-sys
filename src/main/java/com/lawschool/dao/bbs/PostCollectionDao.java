package com.lawschool.dao.bbs;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.bbs.PostCollectionEntity;
import com.lawschool.form.CollectionPostForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PostCollectionDao extends AbstractDao<PostCollectionEntity> {

    /**
     * 查询多条数据
     * @param entity
     * @return
     */
    List<CollectionPostForm> findList(CollectionPostForm entity);

    List<String> queryPostIdList(@Param("userId") String userId);

    void deleteByUserId(@Param("userId") String userId);

    void deleteByPostId(List<String> ids);

    void deleteBatchByUserId(List<String> ids);

    Integer findMyCollection(@Param("userId") String userId);
}
