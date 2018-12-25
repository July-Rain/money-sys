package com.lawschool.service.impl.practicecenter;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.base.Page;
import com.lawschool.beans.ExerciseConfigureEntity;
import com.lawschool.beans.practicecenter.TaskExerciseEntity;
import com.lawschool.dao.practicecenter.TaskExerciseDao;
import com.lawschool.form.CommonForm;
import com.lawschool.service.practicecenter.TaskExerciseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: Moon
 * @Date: 2018/12/21 17:59
 * @Description:
 */
@Service
public class TaskExerciseServiceImpl extends AbstractServiceImpl<TaskExerciseDao, TaskExerciseEntity>
        implements TaskExerciseService {

    public List<CommonForm> findByUserAndConIds(String userId, List<String> list){
        List<CommonForm> resultList = dao.findByUserAndConIds(userId, list);

        return resultList;
    }
}
