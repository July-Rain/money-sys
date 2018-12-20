package com.lawschool.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.SysConfig;
import com.lawschool.beans.SysLogEntity;
import com.lawschool.dao.SysConfigDao;
import com.lawschool.dao.SysLogDao;
import com.lawschool.service.SysConfigService;
import com.lawschool.util.GetUUID;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Query;
import com.lawschool.util.UtilValidate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * ClassName: SysConfigServiceImpl
 * Description: TODO
 * date: 2018-11-28 17:17
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigDao, SysConfig> implements SysConfigService {
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

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String code = (String)params.get("code");
        String value = (String)params.get("value");
        String status = (String)params.get("status");
        EntityWrapper<SysConfig> ew = new EntityWrapper<>();
        ew.setSqlSelect("id,code,value,status,remark"); ew.setSqlSelect("id,code,value,status,remarks");
        if(UtilValidate.isNotEmpty(code)){
            ew.like("code",code);
        }
        if(UtilValidate.isNotEmpty(value)){
            ew.like("value",value);
        }
        if(UtilValidate.isNotEmpty(status)){
            ew.like("status",status);
        }
        Page<SysConfig> page = this.selectPage(
                new Query<SysConfig>(params).getPage(),ew);

        return new PageUtils(page);
    }
}
