package com.lawschool.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.beans.SysConfig;
import com.lawschool.dao.SysConfigDao;
import com.lawschool.service.SysConfigService;
import com.lawschool.util.GetUUID;
import com.lawschool.util.UtilValidate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * ClassName: SysConfigServiceImpl
 * Description: TODO
 * date: 2018-11-28 17:17
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
@Service
public class SysConfigServiceImpl implements SysConfigService {
    @Resource
    private SysConfigDao configMapper;
    @Override
    public String getValue(String code) {
        SysConfig config = configMapper.selectByCode(code);
        if(UtilValidate.isNotEmpty(config)){
            return config.getValue();
        }
        return "";
    }

    @Override
    public int insertConfig(SysConfig config) {
        config.setId(GetUUID.getUUIDs("SC"));
        return configMapper.insert(config);
    }

    @Override
    public int updateConfig(SysConfig config) {
        return configMapper.updateByPrimaryKeySelective(config);
    }

    @Override
    public List<SysConfig> listConfig(SysConfig config) {
        return configMapper.selectList(new EntityWrapper<>());
    }

    @Override
    public int deleteConfig(SysConfig sysConfig) {
        return configMapper.deleteByPrimaryKey(sysConfig.getId());
    }

    @Override
    public SysConfig queryConfig(SysConfig sysConfig) {
        return configMapper.selectByPrimaryKey(sysConfig.getId());
    }
}
