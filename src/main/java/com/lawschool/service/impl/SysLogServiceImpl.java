package com.lawschool.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lawschool.beans.SysLogEntity;
import com.lawschool.dao.SysLogDao;
import com.lawschool.service.SysLogService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Query;
import com.lawschool.util.UtilValidate;
import org.springframework.stereotype.Service;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;


@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLogEntity> implements SysLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String username = (String)params.get("username");
        String operation = (String)params.get("operation");
        String startTime = (String)params.get("startTime");
        String endTime = (String)params.get("endTime");
        EntityWrapper<SysLogEntity> ew = new EntityWrapper<>();

        if(UtilValidate.isNotEmpty(username)){
            ew .like("username",username);
        }
        if(UtilValidate.isNotEmpty(operation)){
            ew .like("operation",operation);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(UtilValidate.isNotEmpty(startTime)){
            Date startParse = null;
            try {
                startParse = sdf.parse(startTime);
                ew.ge("create_date", startParse);
            } catch (ParseException e) {
                throw new RuntimeException();
            }
        }
        if(UtilValidate.isNotEmpty(endTime)){
            Date endParse = null;
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Calendar c = Calendar.getInstance();
                c.setTime(format.parse(endTime));
                Date tomorrow = c.getTime();
                endParse = tomorrow;
                //以开始时间搞
                ew.le("create_date", endParse);
            } catch (ParseException e) {
                throw new RuntimeException();
            }
        }
        ew.orderBy("create_date",false);
        Page<SysLogEntity> page = this.selectPage(
            new Query<SysLogEntity>(params).getPage(),ew);

        return new PageUtils(page);
    }
}
