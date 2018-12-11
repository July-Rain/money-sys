package com.lawschool.dao.system;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.system.TopicTypeEntity;
import org.apache.ibatis.annotations.Param;

/**
 * @version V1.0
 * @Description: 主题类型Dao
 * @author: 中石电子科技 徐祥
 * @date: 2018-12-11 11:13
 */
public interface TopicTypeDao extends AbstractDao<TopicTypeEntity> {
    boolean mysave(TopicTypeEntity entity);

    boolean updateDelFlag(@Param("id") String id);
}
