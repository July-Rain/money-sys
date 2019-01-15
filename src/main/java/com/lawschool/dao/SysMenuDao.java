package com.lawschool.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lawschool.beans.SysMenu;

import java.util.List;
import java.util.Map;


public interface SysMenuDao extends BaseMapper<SysMenu> {

	public List<Map<String,Object>> queryForZtree();
	public SysMenu selectById(String id);
	//public int insert(SysMenu menu);
	//public int updateById(SysMenu menu);

//	public int delete(String id);

	public List<SysMenu> queryListParentId(String id);

	public List<SysMenu> listMenuAuth(String roleId);
	List<SysMenu> queryNotButtonList();

	/**
	 * @Author MengyuWu
	 * @Description 根据菜单id查询子节点
	 * @Date 11:07 2019-1-12
	 * @Param [id]
	 * @return java.util.List<com.lawschool.beans.SysMenu>
	 **/
	List<SysMenu> queryChildById(String id);
}
