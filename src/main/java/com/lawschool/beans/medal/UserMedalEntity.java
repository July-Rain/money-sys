package com.lawschool.beans.medal;

import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;

@TableName("law_user_medal")
public class UserMedalEntity extends DataEntity<UserMedalEntity> {

    private String userId;

    private String medalId;

    private String isWear;//是否佩戴

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

    public String getIsWear() {
        return isWear;
    }

    public void setIsWear(String isWear) {
        this.isWear = isWear;
    }
}