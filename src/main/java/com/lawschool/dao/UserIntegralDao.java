package com.lawschool.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lawschool.base.AbstractDao;
import com.lawschool.beans.User;
import com.lawschool.beans.UserIntegral;

public interface UserIntegralDao extends AbstractDao<UserIntegral> {

    UserIntegral getInfo(User user);
}