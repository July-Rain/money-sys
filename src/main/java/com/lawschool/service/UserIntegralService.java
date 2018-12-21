package com.lawschool.service;

import com.lawschool.beans.User;
import com.lawschool.beans.UserIntegral;
import com.lawschool.util.Result;

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
     * @Description 获取某用户的积分详情
     * @Date 18:16 2018/12/20
     * @Param [param]
    **/
    UserIntegral getInfo(User user);

}
