package com.lawschool.service.impl.auth;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lawschool.beans.SysMenu;
import com.lawschool.beans.auth.AuthRelationBean;
import com.lawschool.dao.SysMenuDao;
import com.lawschool.dao.auth.AuthRelationDao;
import com.lawschool.service.SysMenuService;
import com.lawschool.service.auth.AuthRelationService;
import org.springframework.stereotype.Service;

/**
 * ClassName: AuthRelationServiceImpl
 * Description: TODO
 * date: 2018-12-5 16:20
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
@Service
public class AuthRelationServiceImpl extends ServiceImpl<AuthRelationDao, AuthRelationBean> implements AuthRelationService {
}
