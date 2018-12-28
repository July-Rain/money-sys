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

	public List<Org> queryForTree(String orgCode);

	/**
	 * liuhuan
	 * @param orgCode
	 * @return
	 */
	Org findOrgByCode(String orgCode);

	/**
	 * @Author zjw
	 * @Description 查询子部门
	 * @Date 11:49 2018-12-27
	 * @Param
	 * @return
	**/
	List<String> getSubDeptIdList(String orgId);
}