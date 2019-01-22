package com.lawschool.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lawschool.beans.TestQuestions;
import com.lawschool.beans.UserQuestRecord;

import java.util.List;
import java.util.Map;

public interface UserQuestRecordDao extends BaseMapper<UserQuestRecord> {

    List<TestQuestions> listMyError(Map<String, Object> param);
        //我的错题-组卷 z
    List<TestQuestions> randomErrorColl(Map<String, Object> param);

    int cntMyError(Map<String, Object> param);

    int CheckpointByUser(String uid);
    int OnlinByUserCount(String uid);
    int OnlinByUser(String uid);
    int leitaiByUser(String uid);

    int chuangguanCorrectBydept(String deptcode);
    int chuangguanCorrectBydeptCount(String deptcode);

    int OnlinCorrectBydept(String deptcode);
    int OnlinCorrectBydeptCount(String deptcode);

    int leitaiCorrectBydept(String deptcode);
    int leitaiCorrectBydeptCount(String deptcode);

}