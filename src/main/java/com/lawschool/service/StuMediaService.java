package com.lawschool.service;

import com.lawschool.beans.StuMedia;
import com.lawschool.beans.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *
 * @Descriptin  课件表service
 * @author      zjw
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
public interface StuMediaService  {

    /**
     * 我的收藏-重点课件
     * @param param
     * @return
     */
    List<StuMedia>  listMyCollection(Map<String,Object> param);

    int deleteStuMedia(StuMedia stuMedia);

    /**
     * 详情
     * @param stuMedia
     * @return
     */
    StuMedia getStuMedia(StuMedia stuMedia);

    int insert(StuMedia stuMedia, User user);

    int update(StuMedia stuMedia,User user);
}
