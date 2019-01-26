package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;

@TableName("LAW_USER_INTEGRAL")
public class UserIntegral extends DataEntity<UserIntegral> {
    private String id;

    private String userId;

    private int integralPoint;//积分

    private int creditPoint;//学分

    private String orgCode;//部门code

    private String fullName;//用户名

    private String userCode;//用户code

    private String orgName;//部门名字

    private String orgId;//部门id


    @TableField(exist = false)
    private int allItrRank;//用户积分全排名

    @TableField(exist = false)
    private int orgItrRank;//用户积分部门全排名

    @TableField(exist = false)
    private int allCdtRank;//用户学分全排名

    @TableField(exist = false)
    private int orgCdtRank;//用户学分部门全排名



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

    public int getIntegralPoint() {
        return integralPoint;
    }

    public void setIntegralPoint(int integralPoint) {
        this.integralPoint = integralPoint;
    }

    public int getCreditPoint() {
        return creditPoint;
    }

    public void setCreditPoint(int creditPoint) {
        this.creditPoint = creditPoint;
    }

    public int getAllItrRank() {
        return allItrRank;
    }

    public void setAllItrRank(int allItrRank) {
        this.allItrRank = allItrRank;
    }

    public int getOrgItrRank() {
        return orgItrRank;
    }

    public void setOrgItrRank(int orgItrRank) {
        this.orgItrRank = orgItrRank;
    }

    public int getAllCdtRank() {
        return allCdtRank;
    }

    public void setAllCdtRank(int allCdtRank) {
        this.allCdtRank = allCdtRank;
    }

    public int getOrgCdtRank() {
        return orgCdtRank;
    }

    public void setOrgCdtRank(int orgCdtRank) {
        this.orgCdtRank = orgCdtRank;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}