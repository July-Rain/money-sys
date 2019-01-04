package com.lawschool.service.impl.bbs;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.bbs.PostCollectionEntity;
import com.lawschool.dao.bbs.PostCollectionDao;
import com.lawschool.service.bbs.PostCollectionService;
import org.springframework.stereotype.Service;

/**
 * @ClassName PostCollectionServiceImpl
 * @Author wangtongye
 * @Date 2019/1/3 10:37
 * @Versiom 1.0
 **/
@Service
public class PostCollectionServiceImpl extends AbstractServiceImpl<PostCollectionDao, PostCollectionEntity> implements PostCollectionService {

    public Integer findMyCollection(String userId){
        return dao.findMyCollection(userId);
    }
}
