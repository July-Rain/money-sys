package com.lawschool.dao;

import com.lawschool.beans.UserQuestRecord;

public interface UserQuestRecordDao {
    int insert(UserQuestRecord record);

    int insertSelective(UserQuestRecord record);
}