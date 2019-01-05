package com.lawschool.service;

import com.baomidou.mybatisplus.service.IService;
import com.lawschool.beans.SysRoleOrg;

import java.util.List;

/**
 * InterfaceName: SysRoleOrgService
 * Description: TODO
 * date: 2018-11-29 15:35
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
public interface SysRoleOrgService extends IService<SysRoleOrg> {

    /**
     * 根据角色查询所有数据权限ID
     * @param roleId
     * @return
     */
    List<String> queryOrgIdList(String roleId);
}
