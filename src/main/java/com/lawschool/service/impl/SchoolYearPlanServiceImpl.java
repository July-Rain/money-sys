package com.lawschool.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.DailyQuestionConfiguration;
import com.lawschool.beans.SchoolYearPlan;
import com.lawschool.beans.User;
import com.lawschool.dao.SchoolYearPlanDao;
import com.lawschool.service.SchoolYearPlanService;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @ClassName SchoolYearPlanServiceImpl
 * @Author wangtongye
 * @Date 2018/12/26 14:52
 * @Versiom 1.0
 **/
@Service
public class SchoolYearPlanServiceImpl extends AbstractServiceImpl<SchoolYearPlanDao, SchoolYearPlan> implements SchoolYearPlanService {

    public SchoolYearPlan findYearPlanByNow(String userId){
        return dao.findYearPlanByNow(userId);
    }

    @Override
    public int insertsave(SchoolYearPlan schoolYearPl) {
        int i=0;
//        保存前  先判断时间区间有没有重叠
        List<SchoolYearPlan> list= this.selectList(new EntityWrapper<SchoolYearPlan>());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Boolean b=true;

        //判断  这是  修改  还是  新增
//        if(schoolYearPl.getId()==null){
        //        循环list的得到实体
                for(SchoolYearPlan schoolYearPlan:list)
                {
                    try{
                        String s1 = sdf.format( schoolYearPlan.getStartDate());
                        Date beginTime =  sdf.parse(s1);
                        String s2 = sdf.format( schoolYearPlan.getEndDate());
                        Date endTime =  sdf.parse(s2);
                        if(schoolYearPl.getId()!=null){
                            if(schoolYearPlan.getId().equals(schoolYearPl.getId())){
                                continue;
                            }
                        }
                        if ( (sdf.parse(sdf.format(schoolYearPl.getEndDate())).getTime()< beginTime.getTime()) || (sdf.parse(sdf.format(schoolYearPl.getStartDate())).getTime()> endTime.getTime()))
                        {
                            //无交集
                        }
                        else
                        {
                            //有交集

                            b=false;
                            break;
                        }

                    }catch (Exception e)
                    {
                        System.out.println(e);
                    }
                }
                if(b==true)
                {

                    if(schoolYearPl.getId()!=null){

                        dao.updateById(schoolYearPl);
                        i=1;
                    }
                    else{
                        //没交集 正常添加
                        User u = (User) SecurityUtils.getSubject().getPrincipal();
                        schoolYearPl.setCreateUser(u.getId());
                        schoolYearPl.setId(IdWorker.getIdStr());
                        dao.insert(schoolYearPl);
                        i=1;
                    }

                }
                else if(b==false)
                {
                    //有交集  不能添加
                    i=0;
                }
//        }
        // 修改
//        else{
//            for(SchoolYearPlan schoolYearPlan:list)
//            {
//                try{
//                    String s1 = sdf.format( schoolYearPlan.getStartDate());
//                    Date beginTime =  sdf.parse(s1);
//                    String s2 = sdf.format( schoolYearPlan.getEndDate());
//                    Date endTime =  sdf.parse(s2);
//
//                    if ( (sdf.parse(sdf.format(schoolYearPlan.getEndDate())).getTime()< beginTime.getTime()) || (sdf.parse(sdf.format(schoolYearPlan.getStartDate())).getTime()> endTime.getTime()))
//                    {
//                        //无交集
//                    }
//                    else
//                    {
//                        //有交集
//
//                        b=false;
//                        break;
//                    }
//
//                }catch (Exception e)
//                {
//                    System.out.println(e);
//                }
//            }
//        }
        return i;
    }
}
