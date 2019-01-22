package com.lawschool.service.impl.auth;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lawschool.beans.auth.AuthRelationBean;
import com.lawschool.dao.auth.AuthRelationDao;
import com.lawschool.service.auth.AuthRelationService;
import com.lawschool.util.GetUUID;
import com.lawschool.util.UtilValidate;
import org.springframework.stereotype.Service;

import java.util.*;

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
        if(UtilValidate.isNotEmpty(deptIdArr)){
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

        }

        //设置适用人员权限
        if(UtilValidate.isNotEmpty(userIdArr)){
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
        }

        if(UtilValidate.isNotEmpty(authRelationBeanList)){
            this.insertBatch(authRelationBeanList);
        }
    }

    @Override
    public String[] getDeptIdArr(String functionId, String functionFlag) {
        String [] deptIdArr=null;
        List<AuthRelationBean> deptAuth = this
                .selectList(new EntityWrapper<AuthRelationBean>()
                        .setSqlSelect("auth_id")
                        .eq("function_flag",functionFlag)
                        .eq("function_id",functionId).eq("auth_type","dept"));
        if(UtilValidate.isNotEmpty(deptAuth)){
            deptIdArr= new String[deptAuth.size()] ;
            for(int i=0;i<deptAuth.size();i++){
                deptIdArr[i]=deptAuth.get(i).getAuthId();
            }
        }
        return deptIdArr;
    }

    @Override
    public String[] getUserIdArr(String functionId, String functionFlag) {
        //获取适用人的id
        String [] userIdArr=null;
        List<AuthRelationBean> userAuth = this
                .selectList(new EntityWrapper<AuthRelationBean>()
                        .setSqlSelect("auth_id")
                        .eq("function_flag",functionFlag)
                        .eq("function_id",functionId).eq("auth_type","user"));
        if(UtilValidate.isNotEmpty(userAuth)){
            //把适用人id放在实体中
            userIdArr= new String[userAuth.size()] ;
            for(int i=0;i<userAuth.size();i++){
                userIdArr[i]=userAuth.get(i).getAuthId();
            }
        }
        return userIdArr;
    }

    @Override
    public String[] listAllIdByUser(String deptId, String userId, String functionFlag) {
        Map<String,String> params=new HashMap<>();
        params.put("deptId",deptId);
        params.put("userId",userId);
        params.put("functionFlag",functionFlag);
        return baseMapper.listAllIdByUser(params);
    }
}
