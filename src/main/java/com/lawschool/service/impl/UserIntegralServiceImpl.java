package com.lawschool.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.*;
import com.lawschool.dao.IntegralDao;
import com.lawschool.dao.UserIntegralDao;
import com.lawschool.service.UserIntegralService;
import com.lawschool.service.UserService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Query;
import com.lawschool.util.Result;
import com.lawschool.util.UtilValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.lang.Integer.parseInt;

/**
 * @Description:用户积分学分
 * @Author:Zjw
 * @Date:Create in 18:04 2018/12/20
 * @Modifid By:
 */
@Service
public class UserIntegralServiceImpl extends AbstractServiceImpl<UserIntegralDao, UserIntegral> implements UserIntegralService {

    @Autowired
    private UserIntegralDao userIntegralDao;

    /**
     * @Author zjw
     * @Description 获取某用户分级学分排名等总体信息
     * @Date 19:46 2018/12/20
     * @Param [user]
     * @return com.lawschool.beans.UserIntegral
    **/
    @Override
    public UserIntegral getInfo(User user) {
        UserIntegral info = userIntegralDao.getInfo(user);
        return info;
    }

    /**
     * @Author zjw
     * @Description 学分 人员 
     * @Date 11:21 2018-12-29
     * @Param [orgCode]
     * @return java.util.List<com.lawschool.beans.UserIntegral>
    **/
    @Override
    public PageUtils crdStatUser(Map<String,Object> param) {

        int pageNo= parseInt(Optional.ofNullable(param.get("currPage")).orElse("1").toString());
        int pageSize= parseInt(Optional.ofNullable(param.get("pageSize")).orElse("10").toString());
        Page<UserIntegral> page=new Page(pageNo,pageSize);
        page.setRecords(userIntegralDao.crdStatUser(page,param.get("orgCode").toString()));
        page.setTotal(userIntegralDao.selectCount(new EntityWrapper<UserIntegral>().eq("ORG_CODE", param.get("orgCode").toString())));
        return new PageUtils(page);
    }

    /**
     * @Author zjw
     * @Description 学分 部门
     * @Date 15:14 2019-1-3
     * @Param []
     * @return java.util.List<com.lawschool.beans.CrdStatOrg>
    **/
    @Override
    public List<CrdStatOrg> crdStatOrg() {
        return userIntegralDao.crdStatOrg();
    }



    /**
     * @Author zjw
     * @Description 积分 人员 
     * @Date 11:21 2018-12-29
     * @Param [orgCode]
     * @return java.util.List<com.lawschool.beans.UserIntegral>
     **/
    @Override
    public PageUtils itrStatUser(Map<String, Object> param) {
        int pageNo= parseInt(Optional.ofNullable(param.get("currPage")).orElse("1").toString());
        int pageSize= parseInt(Optional.ofNullable(param.get("pageSize")).orElse("10").toString());
        Page<UserIntegral> page=new Page(pageNo,pageSize);
        page.setRecords(userIntegralDao.itrStatUser(page,param.get("orgCode").toString()));
        page.setTotal(userIntegralDao.selectCount(new EntityWrapper<UserIntegral>().eq("ORG_CODE", param.get("orgCode").toString())));
        return new PageUtils(page);
    }

    @Override
    public List<ItrStatOrg> itrStatOrg() {
        return userIntegralDao.itrStatOrg();
    }

    @Override
    public PageUtils list(Map<String,Object> param) {

       // int pageNo= parseInt(Optional.ofNullable(param.get("currPage")).orElse("1").toString());
        //int pageSize= parseInt(Optional.ofNullable(param.get("pageSize")).orElse("10").toString());

        String userCode=(String)param.get("userCode");
        String fullName=(String)param.get("fullName");

        //Page page=new Page(pageNo,pageSize);
        EntityWrapper<UserIntegral> ew=new EntityWrapper<>();
        if(UtilValidate.isNotEmpty(userCode)){
            ew.like("USER_CODE", userCode);
        }
        if(UtilValidate.isNotEmpty(fullName)){
            ew.like("FULL_NAME", fullName);
        }
        Page page1 = this.selectPage(new Query<UserIntegral>(param).getPage(), ew);

        List<UserIntegral> lst=page1.getRecords();

        User user=new User();
        List<UserIntegral> rst=new ArrayList<>();
        if(UtilValidate.isNotEmpty(lst)){
            lst.stream().forEach(e->{
                user.setOrgCode(e.getOrgCode());
                user.setId(e.getUserId());
                rst.add(userIntegralDao.getInfo(user));
            });
        }

        page1.setRecords(rst);
        PageUtils pageUtils=new PageUtils(page1);
        return pageUtils;
    }


}
