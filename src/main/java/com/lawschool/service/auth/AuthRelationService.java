package com.lawschool.service.auth;

import com.baomidou.mybatisplus.service.IService;
import com.lawschool.beans.auth.AuthRelationBean;

import java.util.Map;

/**
 * InterfaceName: AuthRelationService
 * Description: TODO
 * date: 2018-12-5 16:22
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
public interface AuthRelationService extends IService<AuthRelationBean> {

    /**
     * @Author MengyuWu
     * @Description 权限新增
     * @Date 16:22 2018-12-27
     * @Param [deptIdArr, userIdAr, functionId, functionFlag, operaUserId]
     * @return void
     **/
    
    void insertAuthRelation(String[] deptIdArr,String[] userIdAr,String functionId,String functionFlag,String operaUserId);

    /**
     * @Author MengyuWu
     * @Description 查询部门数组
     * @Date 16:22 2018-12-27
     * @Param [functionId, functionFlag]
     * @return java.lang.String[]
     **/
    
    String[] getDeptIdArr(String functionId,String functionFlag);
    
    /**
     * @Author MengyuWu
     * @Description 查询人员数组
     * @Date 16:22 2018-12-27
     * @Param [functionId, functionFlag]
     * @return java.lang.String[]
     **/
    
    String[] getUserIdArr(String functionId,String functionFlag);

    /**
     * @Author MengyuWu
     * @Description 查询功能表id
     * @Date 10:44 2019-1-16
     * @Param [deptId, userId, functionFlag]
     * @return java.lang.String[]
     **/

    String [] listAllIdByUser(String deptId,String userId,String functionFlag);
}
