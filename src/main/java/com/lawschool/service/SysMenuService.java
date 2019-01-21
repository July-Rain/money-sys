package com.lawschool.service;

import com.baomidou.mybatisplus.service.IService;
import com.lawschool.beans.SysLogEntity;
import com.lawschool.beans.SysMenu;
import com.lawschool.beans.User;
import com.lawschool.util.PageUtils;

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
public interface SysMenuService   extends IService<SysMenu> {
	//根据用户id找菜单   涉及到uesrService  等······

	//list。包含所有的目录，菜单，按钮
	public List<Map<String,Object>> queryForZtree();


	List<SysMenu> queryNotButtonList();

	//根据菜单id 找 详情
	public SysMenu selectById(String id);



	//删除菜单(包含判断是否有子菜单与按钮)
//	public int delete(String id);

	//查找子节点
	public List<SysMenu> queryListParentId(String id);



	/**
	 * @Author
	 * @Description 分页查询
	 * @Date 13:55 2018-12-8
	 * @Param [params]
	 * @return com.lawschool.util.PageUtils
	 **/

	PageUtils queryPage(Map<String, Object> params);


	List<SysMenu> listAllMenuTree();

	/**
	 * @Author MengyuWu
	 * @Description 根据菜单id查询子节点
	 * @Date 11:07 2019-1-12
	 * @Param [id]
	 * @return java.util.List<com.lawschool.beans.SysMenu>
	 **/
	
	List<SysMenu> queryChildById(String id);

	/**
	 * @Author MengyuWu
	 * @Description  根据菜单id查询父节点
	 * @Date 17:37 2019-1-18
	 * @Param [id]
	 * @return java.util.List<com.lawschool.beans.SysMenu>
	 **/

	List<SysMenu> queryParentById(String id);



}
