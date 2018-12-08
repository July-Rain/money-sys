package com.lawschool.service;

import com.baomidou.mybatisplus.service.IService;
import com.lawschool.beans.SysConfig;
import com.lawschool.util.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * InterfaceName: SysConfigService
 * Description: 系统的配置接口
 * date: 17:13 2018-11-28
 * @author MengyuWu
 * @version
 * @since JDK 1.8
 */
public interface SysConfigService extends IService<SysConfig> {

    /**
     * @Author MengyuWu
     * @Description 根据配置的key返回应该的值
     * @Date 17:13 2018-11-28
     * @Param [code]
     * @return java.lang.String
     **/
    
    public String getValue(String code);

    /**
     * @Author MengyuWu
     * @Description 新增配置信息
     * @Date 17:13 2018-11-28
     * @Param [config]
     * @return int
     **/
    
    public int insertConfig(SysConfig config);

    /**
     * @Author MengyuWu
     * @Description 更新配置信息
     * @Date 17:14 2018-11-28
     * @Param [config]
     * @return int
     **/
    
    public int updateConfig(SysConfig config);

    /**
     * @Author MengyuWu
     * @Description 查询列表
     * @Date 17:14 2018-11-28
     * @Param [config]
     * @return java.util.List<com.lawschool.beans.SysConfig>
     **/
    
    public List<SysConfig> listConfig(SysConfig config);

    /**
     * @Author MengyuWu
     * @Description 删除配置
     * @Date 17:14 2018-11-28
     * @Param [sysConfig]
     * @return int
     **/
    
    public int deleteConfig(SysConfig sysConfig);

    /**
     * @Author MengyuWu
     * @Description 查询配置详情
     * @Date 17:27 2018-11-28
     * @Param [sysConfig]
     * @return com.lawschool.beans.SysConfig
     **/
    
    public SysConfig queryConfig(SysConfig sysConfig);

    PageUtils queryPage(Map<String, Object> params);
}
