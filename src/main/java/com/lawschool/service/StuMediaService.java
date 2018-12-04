package com.lawschool.service;

import com.lawschool.beans.StuMedia;
import com.lawschool.beans.User;
import com.lawschool.util.PageUtils;
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
     PageUtils listMyCollection(Map<String,Object> param);

    /**
     * 详情
     * @param stuMedia
     * @return
     */
    StuMedia getStuMedia(StuMedia stuMedia);

}
