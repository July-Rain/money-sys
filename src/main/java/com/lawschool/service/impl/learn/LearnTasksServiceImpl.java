package com.lawschool.service.impl.learn;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.learn.LearnTasksEntity;
import com.lawschool.dao.learn.LearnTasksDao;
import com.lawschool.service.learn.LearnTasksService;
import org.springframework.stereotype.Service;

/**
 * ClassName: LearnTasksServiceImpl
 * Description: 学习任务serviceImpl
 * date: 2018-12-18 16:17
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
@Service
public class LearnTasksServiceImpl extends AbstractServiceImpl<LearnTasksDao,LearnTasksEntity> implements LearnTasksService {
}
