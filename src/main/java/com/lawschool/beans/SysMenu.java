package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableId;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
/**
 *
 * @Descriptin  菜单管理实体类
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/28
 *
 */
/**
 * 菜单管理
 */

public class SysMenu implements Serializable {
	/**
	 * 菜单ID
	 */
	@TableId
	private String id;
	/**
	 * 父菜单ID，一级菜单为0
	 */
	private String parentId;
	/**
	 * 父菜单名称
	 */
	private String parentName;
	/**
	 * 菜单名称
	 */
	private String name;
	/**
	 * 菜单URL
	 */
	private String url;
	/**
	 * 授权(多个用逗号分隔，如：user:list,user:create)
	 */
	private String perms;

	/**
	 * 类型     0：目录   1：菜单   2：按钮
	 */
	private String type;

	/**
	 * 菜单图标
	 */
	private String icon;

	/**
	 * 排序
	 */
	private String orderNum;

	private String isShow;
	
	/**
	 * ztree属性
	 */
//	@TableField(exist=false)
	private Boolean open;

//	@TableField(exist=false)
	private List<?> list;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}


/**
	 * 设置：父菜单ID，一级菜单为0
	 * @param parentId 父菜单ID，一级菜单为0
	 */

	
	/**
	 * 设置：菜单名称
	 * @param name 菜单名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取：菜单名称
	 * @return String
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 设置：菜单URL
	 * @param url 菜单URL
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 获取：菜单URL
	 * @return String
	 */
	public String getUrl() {
		return url;
	}
	
	public String getPerms() {
		return perms;
	}

	public void setPerms(String perms) {
		this.perms = perms;
	}



	/**
	 * 设置：菜单图标
	 * @param icon 菜单图标
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * 获取：菜单图标
	 * @return String
	 */
	public String getIcon() {
		return icon;
	}
	


	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	@Override
	public String toString() {
		return "SysMenu{" +
				"id='" + id + '\'' +
				", parentId='" + parentId + '\'' +
				", name='" + name + '\'' +
				", url='" + url + '\'' +
				", perms='" + perms + '\'' +
				", type='" + type + '\'' +
				", icon='" + icon + '\'' +
				", orderNum='" + orderNum + '\'' +
				", isShow='" + isShow + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof SysMenu)) return false;
		SysMenu sysMenu = (SysMenu) o;
		return Objects.equals(id, sysMenu.id);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id);
	}
}
