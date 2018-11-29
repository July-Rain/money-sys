package com.lawschool.service;


import com.baomidou.mybatisplus.service.IService;
import com.lawschool.beans.SysLogEntity;
import com.lawschool.util.PageUtils;

import java.util.Map;


public interface SysLogService extends IService<SysLogEntity> {

    PageUtils queryPage(Map<String, Object> params);

}
