package com.lawschool.service.impl;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lawschool.beans.SysLogEntity;
import com.lawschool.beans.SysMenu;
import com.lawschool.dao.SysMenuDao;
import com.lawschool.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenu> implements SysMenuService   {
	
	@Autowired
	private SysMenuDao sysmenuDao;

	@Override
	public List<Map<String, Object>> queryForZtree() {
     	return	sysmenuDao.queryForZtree();
	}


	@Override
	public SysMenu selectById(String id) {
		return sysmenuDao.selectById(id);
	}


	/*@Override
	public int insert(SysMenu menu) {
		return sysmenuDao.insert(menu);
	}*/

	/*@Override
	public int updateById(SysMenu menu) {
		return sysmenuDao.updateById(menu);
	}*/

	@Override
	public int delete(String id) {

		//删除前 要先去查找看看这个节点下是否存在子节点  存在布需删
		List<SysMenu> list=queryListParentId(id);
		if(list.size() > 0){
//			return list.error("请先删除子菜单或按钮");
			return 0;
		}
		return sysmenuDao.delete(id);
	}

	@Override
	public List<SysMenu> queryListParentId(String id) {
		return sysmenuDao.queryListParentId(id);
	}
}
