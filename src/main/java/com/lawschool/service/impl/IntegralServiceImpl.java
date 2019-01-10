package com.lawschool.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lawschool.annotation.SysLog;
import com.lawschool.base.AbstractService;
import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.Integral;
import com.lawschool.beans.User;
import com.lawschool.beans.UserIntegral;
import com.lawschool.dao.IntegralDao;
import com.lawschool.dao.UserIntegralDao;
import com.lawschool.service.IntegralService;
import com.lawschool.service.UserIntegralService;
import com.lawschool.util.GetUUID;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;
import com.lawschool.util.UtilValidate;
import com.sun.net.httpserver.Authenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.lawschool.util.Constant.SUCCESS;
import static java.lang.Integer.parseInt;

/**
 * @author zjw
 * @Title: IntegralServiceImpl
 * @ProjectName law_school
 * @Description: TODO
 * @date 2018/12/2019:31
 */
@Service
public class IntegralServiceImpl extends AbstractServiceImpl<IntegralDao, Integral> implements IntegralService {

    @Autowired
    private IntegralDao integralDao;

    @Autowired
    private UserIntegralDao userIntegralDao;

    /**
     * @Author zjw
     * @Description 获取用户的积分学分记录
     * @Date 19:39 2018/12/20
     * @Param [param, user]
     * @return com.lawschool.util.PageUtils
    **/
    @Override
    public PageUtils list(Map<String, Object> param, User user) {
        int pageNo= parseInt(Optional.ofNullable(param.get("currPage")).orElse("1").toString());
        int pageSize= parseInt(Optional.ofNullable(param.get("pageSize")).orElse("10").toString());

        String startTime=(String)param.get("startTime");
        String endTime=(String)param.get("endTime");
        String type=(String)param.get("type");

        Page<Integral> page=new Page<Integral>(pageNo,pageSize);
        EntityWrapper<Integral> ew=new EntityWrapper<>();
        ew.eq("USER_ID", user.getId());

        ew.addFilterIfNeed(UtilValidate.isNotEmpty(startTime),"(CREATE_TIME >= TO_DATE('"+startTime+"', 'yyyy-mm-dd'))");
        ew.addFilterIfNeed(UtilValidate.isNotEmpty(endTime),"(CREATE_TIME <= TO_DATE('"+endTime+"', 'yyyy-mm-dd'))");
        ew.eq(UtilValidate.isNotEmpty(type),"TYPE",type);
        ew.orderBy("CREATE_TIME", false);

        List<Integral> integrals = integralDao.selectPage(page, ew);
        PageUtils pages=new PageUtils(integrals, integrals.size(), pageSize, pageNo);
        return pages;
    }

    /**
     * @Author zjw
     * @Description 添加用户积分学分记录
     * @Date 23:01 2018/12/20
     * @Param [Integral, user]
     * @return int
    **/
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addIntegralRecord(Integral integral, User user) {
        String userId=user.getId();
        String type=integral.getType();
        int point=integral.getPoint();
        //1.添加积分学分记录

        integral.setUserId(userId);
        integral.setCreateTime(new Date());
        integral.setCreateUser(userId);
        integral.setId(GetUUID.getUUIDs("IG"));


        integralDao.insert(integral);

        //2.更新用户积分学分表
//        UserIntegral userIntegral=new UserIntegral();
//        userIntegral.setUserId(userId);
        List<UserIntegral> userIntegral = userIntegralDao.selectList(new EntityWrapper<UserIntegral>().eq("USER_ID",userId));

        //原来就有
        if(userIntegral.size()>0){
            if("0".equalsIgnoreCase(type)){
                userIntegral.get(0).setIntegralPoint(userIntegral.get(0).getIntegralPoint()+point);
            }else if("1".equalsIgnoreCase(type)){
                userIntegral.get(0).setCreditPoint(userIntegral.get(0).getCreditPoint()+point);
            }

            userIntegralDao.update(userIntegral.get(0),new EntityWrapper<UserIntegral>().eq("USER_ID", userId));
        }else{
            UserIntegral userIntegral2=new UserIntegral();
            if("0".equalsIgnoreCase(type)){
                userIntegral2.setIntegralPoint(point);
            }else if("1".equalsIgnoreCase(type)){
                userIntegral2.setCreditPoint(point);
            }
            userIntegral2.setOrgCode(user.getOrgCode());
            userIntegral2.setFullName(user.getFullName());
            userIntegral2.setUserCode(user.getUserCode());
            userIntegral2.setOrgName(user.getOrgName());
            userIntegral2.setUserId(userId);
            userIntegral2.setId(GetUUID.getUUIDs("UIG"));
            userIntegralDao.insert(userIntegral2);
        }

        return SUCCESS;
    }
}
