package com.lawschool.service.exam;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.exam.UserExamAnswer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public interface UserExamAnswerService extends AbstractService<UserExamAnswer> {

    CopyOnWriteArrayList<String> getListByDiffScore(int score);
}
