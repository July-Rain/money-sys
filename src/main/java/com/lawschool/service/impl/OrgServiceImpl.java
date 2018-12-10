package com.lawschool.service.impl;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawschool.beans.Org;
import com.lawschool.beans.User;
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
	
}
