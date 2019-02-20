package com.lawschool.service;

import com.lawschool.beans.CrdStatOrg;
import com.lawschool.beans.ItrStatOrg;
import com.lawschool.beans.User;
import com.lawschool.beans.UserIntegral;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;

import java.util.List;
import java.util.Map;

/**
 * @Description: 用户积分学分表
 * @Author:zjw
 * @Date:Create in 18:01 2018/12/20
 * @Modifid By:
 */
public interface UserIntegralService {
    
    /**
     * @Author zjw
     * @Description 获取某用户的积分学分详情
     * @Date 18:16 2018/12/20
     * @Param [param]
    **/
    UserIntegral getInfo(User user);


    /**
     * @Author zjw
     * @Description  学分 人员
     * @Date 11:20 2018-12-29
     * @Param [orgCode]
     * @return java.util.List<com.lawschool.beans.UserIntegral>
    **/
     PageUtils crdStatUser(Map<String,Object> map);



     /**
      * @Author zjw
      * @Description 学分 部门
      * @Date 15:13 2019-1-3
      * @Param []
      * @return java.util.List<com.lawschool.beans.CrdStatOrg>
     **/
     List<CrdStatOrg> crdStatOrg(Map<String,Object> param);


    /**
     * @Author zjw
     * @Description  积分 人员
     * @Date 11:20 2018-12-29
     * @Param [orgCode]
     * @return java.util.List<com.lawschool.beans.UserIntegral>
     **/
    PageUtils itrStatUser(Map<String,Object> map);



    /**
     * @Author zjw
     * @Description 积分 部门
     * @Date 15:13 2019-1-3
     * @Param []
     * @return java.util.List<com.lawschool.beans.ItrStatOrg>
     **/
    List<ItrStatOrg> itrStatOrg(Map<String,Object> param);

     /**
      * @Author zjw
      * @Description 模糊查询某些用户的积分学分详情
      * @Date 13:50 2018-12-29
      * @Param
      * @return
     **/
     PageUtils list(Map<String,Object> map);

     int getJifenByUserId(String userid);
}
