package com.lawschool.config;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.beans.*;
import com.lawschool.dao.*;
import com.lawschool.util.Constant;
import com.lawschool.util.UtilValidate;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zjw
 * @Title: UserRealm
 * @ProjectName law_school
 * @Description: TODO
 * @date 2018-12-2710:29
 */
@Component("userRealm")
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserMapper sysUserDao;
    @Autowired
    private SysMenuDao sysMenuDao;

    @Autowired
    private OrgDao orgDao;

    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    @Autowired
    private SysRoleOrgDao sysRoleOrgDao;


    @PostConstruct
    public void initCredentialsMatcher() {
        //该句作用是重写shiro的密码验证，让shiro用我自己的验证
        setCredentialsMatcher(new UserCredentialsMatcher());

    }


    //权限验证
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = (User) principals.getPrimaryPrincipal();
        String userId = user.getUserId();

        List<String> permsList;

        //系统管理员，拥有最高权限
        if (user.getIsAdmin() == Constant.SUPER_ADMIN) {
            //所有菜单
            List<SysMenu> menuList = sysMenuDao.selectList(null);
            permsList = new ArrayList<>(menuList.size());
            for (SysMenu menu : menuList) {
                permsList.add(menu.getPerms());
            }
        } else {
            permsList = sysUserDao.queryAllPerms(userId);
        }

        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for (String perms : permsList) {
            if (StringUtils.isBlank(perms)) {
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }


    //登陆验证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

        //查询用户信息
        List<User> userList = sysUserDao.selectList((new EntityWrapper<User>())
                .where("USER_CODE={0}", token.getUsername()));
        User user = null;
        if (UtilValidate.isNotEmpty(userList)) {
            user = userList.get(0);
        }
        //账号不存在
        if (user == null) {
            throw new UnknownAccountException("账号或密码不正确");
        }

        //用户部门id列表
        List<String> deptIdList = new ArrayList<>();
        //管理员
        if (user.getIsAdmin() == Constant.SUPER_ADMIN) {
            List<Org> sysDeptEntities = orgDao.selectList(new EntityWrapper<Org>());
            sysDeptEntities.stream().map(dept -> dept.getId()).collect(Collectors.toSet());

//			deptIdList.stream().forEach(deptId -> {System.out.println(deptId);});
        } else {
            //用户角色对应的部门ID列表

            //用户-》角色
            List<String> roleIdList = sysUserRoleDao.selectList(new EntityWrapper<SysUserRole>().eq("USER_ID", user.getId()))
                    .stream().map(r->r.getRoleId()).collect(Collectors.toList());
            user.setRoleIdList(roleIdList);

            //角色对应的部门以及子部门org_id列表
            if (roleIdList.size() > 0) {
                //角色-》部门
                List<String> userDeptIdList = sysRoleOrgDao.selectList(new EntityWrapper<SysRoleOrg>().in("ROLE_ID", roleIdList)).stream().map(r -> r.getOrgId()).collect(Collectors.toList());


                for (String deptId : userDeptIdList) {
                    //角色关联的部门下的子部门ID列表
                    List<String> subDeptIdList = orgDao.getSubDeptIdList(deptId);
                    deptIdList.addAll(subDeptIdList);
                }
                deptIdList.addAll(userDeptIdList);
            }

            //用户对应的部门以及子部门org_id列表
            List<String> subDeptIdList = orgDao.getSubDeptIdList(user.getOrgId());
            deptIdList.addAll(subDeptIdList);
        }

        user.setOrgIdList(deptIdList);


        ////登录识别串信息  ////密码   //盐值    //realm name
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), getName());
        return info;
    }



}
