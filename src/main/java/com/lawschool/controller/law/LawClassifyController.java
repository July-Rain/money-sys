package com.lawschool.controller.law;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.base.AbstractController;
import com.lawschool.beans.law.LawClassifyEntity;
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
    @RequestMapping("/alltree")
    public Result alltree(){
        EntityWrapper<LawClassifyEntity> ew = new EntityWrapper<LawClassifyEntity>();
        List<LawClassifyEntity> classifyList = classifyService.selectList(ew
                .eq("del_flag","0"));
        LawClassifyEntity classifyEntity = new LawClassifyEntity();
        classifyEntity.setClassifyId("all");
        classifyEntity.setId("");
        classifyEntity.setParentId("0");
        classifyEntity.setClassifyName("全部");
        classifyEntity.setOrderNum(0);
        classifyList.add(classifyEntity);
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
        Collections.sort(finalTrees);
        return  finalTrees;
    }
    @RequestMapping("/zTree")
    public Result zTree(){
        List<TaskDesicEntity> classifyList = classifyService.queryClassTree();
        return Result.ok().put("classifyList",classifyList);
    }
    @RequestMapping("/classTree")
    public Result classTree(){
        List<TaskDesicEntity> classifyList = classifyService.queryClassTree();
        return Result.ok().put("classifyList",bulidClassTree(classifyList) );
    }
    private List<TaskDesicEntity> bulidClassTree(List<TaskDesicEntity> list) {

        List<TaskDesicEntity> finalTrees = new ArrayList<TaskDesicEntity>();

        for (TaskDesicEntity classify : list) {

            if ("0".equals(classify.getInfoParentId())) {
                finalTrees.add(classify);
            }
            List<TaskDesicEntity> classifyList = new ArrayList<TaskDesicEntity>();
            for (TaskDesicEntity c : list) {
                if ((c.getInfoParentId()).equals(classify.getInfoId())) {
                    classifyList.add(c);
                }
            }
            classify.setChild(classifyList);

        }
        return finalTrees;
    }
}
