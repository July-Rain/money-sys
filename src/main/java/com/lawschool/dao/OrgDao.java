package com.lawschool.dao;

import java.util.List;

import com.lawschool.beans.Org;
import com.lawschool.beans.User;

public interface OrgDao {
	public int insert(Org record);

	public int insertSelective(Org record);

	public Org selectOrgById(Long orgId);

	public List<Org> selectAllOrg();

	public void update(Org org);

	public List<Org> findOrgByParentId(Long orgId);
	
	public List<User> findUserByOrg(String orgCode);
}