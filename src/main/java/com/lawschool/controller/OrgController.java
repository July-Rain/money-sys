package com.lawschool.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.base.AbstractController;
import com.lawschool.beans.Org;
import com.lawschool.beans.User;
import com.lawschool.service.OrgService;
import com.lawschool.util.RedisUtil;
import com.lawschool.util.Result;
import com.lawschool.util.UtilMisc;
import com.lawschool.util.UtilValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/org")
public class OrgController extends AbstractController {
    @Autowired
    private OrgService orgService;
    @Autowired
    private RedisUtil redisUtil;
    @RequestMapping("/tree")
    public Result nav(String id){
        User user = getUser();
        EntityWrapper<Org> ew = new EntityWrapper<Org>();
        //TODO
        id="32";
        //把数据存缓存中
        //Object o=redisUtil.get("orgList");
        if(!redisUtil.hasKey("orgList")){
            //对象为空说明redis中没有orgList
            List<Org> orgList = orgService.queryForTree(id);
            orgList=bulidDeptTree(orgList,id);
            redisUtil.set("orgList",orgList);
        }
        //JSONArray jsonArray= JSONArray.fromObject(redisUtil.get("orgList"));
        JSONArray jsonArray = JSONObject.parseArray(redisUtil.get("orgList"));
        return Result.ok().put("orgList",jsonArray);

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
//            if(UtilValidate.isNotEmpty(orgList)){
//                Collections.sort(orgList);
//
//            }
            org.setChild(orgList);

        }
        return finalTrees;
    }

//    @RequestMapping("/queryForTree")
//    public Object queryForTree(){
//        String orgCode="320000000000";
//        List<Map<String, Object>> maps = orgService.queryForTree(orgCode);
//        System.out.println(maps);
//        return maps;
//    }

}
