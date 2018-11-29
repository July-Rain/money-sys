package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableField;

import java.math.BigDecimal;
import java.util.Date;

public class Role {
    private BigDecimal roleId;

    private String roleName;

    private String remark;

    private Date createTime;

    @TableField(exist = false)
    //菜单id
    private String menuIds;

    @TableField(exist = false)
    //单位的id
    private String orgIds;

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

    public String getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }

    public String getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(String orgIds) {
        this.orgIds = orgIds;
    }
}
