package com.lawschool.service.impl.learn;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lawschool.beans.User;
import com.lawschool.beans.learn.TasksUserEntity;
import com.lawschool.dao.learn.TasksUserDao;
import com.lawschool.service.UserService;
import com.lawschool.service.learn.TasksUserService;
import com.lawschool.util.GetUUID;
import com.lawschool.util.UtilValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ClassName: TasksUserServiceImpl
 * Description: TODO
 * date: 2019-1-9 9:31
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
@Service
public class TasksUserServiceImpl extends ServiceImpl<TasksUserDao,TasksUserEntity>  implements TasksUserService {
    @Autowired
    private UserService userService;
    @Override
    public int insertLearnUser(String[] deptIdArr, String[] userIdArr, String taskId) {
        List<TasksUserEntity> tasksUserEntities = new ArrayList<>();
        if(UtilValidate.isNotEmpty(deptIdArr)){
            for(String deptId:deptIdArr){
                //获取所有的人员信息
                List<User> userList =userService.selectList(new EntityWrapper<User>().eq("org_id",deptId));
                for(User user : userList){
                    TasksUserEntity tasksUserEntity = new TasksUserEntity();
                    tasksUserEntity.setId(GetUUID.getUUIDs("TU"));
                    tasksUserEntity.setTaskId(taskId);
                    tasksUserEntity.setUserId(user.getId());
                    tasksUserEntity.setCreateTime(new Date());
                    tasksUserEntity.setIsFinish("0");
                    tasksUserEntities.add(tasksUserEntity);
                }
            }
        }
        if (UtilValidate.isNotEmpty(userIdArr)){
            for(String id: userIdArr){
                TasksUserEntity tasksUserEntity = new TasksUserEntity();
                tasksUserEntity.setId(GetUUID.getUUIDs("TU"));
                tasksUserEntity.setTaskId(taskId);
                tasksUserEntity.setUserId(id);
                tasksUserEntity.setCreateTime(new Date());
                tasksUserEntity.setIsFinish("0");
                tasksUserEntities.add(tasksUserEntity);
            }
        }
        if(tasksUserEntities.size()>0){
            this.insertBatch(tasksUserEntities);
        }
        return tasksUserEntities.size();
    }
}
