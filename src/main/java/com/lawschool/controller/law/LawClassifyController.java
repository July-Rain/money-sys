package com.lawschool.controller.law;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.base.AbstractController;
import com.lawschool.beans.SysMenu;
import com.lawschool.beans.law.LawClassifyEntity;
import com.lawschool.service.law.LawClassifyLibService;
import com.lawschool.service.law.LawClassifyService;
import com.lawschool.util.Result;
import com.lawschool.util.UtilValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ClassName: LawClassifyController
 * Description: 法律分类controller
 * date: 2018-12-8 12:56
 *
 * @author MengyuWu
 * @since JDK 1.8
 */

@RestController
@RequestMapping("/law")
public class LawClassifyController extends AbstractController {
    @Autowired
    private LawClassifyService classifyService;
    @Autowired
    private LawClassifyLibService classifyLibService;

    @RequestMapping("/tree")
    public Result nav(String id){
        EntityWrapper<LawClassifyEntity> ew = new EntityWrapper<LawClassifyEntity>();
        List<LawClassifyEntity> classifyList = classifyService.selectList(ew
                .eq("del_flag","0"));
        return Result.ok().put("classifyList", bulidDeptTree(classifyList));
    }
    /**
     * @Author MengyuWu
     * @Description 两层循环实现建树
     * @Date 15:30 2018-12-8
     * @Param [list]
     * @return java.util.List<com.lawschool.beans.law.LawClassifyEntity>
     **/
    
    private List<LawClassifyEntity> bulidDeptTree(List<LawClassifyEntity> list) {

        List<LawClassifyEntity> finalTrees = new ArrayList<LawClassifyEntity>();

        for (LawClassifyEntity classify : list) {

            if ("0".equals(classify.getParentId())) {
                finalTrees.add(classify);
            }
            List<LawClassifyEntity> classifyList = new ArrayList<LawClassifyEntity>();
            for (LawClassifyEntity c : list) {
                if ((c.getParentId()).equals(classify.getClassifyId())) {
                    classifyList.add(c);
                }
            }
            if(UtilValidate.isNotEmpty(classifyList)){
                Collections.sort(classifyList);

            }
            classify.setList(classifyList);

        }
        return finalTrees;
    }

}
