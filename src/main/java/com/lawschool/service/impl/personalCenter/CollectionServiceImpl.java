package com.lawschool.service.impl.personalCenter;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.personalCenter.CollectionEntity;
import com.lawschool.dao.personalCenter.CollectionDao;
import com.lawschool.service.personalCenter.CollectionService;
import org.springframework.stereotype.Service;

/**
 * @Auther: Moon
 * @Date: 2019/3/8 15:08
 * @Description:
 */
@Service
public class CollectionServiceImpl extends AbstractServiceImpl<CollectionDao, CollectionEntity> implements CollectionService {

    public Integer getTotalQuestion(String userId, Integer type){

        return dao.getTotalQuestion(userId, type);
    }
}
