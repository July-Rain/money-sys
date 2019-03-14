package com.lawschool.controller.personalCenter;

import com.lawschool.base.AbstractController;
import com.lawschool.base.Page;
import com.lawschool.beans.Collection;
import com.lawschool.beans.personalCenter.CollectionEntity;
import com.lawschool.form.CommonForm;
import com.lawschool.form.QuestForm;
import com.lawschool.service.TestQuestionService;
import com.lawschool.service.personalCenter.CollectionService;
import com.lawschool.util.Result;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Moon
 * @Date: 2019/3/8 15:20
 * @Description:
 */
@RestController
@RequestMapping("/collection")
public class CollectionController extends AbstractController{

    @Autowired private CollectionService collectionService;

    @Autowired private TestQuestionService testQuestionService;



    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(@RequestParam Map<String, Object> params){
        CollectionEntity entity = new CollectionEntity();

        // 封装查询参数
        entity.setCreateUser(getUser().getId());
        if(params.get("specialKnowledgeId") != null){
            entity.setSpecialKnowledgeId(String.valueOf(params.get("specialKnowledgeId")));
        }

        if(params.get("questionDifficulty") != null){
            entity.setQuestionDifficulty(String.valueOf(params.get("questionDifficulty")));
        }

        if(params.get("questionType") != null){
            entity.setQuestionType(String.valueOf(params.get("questionType")));
        }

        Integer type = 0;
        if(params.get("type") != null){
             Integer.parseInt(String.valueOf(params.get("type")));
        }
        entity.setType(type);

        Page<CollectionEntity> page = null;
        Integer num = 0;
        if(type == CollectionEntity.VITAL_COURSE){// 重点课程

        } else {
            page = collectionService.findPage(new Page<CollectionEntity>(params), entity);
            // 获取非主观题的个数
            num = collectionService.getTotalQuestion(getUser().getId(), type);
        }

        return Result.ok().put("page", page).put("num", num);
    }

    /**
     * 查看
     * @param qid 资源ID
     * @param type 类型
     * @return
     */
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public Result show(@RequestParam("qid") String qid,
                       @RequestParam("type") Integer type){

        if(type == CollectionEntity.VITAL_COURSE){
            // 重点试题

            return Result.ok();
        } else {
            // 题目信息,包括选项信息
            List<String> params = new ArrayList<>(1);
            params.add(qid);

            List<QuestForm> list = testQuestionService.getQuestions(params);
            QuestForm form = null;// 返回结果
            if(CollectionUtils.isNotEmpty(list)){

                form = list.get(0);
            }

            return Result.ok().put("form", form);
        }

    }

    /**
     * 取消收藏
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public Result delete(@PathVariable("id") String id){

        List<String> ids = new ArrayList<String>();
        ids.add(id);

        collectionService.delete(ids);

        return Result.ok();
    }

    /**
     * 组卷
     * @param form
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Result createPaper(@RequestBody CommonForm form){

        return Result.ok();
    }

}
