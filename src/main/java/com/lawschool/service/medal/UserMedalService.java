package com.lawschool.service.medal;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.medal.UserMedalEntity;

import java.util.List;

public interface UserMedalService extends AbstractService<UserMedalEntity> {

    List<String> findMedalIdList(String userId);

    void enbleWear(String userId, String medalId);

    Boolean checkUserMedal(UserMedalEntity userMedal);
}
