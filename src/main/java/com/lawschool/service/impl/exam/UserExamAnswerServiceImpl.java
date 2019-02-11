package com.lawschool.service.impl.exam;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.exam.UserExamAnswer;
import com.lawschool.dao.exam.UserExamAnswerDao;
import com.lawschool.service.exam.UserExamAnswerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * ClassName: UserExamAnswerServiceImpl
 * Description: TODO
 * date: 2019/1/1014:58
 *
 * @author 王帅奇
 */
@Service
public class UserExamAnswerServiceImpl extends AbstractServiceImpl<UserExamAnswerDao,UserExamAnswer> implements UserExamAnswerService {

    @Override
    public CopyOnWriteArrayList<String> getListByDiffScore(int score) {
        return dao.getListByDiffScore(score);
    }
}
