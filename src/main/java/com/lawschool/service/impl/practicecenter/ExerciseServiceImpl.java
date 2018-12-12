package com.lawschool.service.impl.practicecenter;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.TestQuestions;
import com.lawschool.beans.practicecenter.ExerciseEntity;
import com.lawschool.dao.practicecenter.ExerciseDao;
import com.lawschool.form.RandomExerciseForm;
import com.lawschool.service.practicecenter.ExerciseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @version V1.0
 * @Description: 练习任务Service实现类
 * @author: 中石电子科技 徐祥
 * @date: 2018-12-07 15:33
 */
@Service
public class ExerciseServiceImpl extends AbstractServiceImpl<ExerciseDao, ExerciseEntity> implements ExerciseService {

    @Transactional(rollbackFor = Exception.class)
    public TestQuestions startExercise(RandomExerciseForm form){

        // 1、生成随机练习任务
        String id = IdWorker.getIdStr();
        ExerciseEntity entity = new ExerciseEntity();
        entity.setAnswerNum(0);
        entity.setRightNum(0);
        entity.setCreateTime(new Date());
        entity.setCreateUser(form.getUserId());
        entity.setOptTime(entity.getCreateTime());
        entity.setOptUser(form.getUserId());
        entity.setId(id);
        dao.insert(entity);

        // 2、获取符合条件试题Ids，存放到redis中
        // TODO 徐祥 等题库接口
        // 3、返回第一条题目

        return null;
    }

    /**
     * 获取题目
     * @param id
     * @param userId
     * @return
     */
    public TestQuestions getQuestion(String id, String userId){

        return null;
    }

}
