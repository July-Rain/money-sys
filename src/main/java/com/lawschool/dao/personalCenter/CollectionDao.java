package com.lawschool.dao.personalCenter;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.personalCenter.CollectionEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther: Moon
 * @Date: 2019/3/8 15:04
 * @Description: 收藏Dao
 */
public interface CollectionDao extends AbstractDao<CollectionEntity> {

    Integer getTotalQuestion(@Param("userId") String userId,
                             @Param("type") Integer type);

    /**
     * 获取题目为了组卷
     * @param type
     * @param num
     * @return
     */
    List<String> getQuestionsForPaper(@Param("type") Integer type,
                                      @Param("num") Integer num,
                                      @Param("userId") String userId);

    /**
     * 判断是否收藏过
     * @param sourceId
     * @param userId
     * @return > 0 已收藏
     */
    Integer isCollect(@Param("sourceId") String sourceId,
                      @Param("userId") String userId,
                      @Param("source") Integer source);

    /**
     * 保存收藏
     * @param entity
     * @return
     */
    boolean mySave(CollectionEntity entity);

    boolean myDelete(@Param("sourceId") String sourceId,
                     @Param("userId") String userId,
                     @Param("source") Integer source);
}
