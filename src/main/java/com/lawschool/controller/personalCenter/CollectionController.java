package com.lawschool.controller.personalCenter;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.base.AbstractController;
import com.lawschool.base.Page;
import com.lawschool.beans.Collection;
import com.lawschool.beans.ExerciseConfigureEntity;
import com.lawschool.beans.User;
import com.lawschool.beans.personalCenter.CollectionEntity;
import com.lawschool.form.CommonForm;
import com.lawschool.form.DoCollectionForm;
import com.lawschool.form.QuestForm;
import com.lawschool.service.ExerciseConfigureService;
import com.lawschool.service.TestQuestionService;
import com.lawschool.service.personalCenter.CollectionService;
import com.lawschool.util.RedisUtil;
import com.lawschool.util.Result;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    @Autowired private ExerciseConfigureService exerciseConfigureService;

    @Autowired private RedisUtil redisUtil;

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
             type = Integer.parseInt(String.valueOf(params.get("type")));
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
    @RequestMapping(value = "/createPaper", method = RequestMethod.POST)
    public Result createPaper(@RequestBody CommonForm form){

        User user = getUser();

        // 封装保存参数
        ExerciseConfigureEntity entity = new ExerciseConfigureEntity();
        entity.setUsers(user.getId());

        if(StringUtils.isNotBlank(form.getOpinion())){
            entity.setSource(Integer.parseInt(form.getOpinion()));
        } else {
            entity.setSource(ExerciseConfigureEntity.SOURCE_COLLECT_ERROR);
        }

        // 试题数量
        if(StringUtils.isNotBlank(form.getValue())){
            entity.setTotal(Integer.parseInt(form.getValue()));
        }

        entity.setPrefix(form.getKey());
        entity.setName(createName(form.getKey()));
        entity.setType("综合类型");
        entity.preInsert(user.getId());
        entity.setUserName(user.getFullName());
        entity.setDelFlag(0);

        exerciseConfigureService.saveConfigure(entity);

        return Result.ok().put("name", entity.getName()).put("id", entity.getId());
    }

    protected String createName(String prefix){
        Date date = new Date();
        SimpleDateFormat sim = new SimpleDateFormat("yyyyMMdd");
        String time = sim.format(date);
        String name = prefix + "" + time;

        String key = "NUMBER" + time;
        String value = redisUtil.getNumber(key, 24*60*60, 4);

        name += value;

        return name;
    }

    /**
     * 收藏/取消收藏
     * @return
     */
    @RequestMapping(value = "/doCollect", method = RequestMethod.POST)
    public Result doCollect(@RequestBody DoCollectionForm form){

        User user = getUser();

        boolean result = collectionService.doCollect(form.getSourceId(), form.getType(),
                                                    form.getIsCollect()==1?true : false,
                                                    user.getId());

        if(result){
            return Result.ok();
        } else {
            String msg = "收藏失败";
            if(form.getIsCollect() == 0){
                msg = "取消收藏失败";
            }
            return Result.error(msg);
        }
    }
}
