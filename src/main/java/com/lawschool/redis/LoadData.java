package com.lawschool.redis;

import com.lawschool.beans.Dict;
import com.lawschool.beans.User;
import com.lawschool.beans.competition.BattlePlatform;
import com.lawschool.service.DictService;
import com.lawschool.service.competition.BattlePlatformService;
import com.lawschool.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void putDataToRedis(){

        //定义 map对应的K到时候存 比武台的 id   和 实体
        Map<String,BattlePlatform> battlePlatformMap=new HashMap<String,BattlePlatform>();

        //将这个项目一加载的list放到redis里面
        redisUtil.set("redisFrombattlePlatformMap",battlePlatformMap);

//
//        List<Dict> list = dictService.selectAllDict();
//        for(Dict data:list){
//            redisUtil.set("dictInfo",data);
//        }

    }




}
