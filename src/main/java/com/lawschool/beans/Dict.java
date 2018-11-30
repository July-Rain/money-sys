package com.lawschool.beans;

import java.math.BigDecimal;
import java.util.Date;

public class Dict {
    private String id;

    private String name;

    private String type;

    private String parentCode;

    private String code;

    private String value;

    private int orderNum;

    private String remark;

    private String createUser;

    private Date addTime;

    private String delFlag;

    public Dict(String id, String name, String type, String parentCode, String code, String value, int orderNum, String remark, String createUser, Date addTime, String delFlag) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.parentCode = parentCode;
        this.code = code;
        this.value = value;
        this.orderNum = orderNum;
        this.remark = remark;
        this.createUser = createUser;
        this.addTime = addTime;
        this.delFlag = delFlag;
    }

    public Dict() {
        super();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode == null ? null : parentCode.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }

    @Override
    public String toString() {
        return "Dict{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", parentCode='" + parentCode + '\'' +
                ", code='" + code + '\'' +
                ", value='" + value + '\'' +
                ", orderNum=" + orderNum +
                ", remark='" + remark + '\'' +
                ", createUser='" + createUser + '\'' +
                ", addTime=" + addTime +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }
}
