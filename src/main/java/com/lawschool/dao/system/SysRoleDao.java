package com.lawschool.dao.system;

import java.util.List;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.system.SysRoleEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @Descriptin  用户dao
 * @author      张奇
 * @version     v1.0
 * @Time        2018/11/28
 *
 */
public interface SysRoleDao extends AbstractDao<SysRoleEntity> {

    /**
     * @Author MengyuWu
     * @Description 根据用户的id查询用户的角色
     * @Date 14:33 2018-11-29
     * @Param [userId]
     * @return java.util.List<com.lawschool.beans.system.SysRoleEntity>
     **/
    
    List<SysRoleEntity> listRoleByUserId(String userId);

}
