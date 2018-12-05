package com.lawschool.service.impl.auth;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lawschool.beans.SysMenu;
import com.lawschool.beans.auth.AuthRelationBean;
import com.lawschool.dao.SysMenuDao;
import com.lawschool.dao.auth.AuthRelationDao;
import com.lawschool.service.SysMenuService;
import com.lawschool.service.auth.AuthRelationService;
import com.lawschool.util.GetUUID;
import com.lawschool.util.UtilValidate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @Override
    public void insertAuthRelation(String[] deptIdArr, String[] userIdArr,String functionId,String functionFlag,String operaUserId) {
        List<AuthRelationBean> authRelationBeanList =new ArrayList<>();
        //设置适用部门权限
        for(String deptId:deptIdArr){
            AuthRelationBean relationBean = new AuthRelationBean();
            relationBean.setId(GetUUID.getUUIDs("AU"));
            relationBean.setAuthId(deptId);
            relationBean.setAuthType("dept");
            relationBean.setFunctionFlag(functionFlag);
            relationBean.setFunctionId(functionId);
            relationBean.setCreateTime(new Date());
            relationBean.setCreateUser(operaUserId);
            authRelationBeanList.add(relationBean);
        }

        //设置适用人员权限
        for(String userId:userIdArr){
            AuthRelationBean relationBean = new AuthRelationBean();
            relationBean.setId(GetUUID.getUUIDs("AU"));
            relationBean.setAuthId(userId);
            relationBean.setAuthType("user");
            relationBean.setFunctionFlag(functionFlag);
            relationBean.setFunctionId(functionId);
            relationBean.setCreateTime(new Date());
            relationBean.setCreateUser(userId);
            authRelationBeanList.add(relationBean);
        }
        if(UtilValidate.isNotEmpty(authRelationBeanList)){
            this.insertBatch(authRelationBeanList);
        }
    }
}
