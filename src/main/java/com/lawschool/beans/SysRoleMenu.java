package com.lawschool.beans;

import java.math.BigDecimal;

public class SysRoleMenu {
    private BigDecimal id;

    private BigDecimal roleId;

    private BigDecimal menuId;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getRoleId() {
        return roleId;
    }

    public void setRoleId(BigDecimal roleId) {
        this.roleId = roleId;
    }

    public BigDecimal getMenuId() {
        return menuId;
    }

    public void setMenuId(BigDecimal menuId) {
        this.menuId = menuId;
    }
}