package com.lawschool.redis;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.beans.Org;
import com.lawschool.beans.competition.BattlePlatform;
import com.lawschool.beans.system.DictEntity;
import com.lawschool.service.OrgService;
import com.lawschool.service.system.DictionService;
import com.lawschool.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private DictionService dictionService;

    @Autowired
    private OrgService orgService;

    //数据表在Servlet容器启动时存入Redis
    @PostConstruct
    public void putDataToRedis(){

        //定义 map对应的K到时候存 比武台的 id   和 实体
        Map<String,BattlePlatform> battlePlatformMap=new HashMap<String,BattlePlatform>();

        //将这个项目一加载的list放到redis里面
        redisUtil.set("redisFrombattlePlatformMap",battlePlatformMap);
        //把数据存缓存中
        if(!redisUtil.hasKey("orgList")){
            //对象为空说明redis中没有orgList
            List<Org> orgList = orgService.queryForTree("32");
            orgList=bulidDeptTree(orgList,"32");
            redisUtil.set("orgList",orgList);
        }

        List<DictEntity> list = dictionService.selectAllDict();
        for(DictEntity data : list){
            redisUtil.set("dictInfo",data);
        }


        if(!redisUtil.hasKey("permOrg")){
            //权限设置-部门数据(OrgId)
            List<String> sysDeptEntities = orgService.selectList(new EntityWrapper<Org>().in("ORG_TYPE","10,70,-1,0")).stream().map(e->e.getOrgId()).collect(Collectors.toList());
            redisUtil.set("permOrg",sysDeptEntities);
        }

    }


    /**
     * 两层循环实现建树
     * @param list 传入的树节点列表
     * @return
     */
    private List<Org> bulidDeptTree(List<Org> list,String id) {

        List<Org> finalTrees = new ArrayList<Org>();

        for (Org org : list) {

            if ("32".equals(org.getLocalOrgCode())) {
                finalTrees.add(org);
            }
            List<Org> orgList = new ArrayList<Org>();
            for (Org o: list) {
                if ((o.getParentId()).equals(org.getOrgId())) {
                    orgList.add(o);
                }
            }
            org.setChild(orgList);

        }
        return finalTrees;
    }


}
