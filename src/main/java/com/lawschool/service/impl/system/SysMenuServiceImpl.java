package com.lawschool.service.impl.system;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.User;
import com.lawschool.beans.system.SysMenuEntity;
import com.lawschool.dao.UserMapper;
import com.lawschool.dao.system.SysMenuDao;
import com.lawschool.form.CommonForm;
import com.lawschool.form.TreeForm;
import com.lawschool.service.SysRoleMenuService;
import com.lawschool.service.system.SysMenuService;
import com.lawschool.util.Constant;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Auther: Moon
 * @Date: 2019/1/16 19:31
 * @Description:
 */
@Service("menuService")
public class SysMenuServiceImpl extends AbstractServiceImpl<SysMenuDao, SysMenuEntity> implements SysMenuService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Override
    public List<SysMenuEntity> findList(String parentId){
        SysMenuEntity entity = new SysMenuEntity();
        if(StringUtils.isBlank(parentId)){
            parentId = "0";
        }
        entity.setParentId(parentId);

        return dao.findList(entity);
    }

    /**
     * 此方法只展示2层结构，点第二层目录时需要重新请求
     * @param userId
     * @return
     */
    public List<SysMenuEntity> userMenu(String userId, List<String> parentIds){
        List<SysMenuEntity> resultList = new ArrayList<SysMenuEntity>();

        // 存放查询参数，角色IDS
        List<String> params = new ArrayList<String>();

        User user = userMapper.findOne(userId);
        if(user == null){
            throw new RuntimeException("用户信息获取失败，请重新登录");
        }

        // 非超级管理员，关联角色菜单
        if(user.getIsAdmin() != Constant.SUPER_ADMIN){
            String roles = user.getRoles();
            if(StringUtils.isBlank(roles)){
                // 没有角色，返回空
                return resultList;
            } else {
                params = Arrays.asList(roles.split(","));
            }
        }

        List<SysMenuEntity> menuList = dao.findUserMenu(params, parentIds);

        // 获取子集
        menuList = this.handleList(menuList, params);

        return menuList;
    }

    private List<SysMenuEntity> handleList(List<SysMenuEntity> list, List<String> roleList){

        List<String> idList = new ArrayList<String>(list.size());

        for(SysMenuEntity entity : list){
            idList.add(entity.getId());
        }

        // 顺序为parentId
        List<SysMenuEntity> childList = dao.findUserMenu(roleList, idList);

        // 用于递归
        List<SysMenuEntity> listForRecursion = new ArrayList<>();

        for(SysMenuEntity entity : list){

            List<SysMenuEntity> tempList = new ArrayList<SysMenuEntity>();
            String parentId = entity.getId();

            for(SysMenuEntity child : childList){
                String key = child.getParentId();
                if(parentId.equals(key)){
                    tempList.add(child);
                }
            }

            entity.setList(tempList);
            childList.removeAll(tempList);
            listForRecursion.addAll(tempList);
        }

        if(CollectionUtils.isNotEmpty(listForRecursion)){
            this.handleList(listForRecursion, roleList);
        }

        return list;
    }

    /**
     * 获取所有目录
     * @return
     */
    @Override
    public List<TreeForm> getAllCatalog(List<Integer> typeList){
        List<TreeForm> resultList = new ArrayList<TreeForm>();

        // 此list是按照parentId 升序排序的
        List<TreeForm> list = dao.getAllCatalog(typeList);

        Map<String, TreeForm> formMap = new HashMap<String, TreeForm>();// 存放Form

        Map<String, List<TreeForm>> tempMap = new HashMap<String, List<TreeForm>>();
        for(TreeForm form : list){
            String pid = form.getParentId();// 上级ID
            if("0".equals(pid)){// 一级目录
                resultList.add(form);
            } else {
                List<TreeForm> sets = null;
                if(tempMap.get(pid) != null){
                    sets = tempMap.get(pid);
                } else {
                    sets = new ArrayList<TreeForm>();
                }
                sets.add(form);
                tempMap.put(pid, sets);

                String id = form.getId();
                formMap.put(id, form);
            }
        }

        Set<String> keySet = tempMap.keySet();
        for(String str : keySet){
            TreeForm form = formMap.get(str);
            if(form != null){
                form.setChildren(tempMap.get(str));
            }
        }

        for(TreeForm form : resultList){
            String key = form.getId();
            List<TreeForm> tempList = tempMap.get(key);
            form.setChildren(tempList);
        }

        return resultList;
    }

    @Override
    public List<SysMenuEntity> queryParentById(String id){

        return dao.queryParentById(id);
    }

    /**
     * 获取角色对应的菜单和目录
     * @param roleId
     * @return
     */
    @Override
    public List<SysMenuEntity> findAllByRole(String roleId){
        List<String> roleIds = new ArrayList<>(1);
        roleIds.add(roleId);

        List<SysMenuEntity> list = dao.findUserMenu(roleIds, null);

        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean remove(List<String> ids){
        // 批量删除，并且删除下级目录
        if(CollectionUtils.isEmpty(ids)){
            return true;
        }

        // 获取所有本级和下级IDS
        List<String> list_all = dao.findAllByList(ids);
        // 批量删除
        this.delete(list_all);

        // 删除角色和菜单关联信息
        sysRoleMenuService.deleteByMenuId(list_all);
        return true;
    }
}
