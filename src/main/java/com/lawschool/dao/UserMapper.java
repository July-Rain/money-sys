package com.lawschool.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.lawschool.base.AbstractDao;
import com.lawschool.beans.User;
import com.lawschool.beans.UserExample;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.lawschool.beans.diagnosis.StuDiagnosisEntity;
import org.apache.ibatis.annotations.Param;

public interface UserMapper  extends AbstractDao<User> {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(BigDecimal id);

    //int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(BigDecimal id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);



    //查询用户所有的菜单
    List<String> queryAllPerms(String userId);


    List<Long> queryAllMenuId(Long userId);


    /**
     * @Author MengyuWu
     * @Description 获取人员数据
     * @Date 17:19 2019-2-20
     * @Param [page, param]
     * @return java.util.List<com.lawschool.beans.User>
     **/
    
    List<User> selectAllUsers(Page page, Map<String,String> param);

    /**
     * @Author MengyuWu
     * @Description 获取个数
     * @Date 17:19 2019-2-20
     * @Param [param]
     * @return int
     **/
    
    int countUser(Map<String,String> param);
}