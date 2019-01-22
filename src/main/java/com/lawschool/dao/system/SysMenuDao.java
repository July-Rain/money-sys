package com.lawschool.dao.system;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.system.SysMenuEntity;
import com.lawschool.form.TreeForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther: Moon
 * @Date: 2019/1/16 19:29
 * @Description:
 */
public interface SysMenuDao extends AbstractDao<SysMenuEntity> {

    /**
     * 获取用户菜单
     * @param list 角色IDS
     * @param idList 父级IDS
     * @return
     */
    List<SysMenuEntity> findUserMenu(@Param("list") List<String> list, @Param("idList") List<String> idList);

    /**
     * 获取所有目录
     * @return
     */
    List<TreeForm> getAllCatalog(@Param("list") List<Integer> list);

    List<SysMenuEntity> queryParentById(@Param("id") String id);
}
