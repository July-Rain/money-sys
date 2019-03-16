package com.lawschool.service.personalCenter;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.personalCenter.CollectionEntity;

import java.util.List;

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

    /**
     * 获取收藏的题目，组卷用
     * @param source 类型，收藏OR 错题
     * @param num 题目数量
     * @return
     */
    List<String> getQuestionsForPaper(Integer source, Integer num, String userId);

    /**
     * 收藏功能
     * @param sourceId 资源ID
     * @param source 来源，见CollectionEntity的静态常量
     * @param isCollect 收藏 true/取消收藏 false
     * @return
     */
    boolean doCollect(String sourceId, Integer source, boolean isCollect, String userId);

    /**
     * 判断是否收藏
     * @param sourceId 资源ID
     * @param userId 用户ID
     * @param source 来源，见CollectionEntity定义的静态常量
     * @return
     */
    boolean isCollect(String sourceId, String userId, Integer source);
}
