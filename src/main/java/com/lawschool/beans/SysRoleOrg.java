package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;
@TableName("law_sys_role_org")
public class SysRoleOrg {
    private String id;

    private String roleId;

    private String orgId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}