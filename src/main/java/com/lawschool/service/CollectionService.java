package com.lawschool.service;

import com.baomidou.mybatisplus.service.IService;
import com.lawschool.base.AbstractService;
import com.lawschool.beans.Answer;
import com.lawschool.beans.Collection;
import com.lawschool.beans.TestQuestions;
import com.lawschool.beans.User;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;

import java.util.Map;

/**
 *
 * @Descriptin  收藏service
 * @author      zjw
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
public interface CollectionService extends IService<Collection> {

    /**
     * 取消收藏
     * @param collection
     * @param user
     * @return
     */
    int delCollection(Collection collection, User user);

    /**
     * 添加收藏
     * @param collection
     * @param user
     * @return
     */
    int addCollection(Collection collection,User user);


    /**
     * 我的收藏-重点试题（我收藏的题目）
     * @author      zjw
     * @param param
     * @return
     */
    PageUtils listMyCollection(Map<String,Object> param);

    /**
     * 重点试题-组卷
     * @author      zjw
     * @param param
     * @return
     */
    Result randomQuestColl(Map<String,Object> param, User user);

    /**
     * 我的收藏-我的错题（获取所有的错题）
     * @author      zjw
     * @param param
     * @return
     */
    PageUtils listMyErrorQuestion(Map<String,Object> param);

    /**
     * 错题-组卷
     * @author      zjw
     * @param param
     * @return
     */
    Result  randomErrorColl(Map<String,Object> param,User user);

    /**
     * 详情
     * @author      zjw
     * @param testQuestions
     * @return
     */
    TestQuestions getTestQuestions(TestQuestions testQuestions);

    /**
     * 取消题目收藏
     * @param questionId
     * @param userId
     * @return
     */
    boolean cancle(String questionId, String userId);
}
