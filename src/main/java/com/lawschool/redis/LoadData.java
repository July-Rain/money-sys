package com.lawschool.redis;

import com.lawschool.beans.Dict;
import com.lawschool.service.DictService;
import com.lawschool.service.competition.BattlePlatformService;
import com.lawschool.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

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

    @Autowired
    DictService dictService;

    //数据表在Servlet容器启动时存入Redis
    @PostConstruct
    public void putDataToRedis(String SYS_DICT,Dict value){
        List<Dict> list = dictService.selectAllDict();
        for(Dict data:list){
            redisUtil.set("dictInfo",data);
        }

    }




}
