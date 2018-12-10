package com.lawschool.service;

import com.baomidou.mybatisplus.service.IService;
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
public interface StuMediaService  extends IService<StuMedia> {

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

    /**
     * @Author MengyuWu
     * @Description 插入学习管理数据
     * @Date 15:34 2018-12-5
     * @Param [stuMedia]
     * @return int
     **/
    
    void insertStuMedia(StuMedia stuMedia,User user);

    /**
     * @Author MengyuWu
     * @Description 根据id查看详情页面 包括权限内数据
     * @Date 16:35 2018-12-5
     * @Param [id]
     * @return com.lawschool.beans.StuMedia
     **/
    
    StuMedia selectStuMediaInfo(String id);
    
    
    /**
     * @Author zjw
     * @Description 获取教官的课程
     * @Date 9:19 2018-12-6
     * @Param [id]
     * @return java.util.List<com.lawschool.beans.StuMedia>
    **/
    List<StuMedia> selectTchMedia(Map<String,Object> param,User user);

    /**
     * @Author zjw
     * @Description 教官删除自己的课程
     * @Date 15:38 2018-12-6
     * @Param [stuMedia, user]
     * @return java.util.List<com.lawschool.beans.StuMedia>
    **/
    int delTchMedia(StuMedia stuMedia,User user);

    /**
     * @Author MengyuWu
     * @Description 分页查询
     * @Date 13:56 2018-12-8
     * @Param [params]
     * @return com.lawschool.util.PageUtils
     **/
    
    PageUtils queryPage(Map<String, Object> params);
}
