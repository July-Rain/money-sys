package com.lawschool.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lawschool.beans.SysRoleOrg;
import com.lawschool.dao.SysRoleOrgDao;
import com.lawschool.service.SysRoleOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: SysRoleOrgServiceImpl
 * Description: TODO
 * date: 2018-11-29 15:35
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
@Service
public class SysRoleOrgServiceImpl extends ServiceImpl<SysRoleOrgDao,SysRoleOrg> implements SysRoleOrgService {

    @Autowired
    private SysRoleOrgDao dao;

    public List<String> queryOrgIdList(String roleId){
        return dao.queryOrgIdList(roleId);
    }
}
