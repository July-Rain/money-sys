package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;

@TableName("LAW_USER_INTEGRAL")
public class UserIntegral extends DataEntity<UserIntegral> {
    private String id;

    private String userId;

    private Integer integralPoint;

    private Integer creditPoint;

    private String orgCode;


    @TableField(exist = false)
    private Integer allItrRank;

    @TableField(exist = false)
    private Integer orgItrRank;

    @TableField(exist = false)
    private Integer allCdtRank;

    @TableField(exist = false)
    private Integer orgCdtRank;


    public UserIntegral() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }


    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public Integer getIntegralPoint() {
        return integralPoint;
    }

    public void setIntegralPoint(Integer integralPoint) {
        this.integralPoint = integralPoint;
    }

    public Integer getCreditPoint() {
        return creditPoint;
    }

    public void setCreditPoint(Integer creditPoint) {
        this.creditPoint = creditPoint;
    }

    public Integer getAllItrRank() {
        return allItrRank;
    }

    public void setAllItrRank(Integer allItrRank) {
        this.allItrRank = allItrRank;
    }

    public Integer getOrgItrRank() {
        return orgItrRank;
    }

    public void setOrgItrRank(Integer orgItrRank) {
        this.orgItrRank = orgItrRank;
    }

    public Integer getAllCdtRank() {
        return allCdtRank;
    }

    public void setAllCdtRank(Integer allCdtRank) {
        this.allCdtRank = allCdtRank;
    }

    public Integer getOrgCdtRank() {
        return orgCdtRank;
    }

    public void setOrgCdtRank(Integer orgCdtRank) {
        this.orgCdtRank = orgCdtRank;
    }
}