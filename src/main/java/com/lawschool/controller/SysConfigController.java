package com.lawschool.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.beans.SysConfig;
import com.lawschool.service.SysConfigService;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * ClassName: SysConfigController
 * Description: TODO
 * date: 2018-11-28 18:12
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/sysconfig")
public class SysConfigController {
    @Autowired
    private SysConfigService configService;

    @RequestMapping("/list")
    public Result list(@RequestParam Map<String, Object> params){
        SysConfig config = new SysConfig();
        config.setId(1);
        return Result.ok().put("data", configService.queryConfig(config));
    }

}
