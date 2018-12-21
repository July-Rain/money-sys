package com.lawschool.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.Integral;
import com.lawschool.beans.User;
import com.lawschool.beans.UserIntegral;
import com.lawschool.dao.IntegralDao;
import com.lawschool.dao.UserIntegralDao;
import com.lawschool.service.UserIntegralService;
import com.lawschool.service.UserService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;
import com.lawschool.util.UtilValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
