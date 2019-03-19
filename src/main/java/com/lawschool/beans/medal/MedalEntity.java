package com.lawschool.beans.medal;

import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;

@TableName("LAW_MEDAL")
public class MedalEntity extends DataEntity<MedalEntity> {

    private String titleName;//头衔名称
    private String integral;//积分条件
    private String credit;//学分条件
    private String badge;//徽章图标
    private String medalColor;  //勋章颜色

    public String getMedalColor() {
        return medalColor;
    }

    public void setMedalColor(String medalColor) {
        this.medalColor = medalColor;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }
}
