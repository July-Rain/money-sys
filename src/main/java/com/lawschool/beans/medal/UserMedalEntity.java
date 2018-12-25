package com.lawschool.beans.medal;

import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;

@TableName("law_user_medal")
public class UserMedalEntity extends DataEntity<UserMedalEntity> {

    private String userId;

    private String medalId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMedalId() {
        return medalId;
    }

    public void setMedalId(String medalId) {
        this.medalId = medalId;
    }
}