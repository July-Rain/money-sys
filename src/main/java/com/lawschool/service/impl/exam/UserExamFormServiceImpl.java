package com.lawschool.service.impl.exam;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.exam.UserExam;
import com.lawschool.dao.exam.UserExamDao;
import com.lawschool.dao.exam.UserExamFormDao;
import com.lawschool.form.UserExamForm;
import com.lawschool.service.exam.UserExamFormService;
import com.lawschool.service.exam.UserExamService;
import org.springframework.stereotype.Service;

/**
 * ClassName: UserExamFormServiceImpl
 * Description: TODO
 * date: 2019/1/2915:39
 *
 * @author 王帅奇
 */
@Service
public class UserExamFormServiceImpl extends AbstractServiceImpl<UserExamFormDao, UserExamForm> implements UserExamFormService {
}
