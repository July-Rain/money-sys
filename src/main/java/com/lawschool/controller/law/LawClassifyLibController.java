package com.lawschool.controller.law;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.base.AbstractController;
import com.lawschool.beans.law.LawClassifyEntity;
import com.lawschool.beans.law.LawClassifyLibEntity;
import com.lawschool.beans.law.TaskDesicEntity;
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
 * Description: 法律库controller
 * date: 2018-12-8 12:56
 *
 * @author MengyuWu
 * @since JDK 1.8
 */

@RestController
@RequestMapping("/classlib")
public class LawClassifyLibController extends AbstractController {
    @Autowired
    private LawClassifyLibService classifyLibService;

    @RequestMapping("/tree")
    public Result nav(String id){
        EntityWrapper<LawClassifyLibEntity> ew = new EntityWrapper<LawClassifyLibEntity>();
        List<LawClassifyLibEntity> classifyLibList = classifyLibService.selectList(ew
                .eq("del_flag","0"));
        return Result.ok().put("data", bulidDeptTree(classifyLibList));
    }
    /**
     * @Author MengyuWu
     * @Description 两层循环实现建树
     * @Date 15:30 2018-12-8
     * @Param [list]
     * @return java.util.List<com.lawschool.beans.law.LawClassifyLibEntity>
     **/
    
    private List<LawClassifyLibEntity> bulidDeptTree(List<LawClassifyLibEntity> list) {

        List<LawClassifyLibEntity> finalTrees = new ArrayList<LawClassifyLibEntity>();

        for (LawClassifyLibEntity classify : list) {

            if ("0".equals(classify.getParentId())) {
                finalTrees.add(classify);
            }
            List<LawClassifyLibEntity> classifyList = new ArrayList<LawClassifyLibEntity>();
            for (LawClassifyLibEntity c : list) {
                if ((c.getParentId()).equals(classify.getLibId())) {
                    classifyList.add(c);
                }
            }
            classify.setChild(classifyList);

        }
        return finalTrees;
    }
}
