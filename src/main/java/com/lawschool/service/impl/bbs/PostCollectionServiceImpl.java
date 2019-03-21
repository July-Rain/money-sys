package com.lawschool.service.impl.bbs;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.base.Page;
import com.lawschool.beans.bbs.PostCollectionEntity;
import com.lawschool.dao.bbs.PostCollectionDao;
import com.lawschool.form.CollectionPostForm;
import com.lawschool.service.bbs.PostCollectionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName PostCollectionServiceImpl
 * @Author wangtongye
 * @Date 2019/1/3 10:37
 * @Versiom 1.0
 **/
@Service
public class PostCollectionServiceImpl extends AbstractServiceImpl<PostCollectionDao, PostCollectionEntity> implements PostCollectionService {

    /**
     * 查询分页数据
     * @param page   分页对象
     * @param entity
     * @return
     */
    @Override
    public Page<CollectionPostForm> findPage(Page<CollectionPostForm> page, CollectionPostForm entity){
        entity.setPage(page);
        page.setList(dao.findList(entity));
        return page;
    }

    @Override
    public Integer findMyCollection(String userId){
        return dao.findMyCollection(userId);
    }

    @Override
    public Integer findByUser(PostCollectionEntity postCollectionEntity){
        return dao.findByUser(postCollectionEntity);
    }

    @Override
    public List<String> findMyCollIds(String userId) {
        return dao.queryPostIdList(userId);
    }
}
