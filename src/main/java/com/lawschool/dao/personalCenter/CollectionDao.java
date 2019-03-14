package com.lawschool.dao.personalCenter;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.personalCenter.CollectionEntity;

/**
 * @Auther: Moon
 * @Date: 2019/3/8 15:04
 * @Description: 收藏Dao
 */
public interface CollectionDao extends AbstractDao<CollectionEntity> {

    Integer getTotalQuestion(String userId, Integer type);
}
