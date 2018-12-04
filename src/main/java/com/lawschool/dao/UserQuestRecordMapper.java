package com.lawschool.dao;

import com.lawschool.beans.UserQuestRecord;

public interface UserQuestRecordMapper {
    int insert(UserQuestRecord record);

    int insertSelective(UserQuestRecord record);
}