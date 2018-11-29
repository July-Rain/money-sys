package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableName;

@TableName("law_sys_role_menu")
public class SysRoleMenu {
    private String id;

    private String roleId;

    private String menuId;

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

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
}