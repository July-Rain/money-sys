package com.lawschool.service;

import com.baomidou.mybatisplus.service.IService;
import com.lawschool.beans.RecruitConfiguration;
import com.lawschool.beans.SysMenu;

import java.util.List;
import java.util.Map;


/**
 * 
 * @Descriptin  闯关配置service接口
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
public interface RecruitConfigurationService  extends IService<RecruitConfiguration> {

    public List<RecruitConfiguration> findAll();



    public RecruitConfiguration info(String id);

    public void save();

}
