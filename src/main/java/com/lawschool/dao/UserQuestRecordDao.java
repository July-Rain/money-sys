package com.lawschool.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lawschool.beans.SysMenu;
import com.lawschool.beans.TestQuestions;
import com.lawschool.beans.UserQuestRecord;

import java.util.List;
import java.util.Map;

public interface UserQuestRecordDao extends BaseMapper<UserQuestRecord> {


    List<TestQuestions> listMyError(Map<String, Object> param);
        //我的错题-组卷 z
    List<TestQuestions> randomErrorColl(Map<String, Object> param);

    int cntMyError(Map<String, Object> param);
}