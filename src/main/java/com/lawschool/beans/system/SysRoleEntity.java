package com.lawschool.beans.system;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@TableName("LAW_SYS_ROLE")
public class SysRoleEntity extends DataEntity<SysRoleEntity> {
    /**
     * 角色姓名
     */
    private String roleName;
    /**
     * 角色备注
     */
    private String remark;

    @TableField(exist = false)
    private String menuIds;
    @TableField(exist = false)
    private String orgIds;


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
