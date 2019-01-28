package com.lawschool.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.lawschool.base.AbstractDao;
import com.lawschool.beans.CrdStatOrg;
import com.lawschool.beans.ItrStatOrg;
import com.lawschool.beans.User;
import com.lawschool.beans.UserIntegral;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserIntegralDao extends AbstractDao<UserIntegral> {

    UserIntegral getInfo(User user);

    //学分 人员维度
    List<UserIntegral> crdStatUser(Pagination page,@Param("orgId") String orgId);

    //学分 部门维度
    List<CrdStatOrg> crdStatOrg(@Param("orgId") String orgId);

    //积分 人员维度
    List<UserIntegral> itrStatUser(Pagination page,@Param("orgId") String orgId);


    //积分 部门维度
    List<ItrStatOrg> itrStatOrg(@Param("orgId") String orgId);


}