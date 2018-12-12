package com.lawschool.service.impl;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lawschool.beans.SysConfig;
import com.lawschool.beans.SysLogEntity;
import com.lawschool.beans.SysMenu;
import com.lawschool.dao.SysMenuDao;
import com.lawschool.service.SysMenuService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Query;
import com.lawschool.util.UtilValidate;
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

//	@Override
//	public int delete(String id) {
//
//		//删除前 要先去查找看看这个节点下是否存在子节点  存在布需删
//		List<SysMenu> list=queryListParentId(id);
//		if(list.size() > 0){
////			return list.error("请先删除子菜单或按钮");
//			return 0;
//		}
//		return sysmenuDao.delete(id);
//	}

	@Override
	public List<SysMenu> queryListParentId(String id) {
		return sysmenuDao.queryListParentId(id);
	}



	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String type = (String)params.get("type");
		String parentId = (String)params.get("parentId");
//		String status = (String)params.get("status");
		EntityWrapper<SysMenu> ew = new EntityWrapper<>();

		if(UtilValidate.isNotEmpty(type)){
			ew.eq("TYPE",type);
		}

		if(UtilValidate.isNotEmpty(parentId)){
			ew.eq("PARENT_ID",parentId);
		}
//		ew.eq("type","0");//为目录  然且没删除
		Page<SysMenu> page = this.selectPage(
				new Query<SysMenu>(params).getPage(),ew);

		return new PageUtils(page);
	}

	@Override
	public List<SysMenu> queryNotButtonList() {
		return baseMapper.queryNotButtonList();
	}

	@Override
	public List<SysMenu> listAllMenuTree() {
		//先获得一级目录
		 List<SysMenu> SysMenuOne= this.selectList(new EntityWrapper<SysMenu>().eq("TYPE","0").orderBy("ORDER_NUM", true));
		 for(SysMenu menu:SysMenuOne)
		 {
			//去找有没有子菜单
			 listAllMenuTree2(menu,menu.getList());
		 }
		return SysMenuOne;
	}

	public  void listAllMenuTree2(SysMenu menu, List menuList)
	{


		List<SysMenu> SysMenuSon= this.selectList(
				new EntityWrapper<SysMenu>().eq("PARENT_ID",menu.getId()).orderBy("ORDER_NUM", true)
		);
		for(SysMenu menuSon:SysMenuSon)
		{
			listAllMenuTree2(menuSon,menuSon.getList());

			menuList.add(menuSon);
		}

	}
}
