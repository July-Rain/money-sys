package com.lawschool.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.beans.Org;
import com.lawschool.beans.SysMenu;
import com.lawschool.service.OrgService;
import com.lawschool.util.Result;
import com.lawschool.util.UtilValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/org")
public class OrgController {
    @Autowired
    private OrgService orgService;

    @RequestMapping("/tree")
    public Result nav(String id){
        EntityWrapper<Org> ew = new EntityWrapper<Org>();
        List<Org> orgList = orgService.selectList(ew
                .ne("type","2").eq("is_show","1"));

        return Result.ok().put("orgList", bulidDeptTree(orgList));

    }
    /**
     * 两层循环实现建树
     * @param list 传入的树节点列表
     * @return
     */
    private List<Org> bulidDeptTree(List<Org> list) {

        List<Org> finalTrees = new ArrayList<Org>();

        for (Org org : list) {

            if ("0".equals(org.getParentId())) {
                finalTrees.add(org);
            }
            List<Org> orgList = new ArrayList<Org>();
            for (Org o: list) {
                if ((o.getParentId()).equals(o.getId())) {
                    orgList.add(o);
                }
            }
            if(UtilValidate.isNotEmpty(orgList)){
                Collections.sort(orgList);

            }
            org.setChild(orgList);

        }
        return finalTrees;
    }


}
