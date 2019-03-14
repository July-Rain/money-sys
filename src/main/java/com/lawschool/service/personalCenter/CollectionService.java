package com.lawschool.service.personalCenter;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.personalCenter.CollectionEntity;

/**
 * @Auther: Moon
 * @Date: 2019/3/8 15:06
 * @Description: 收藏service
 */
public interface CollectionService extends AbstractService<CollectionEntity> {

    /**
     * 查询用户收藏/错题数（除主观题、不可用题）
     * @param userId
     * @param type
     * @return
     */
    Integer getTotalQuestion(String userId, Integer type);
}
