package com.lawschool.beans.system;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@TableName("LAW_SYS_ROLE")
public class SysRoleEntity extends DataEntity<SysRoleEntity> {
    /**
     * 角色名
     */
    private String roleName;
    /**
     * 角色备注
     */
    private String remark;


    @TableField(exist = false)
    private List<String> menuList;
    @TableField(exist = false)
    private List<String> orgList;


    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<String> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<String> menuList) {
        this.menuList = menuList;
    }

    public List<String> getOrgList() {
        return orgList;
    }

    public void setOrgList(List<String> orgList) {
        this.orgList = orgList;
    }
}
