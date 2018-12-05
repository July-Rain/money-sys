package com.lawschool.redis;

import com.lawschool.service.competition.BattlePlatformService;
import com.lawschool.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 *
 * @Descriptin  redis服务启动时调用
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */

@Configuration
public class LoadData {

    @Autowired
    private RedisUtil redisUtil;

    @PostConstruct
    public void loadData()
    {
        redisUtil.set("idd","123456");
    }




}
