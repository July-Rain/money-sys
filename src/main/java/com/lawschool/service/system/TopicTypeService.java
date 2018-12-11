package com.lawschool.service.system;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.system.TopicTypeEntity;

/**
 * @version V1.0
 * @Description: 主题类型Service
 * @author: 中石电子科技 徐祥
 * @date: 2018-12-11 11:15
 */
public interface TopicTypeService extends AbstractService<TopicTypeEntity> {

    boolean mysave(TopicTypeEntity entity);

    boolean updateDelFlag(String id);
}
