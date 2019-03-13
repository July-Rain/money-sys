package com.lawschool.service;

import com.baomidou.mybatisplus.service.IService;
import com.lawschool.beans.Integral;
import com.lawschool.beans.User;
import com.lawschool.beans.diagnosis.OrgDiagnosisEntity;
import com.lawschool.beans.learn.StuRecordEntity;
import com.lawschool.beans.vo.OrgCompetitionVO;
import com.lawschool.util.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * @Author zjw
 * @Description 积分学分记录
 * @Date 19:30 2018/12/20
 * @Param 
 * @return 
**/
public interface IntegralService extends IService<Integral> {
    /**
     * @Author zjw
     * @Description 获取用户的积分学分记录
     * @Date 22:21 2018/12/20
     * @Param [param, user]
     * @return com.lawschool.util.PageUtils
    **/
    PageUtils list(Map<String, Object> param, User user);
    
    /**
     * @Author zjw
     * @Description 添加用户积分学分记录
     * @Date 23:01 2018/12/20
     * @Param [Integral, user]
     * @return int
    **/
    int addIntegralRecord(Integral integer, User user);

    int chuangguanCountByUser(String uid);

    int pkByUser(String uid);
    int leitaiByUser(String uid);



    PageUtils userByDeptList(Map<String, Object> params, String deptcode);

    List<OrgCompetitionVO> orgDiaStat(Map<String,String> param);

    /**
     * @Author MengyuWu
     * @Description 根据当前登陆人信息 统计当天某个类型下的积分或者学分的总和
     * @Date 10:13 2019-3-12
     * @Param [integral]
     * @return java.lang.Float
     **/
    
    Float sumPointByUser(Integral integral);

}
