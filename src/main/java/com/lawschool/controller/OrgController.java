package com.lawschool.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.base.AbstractController;
import com.lawschool.beans.Org;
import com.lawschool.beans.User;
import com.lawschool.service.OrgService;
import com.lawschool.util.Result;
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

    @RequestMapping("/tree")
    public Result nav(String id){
        User user = getUser();
        EntityWrapper<Org> ew = new EntityWrapper<Org>();
        //TODO
        id="32";
        List<Org> orgList = orgService.queryForTree(id);

        return Result.ok().put("orgList", bulidDeptTree(orgList,id));

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
