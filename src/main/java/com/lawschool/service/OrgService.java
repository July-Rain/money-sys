package com.lawschool.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.lawschool.beans.Org;
import com.lawschool.beans.User;
import com.lawschool.dao.OrgDao;

/**
 * 
 * @Title:OrgService.java
 * @Description: 部门管理接口
 * @author: 王帅奇
 * @date 2018年11月29日
 */
public interface OrgService  extends IService<Org> {

	/**
	 * 新增部门
	 * @param record
	 * @return
	 */
	//public int insert(Org record);

	public int insertSelective(Org record);

	/**
	 * 根据部门编号查部门信息
	 * @param orgId
	 * @return
	 */
	public Org selectOrgById(Long orgId);

	/**
	 * 查询所有部门
	 * @return
	 */
	public List<Org> selectAllOrg();

	/**
	 * 更新部门信息
	 * @param org
	 */
	public void update(Org org);

	/**
	 * 根据父部门ID查询所有子部门
	 * @param orgId
	 * @return
	 */
	public List<Org> findOrgByParentId(Long orgId);
	
	/**
	 * 根据部门编号查询部门下人员
	 * @param orgCode
	 * @return
	 */
	public List<Map<String, Object>> findUserByOrg(String orgCode);
}
