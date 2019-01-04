package com.lawschool.dao.medal;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.medal.UserMedalEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMedalDao extends AbstractDao<UserMedalEntity> {

    List<String> findMedalIdList(@Param("userId") String userId);

    void enbleWear(@Param("userId") String userId, @Param("medalId") String medalId);

    void disenbleWear(@Param("userId") String userId);

    Integer checkUserMedal(UserMedalEntity userMedal);
}
