package com.lawschool.service;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.StuMedia;
import com.lawschool.beans.User;
import com.lawschool.util.PageUtils;

import java.math.BigDecimal;
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
public interface StuMediaService  extends AbstractService<StuMedia> {

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
    
    PageUtils queryPage(Map<String, Object> params,User user);

    /**
     * @Author MengyuWu
     * @Description 学习管理修改
     * @Date 17:08 2018-12-13
     * @Param [stuMedia, user]
     * @return void
     **/
    

    void updateStuMedia(StuMedia stuMedia,User user);

    /**
     * @Author MengyuWu
     * @Description 根据任务信息查询相关的图文 视频 音频信息
     * @Date 10:19 2018-12-27
     * @Param [params]
     * @return com.lawschool.util.PageUtils
     **/
    
    PageUtils listStuByTask(Map<String, Object> params);

    /**
     * @Author MengyuWu
     * @Description 根据id更新对应的数据
     * @Date 10:32 2018-12-28
     * @Param [accId]
     * @return int
     **/
    
    int updateCount(String stuId);

    boolean updateStatus(String id, String status);
    boolean updateStatus2(String id, String status);
    /**
     * @Author MengyuWu
     * @Description 根据当前用户查询任务信息查询相关的图文 视频 音频信息
     * @Date 10:19 2018-12-27
     * @Param [params]
     * @return com.lawschool.util.PageUtils
     **/

    PageUtils listStuByUser(Map<String, Object> params);

    /**
     * @Author MengyuWu
     * @Description 公开学习内容
     * @Date 13:45 2019-3-15
     * @Param [stuId, user]
     * @return int
     **/
    
    int updateOpen(String stuId,User user);
}
