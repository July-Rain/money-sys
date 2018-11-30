package com.lawschool.service;

import com.lawschool.beans.Collection;
import com.lawschool.beans.User;
/**
 *
 * @Descriptin  收藏service
 * @author      zjw
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
public interface CollectionService {

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


}
