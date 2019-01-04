package com.lawschool.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.lawschool.base.AbstractDao;
//import com.lawschool.beans.CrdStatOrg;
import com.lawschool.beans.User;
import com.lawschool.beans.UserIntegral;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserIntegralDao extends AbstractDao<UserIntegral> {

    UserIntegral getInfo(User user);

    //学分 人员维度
    List<UserIntegral> crdStatUser(Pagination page,@Param("orgCode") String orgCode);

    //学分 部门维度
//    List<CrdStatOrg> crdStatOrg();
}