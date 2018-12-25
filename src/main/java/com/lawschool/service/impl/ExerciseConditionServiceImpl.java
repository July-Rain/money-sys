package com.lawschool.service.impl;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.ExerciseConditionEntity;
import com.lawschool.beans.User;
import com.lawschool.dao.ExerciseConditionDao;
import com.lawschool.service.ExerciseConditionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther: Moon
 * @Date: 2018/12/20 15:42
 * @Description:
 */
@Service
public class ExerciseConditionServiceImpl
        extends AbstractServiceImpl<ExerciseConditionDao, ExerciseConditionEntity>
        implements ExerciseConditionService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBatch(List<ExerciseConditionEntity> list, String conId){

        for(ExerciseConditionEntity entity : list){
            entity.preInsert("");
            entity.setConId(conId);

            dao.insert(entity);
        }

    }
}
