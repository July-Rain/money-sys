package com.lawschool.service.learn;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.StuMedia;
import com.lawschool.beans.User;
import com.lawschool.beans.learn.LearnTasksEntity;
import com.lawschool.util.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * InterfaceName: LearnTasksService
 * Description: 学习任务service
 * date: 2018-12-18 16:16
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
public interface LearnTasksService extends AbstractService<LearnTasksEntity> {


    /**
     * @Author MengyuWu
     * @Description 插入学习任务
     * @Date 15:25 2018-12-24
     * @Param [stuMedia, user]
     * @return void
     **/


    void insertLearnTask(LearnTasksEntity stuMedia,User user);

    /**
     * @Author MengyuWu
     * @Description 根据id查看详情页面 包括权限内数据
     * @Date 15:25 2018-12-24
     * @Param [id]
     * @return com.lawschool.beans.StuMedia
     **/


    LearnTasksEntity selectLearnTask(String id);



    /**
     * @Author MengyuWu
     * @Description 分页查询
     * @Date 15:25 2018-12-24
     * @Param [params]
     * @return com.lawschool.util.PageUtils
     **/
    

    PageUtils queryPage(Map<String, Object> params);

    /**
     * @Author MengyuWu
     * @Description 学习任务修改
     * @Date 15:26 2018-12-24
     * @Param [learnTask, user]
     * @return void
     **/
    


    void updateLearnTask(LearnTasksEntity learnTask, User user);
}
