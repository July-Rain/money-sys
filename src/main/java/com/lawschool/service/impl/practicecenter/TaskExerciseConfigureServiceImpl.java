package com.lawschool.service.impl.practicecenter;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.practicecenter.TaskExerciseConfigureEntity;
import com.lawschool.dao.practicecenter.TaskExerciseConfigureDao;
import com.lawschool.service.TestQuestionService;
import com.lawschool.service.practicecenter.TaskExerciseConfigureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: Moon
 * @Date: 2018/12/29 14:01
 * @Description: 练习中心--练习任务配置Impl
 */
@Service
public class TaskExerciseConfigureServiceImpl
        extends AbstractServiceImpl<TaskExerciseConfigureDao, TaskExerciseConfigureEntity>
        implements TaskExerciseConfigureService {

    @Autowired
    private TestQuestionService testQuestionService;

    /**
     * 自定义保存方法，处理逻辑
     * @param entity
     */
    @Transactional(rollbackFor = Exception.class)
    public void mySave(TaskExerciseConfigureEntity entity){

        // 获取满足条件的题目数量
        Map<String, String> map = new HashMap<String, String>();

        map.put("classify", entity.getClassify());
        map.put("type", entity.getType());
        map.put("difficulty", entity.getDifficulty());
        map.put("themeId", entity.getThemeId());
        map.put("delFlag", "0");

        Integer num = testQuestionService.getNumByConditions(map);

        entity.setTotal(num);
        entity.setDelFlag(TaskExerciseConfigureEntity.DEL_NORMAL);
        entity.preInsert(entity.getCreateUser());

        dao.insert(entity);
    }

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean logicDelete(String id){

        return dao.logicDelete(id, 1);
    }

}
