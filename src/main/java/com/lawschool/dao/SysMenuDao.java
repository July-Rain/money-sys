package com.lawschool.dao;

import com.lawschool.beans.SysMenu;
import com.lawschool.beans.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface SysMenuDao {

	public List<Map<String,Object>> queryForZtree();
	public SysMenu selectById(String id);
	public int insert(SysMenu menu);
	public int updateById(SysMenu menu);

	public int delete(String id);

	public List<SysMenu> queryListParentId(String id);
}
