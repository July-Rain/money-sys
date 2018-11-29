package com.lawschool.beans;

import java.math.BigDecimal;
import java.util.Date;

public class Role {
    private BigDecimal roleId;

    private String roleName;

    private String remark;

    private Date createTime;

    public Role(BigDecimal roleId, String roleName, String remark, Date createTime) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.remark = remark;
        this.createTime = createTime;
    }

    public Role() {
        super();
    }

    public BigDecimal getRoleId() {
        return roleId;
    }

    public void setRoleId(BigDecimal roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
