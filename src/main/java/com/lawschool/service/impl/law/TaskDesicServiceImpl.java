package com.lawschool.service.impl.law;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lawschool.beans.law.TaskDesicEntity;
import com.lawschool.dao.law.TaskDesicDao;
import com.lawschool.service.law.TaskDesicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: TaskDesicServiceImpl
 * Description: 任务节点impl
 * date: 2018-12-25 17:11
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
@Service
public class TaskDesicServiceImpl extends ServiceImpl<TaskDesicDao,TaskDesicEntity> implements TaskDesicService {
}
