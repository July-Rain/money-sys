package com.lawschool.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lawschool.beans.Role;
import com.lawschool.beans.SysMenu;
import com.lawschool.beans.User;
import org.springframework.stereotype.Service;

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
}
