package com.lawschool.service.impl;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lawschool.util.Result;
import com.lawschool.util.UtilValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawschool.beans.Org;
import com.lawschool.dao.OrgDao;
import com.lawschool.service.OrgService;

@Service
public class OrgServiceImpl extends ServiceImpl<OrgDao,Org> implements OrgService{

	@Autowired
	private OrgDao orgDao;

	
	/*public int insert(Org record) {
		// TODO Auto-generated method stub
		return orgDao.insert(record);
	}*/

	
	public int insertSelective(Org record) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public Org selectOrgById(Long orgId) {
		// TODO Auto-generated method stub
		return orgDao.selectOrgById(orgId);
	}

	
	public List<Org> selectAllOrg() {
		// TODO Auto-generated method stub
		return orgDao.selectAllOrg();
	}

	
	public void update(Org org) {
		// TODO Auto-generated method stub
		
	}

	
	public List<Org> findOrgByParentId(Long orgId) {
		return orgDao.findOrgByParentId(orgId);
	}

	public List<Map<String, Object>> findUserByOrg(String orgCode) {
		return orgDao.findUserByOrg(orgCode);
	}

	@Override
	public List<Org> queryForTree(String orgCode) {

		return	orgDao.queryForTree(orgCode);
	}

	@Override
	public Org findOrgByCode(String orgCode) {
		return orgDao.findOrgByCode(orgCode);
	}

	@Override
	public List<String> getSubDeptIdList(String orgId) {
		return orgDao.getSubDeptIdList(orgId);
	}


	private void updateSubLevel(Org org){
		List<Org> lst=orgDao.selectList(new EntityWrapper<Org>().eq("PARENT_ID",org.getOrgId()));
		Integer orgLevel = org.getOrgLevel();
		if(UtilValidate.isNotEmpty(lst) ){
			lst.stream().forEach(e->{
				e.setOrgLevel(orgLevel+1);
				orgDao.updateAllColumnById(e);
				updateSubLevel(e);
			});
		}

	}

	@Override
	public Result addOrgLevel() {
		List<Org> lst = orgDao.selectList(new EntityWrapper<Org>().eq("LOCAL_ORG_CODE", "32"));

		lst.stream().forEach(e->{
			e.setOrgLevel(0);
			orgDao.updateAllColumnById(e);
			updateSubLevel(e);
		});
		return null;
	}
}
