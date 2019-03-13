package com.lawschool.beans.system;

import com.baomidou.mybatisplus.annotations.TableField;
import com.lawschool.base.DataEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Auther: Moon
 * @Date: 2019/1/16 19:04
 * @Description: 菜单
 */
public class SysMenuEntity extends DataEntity<SysMenuEntity> {

    private static final long serialVersionUID = -4864499324792183331L;

    private String parentId;// 父ID
    private String name;// 名称
    private String url;// 路由
    private String perms;// 权限
    private Integer type;// 类型
    private String icon;// 图标
    private Integer orderNum;// 排序
    private Integer isShow;// 是否使用

    @TableField(exist = false)
    private List<SysMenuEntity> list = new ArrayList<SysMenuEntity>();// 子类
    @TableField(exist=false)
    private int level;//层级

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public List<SysMenuEntity> getList() {
        return list;
    }

    public void setList(List<SysMenuEntity> list) {
        this.list = list;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SysMenuEntity sysMenuEntity = (SysMenuEntity) obj;
        return Objects.equals(id, sysMenuEntity.id);
    }
}
