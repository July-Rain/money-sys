package com.lawschool.service;

import com.lawschool.beans.SysMenu;
import com.lawschool.beans.User;

import java.util.List;
import java.util.Map;


/**
 * 
 * @Descriptin  菜单管理service接口
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/28
 *
 */
public interface SysMenuService {
	//根据用户id找菜单   涉及到uesrService  等······

	//list。包含所有的目录，菜单，按钮
	public List<Map<String,Object>> queryForZtree();

	//根据菜单id 找 详情
	public SysMenu selectById(String id);


    //	保存新增菜单
	public int insert(SysMenu menu);
	//修改菜单
	public int updateById(SysMenu menu);

	//删除菜单(包含判断是否有子菜单与按钮)
	public int delete(String id);

	//查找子节点
	public List<SysMenu> queryListParentId(String id);

}
