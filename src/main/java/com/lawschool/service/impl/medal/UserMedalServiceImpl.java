package com.lawschool.service.impl.medal;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.medal.UserMedalEntity;
import com.lawschool.dao.medal.UserMedalDao;
import com.lawschool.service.medal.UserMedalService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMedalServiceImpl extends AbstractServiceImpl<UserMedalDao, UserMedalEntity> implements UserMedalService {

    public List<String> findMedalIdList(String userId) {
        return dao.findMedalIdList(userId);
    }

    public void enbleWear(String userId, String medalId){
        dao.disenbleWear(userId);
        dao.enbleWear(userId, medalId);
    }

    public Boolean checkUserMedal(UserMedalEntity userMedal){
        int num = dao.checkUserMedal(userMedal);
        return (num > 0);
    }
}
