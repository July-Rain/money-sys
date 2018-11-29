package com.lawschool.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lawschool.beans.SysLogEntity;
import com.lawschool.dao.SysLogMapper;
import com.lawschool.service.SysLogService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Query;
import com.lawschool.util.UtilValidate;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLogEntity> implements SysLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String)params.get("key");
        EntityWrapper<SysLogEntity> ew = new EntityWrapper<>();

        if(UtilValidate.isNotEmpty(key)){
            key="%"+key+"%";
            ew .addFilter("username like {0} or operation like {1}",key,key);
        }
        Page<SysLogEntity> page = this.selectPage(
            new Query<SysLogEntity>(params).getPage(),ew);

        return new PageUtils(page);
    }
}
