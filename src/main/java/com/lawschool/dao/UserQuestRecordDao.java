package com.lawschool.dao;

import com.lawschool.beans.TestQuestions;
import com.lawschool.beans.UserQuestRecord;

import java.util.List;
import java.util.Map;

public interface UserQuestRecordDao {
    int insert(UserQuestRecord record);

    int insertSelective(UserQuestRecord record);

    List<TestQuestions> listMyError(Map<String, Object> param);
        //我的错题-组卷 z
    List<TestQuestions> randomErrorColl(Map<String, Object> param);

    int cntMyError(Map<String, Object> param);
}