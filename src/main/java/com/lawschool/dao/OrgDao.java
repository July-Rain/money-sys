package com.lawschool.dao;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lawschool.beans.Org;

public interface OrgDao extends BaseMapper<Org> {
	//public int insert(Org record);

	public int insertSelective(Org record);

	public Org selectOrgById(Long orgId);

	public List<Org> selectAllOrg();

	public void update(Org org);

	public List<Org> findOrgByParentId(Long orgId);
	
	public List<Map<String,Object>> findUserByOrg(String orgCode);
}