package com.lawschool.dao.learn;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.learn.LearnTasksEntity;

/**
 * InterfaceName: LearnTasksDao
 * Description: 学习任务dao
 * date: 2018-12-18 16:16
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
public interface LearnTasksDao extends AbstractDao<LearnTasksEntity> {
    /**
     * @Author MengyuWu
     * @Description 新增数据
     * @Date 11:17 2019-3-13
     * @Param [learnTasksEntity]
     * @return void
     **/
    
    void insertWithBLOBs(LearnTasksEntity learnTasksEntity);
    /**
     * @Author MengyuWu
     * @Description 修改数据
     * @Date 11:17 2019-3-13
     * @Param [learnTasksEntity]
     * @return void
     **/
    
    void updateByPrimaryKeyWithBLOBs(LearnTasksEntity learnTasksEntity);
}
