package com.lawschool.service.learn;

import com.baomidou.mybatisplus.service.IService;
import com.lawschool.beans.learn.TasksUserEntity;

/**
 * InterfaceName: TasksUserService
 * Description: TODO
 * date: 2019-1-9 9:30
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
public interface TasksUserService extends IService<TasksUserEntity> {
    /**
     * @Author MengyuWu
     * @Description TODO
     * @Date 9:26 2019-1-9
     * @Param [deptIdArr, userIdArr, taskId]
     * @return int
     **/

    int insertLearnUser(String [] deptIdArr,String [] userIdArr,String taskId);
}
