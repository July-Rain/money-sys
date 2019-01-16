package com.lawschool.dao.auth;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lawschool.beans.auth.AuthRelationBean;

import java.util.List;
import java.util.Map;

/**
 * ClassName: AuthRelationDao
 * Description: 权限dao
 * date: 2018-12-5 16:15
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
public interface AuthRelationDao  extends BaseMapper<AuthRelationBean> {
    /**
     * @Author MengyuWu
     * @Description 查询功能表id
     * @Date 10:43 2019-1-16
     * @Param [param]
     * @return java.lang.String[]
     **/
    
    String [] listAllIdByUser(Map<String,String> param);
}
