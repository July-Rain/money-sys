package com.lawschool.service.impl;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.base.Page;
import com.lawschool.beans.ExerciseConditionEntity;
import com.lawschool.beans.ExerciseConfigureEntity;
import com.lawschool.dao.ExerciseConfigureDao;
import com.lawschool.form.AnswerForm;
import com.lawschool.form.CommonForm;
import com.lawschool.form.QuestForm;
import com.lawschool.service.AnswerService;
import com.lawschool.service.ExerciseConditionService;
import com.lawschool.service.ExerciseConfigureService;
import com.lawschool.service.TestQuestionService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Auther: Moon
 * @Date: 2018/12/20 15:20
 * @Description:
 */
@Service
public class ExerciseConfigureServiceImpl
        extends AbstractServiceImpl<ExerciseConfigureDao, ExerciseConfigureEntity>
        implements ExerciseConfigureService {

    @Autowired
    private ExerciseConditionService exerciseConditionService;

    @Autowired
    private TestQuestionService testQuestionService;

    @Autowired
    private AnswerService answerService;

    @Transactional(rollbackFor = Exception.class)
    public void saveConfigure(ExerciseConfigureEntity entity){

        // 保存配置，并保存配置条件
        dao.insert(entity);

        // 保存条件信息
        List<ExerciseConditionEntity> list = entity.getList();

        if(CollectionUtils.isEmpty(list)){
            throw new RuntimeException("请设置配置条件");
        }
        exerciseConditionService.saveBatch(list, entity.getId());

        // 获取满足条件的题目IDS，更新配置表
        String ids = "";
        int total = 0;
        for(ExerciseConditionEntity ent : list){
            int max = 0;

            int pri = ent.getPrimaryNum()==null?0:ent.getPrimaryNum();
            int mid = ent.getMiddleNum()==null?0:ent.getMiddleNum();
            int sen = ent.getSeniorNum()==null?0:ent.getSeniorNum();

            if(pri > mid){
                max = pri;
            } else {
                max = mid;
            }

            if(sen > max){
                max = sen;
            }

            List<CommonForm> listTemp = testQuestionService.selectByTopicAndNum(ent.getTopicId(), max);

            for(CommonForm form : listTemp){
                String diff = form.getKey();// 题目难度
                String value = form.getValue();
                if(StringUtils.isNotBlank(value)){
                    String[] arr = value.split(",");

                    int currentSize = 0;
                    if("10001".equals(diff)){// 初级难度
                        currentSize = pri;
                    } else if("10002".equals(diff)){// 中级难度
                        currentSize = mid;
                    } else {// 高级难度
                        currentSize = sen;
                    }

                    if(currentSize < arr.length){// 截取list
                        total += currentSize;
                        List<String> tempList = Arrays.asList(arr).subList(0, currentSize+1);
                        ids += tempList.stream().collect(Collectors.joining(",")) + ",";
                    } else {
                        total += arr.length;
                        ids += value + ",";
                    }
                }
            }
        }

        if(StringUtils.isNotBlank(ids)){
            if(ids.endsWith(",")){
                ids = ids.substring(0, ids.length()-1);
            }

            dao.updateQuestions(entity.getId(), ids, total);
        }

    }

    /**
     * 试卷展示
     * @param id
     * @return
     */
    @Override
    public List<QuestForm> showPaper(String id){

        List<QuestForm> list = new ArrayList<>();

        // 1、根据ID 获取题目ids
        String questions = dao.findQuestionsById(id);

        if(StringUtils.isBlank(questions)){
            return list;
        }

        // 2、根据题目ids获取题目信息
        if(StringUtils.isNotBlank(questions)){
            List<String> ids = Arrays.asList(questions.split(","));
            list = testQuestionService.getQuestions(ids);
        }

        return list;
    }

    /**
     * 练习中心，练习任务分页列表
     * @param page
     * @param entity
     * @return
     */
    public Page<ExerciseConfigureEntity> findPageByUser(Page<ExerciseConfigureEntity> page,
                                           ExerciseConfigureEntity entity){
        entity.setPage(page);
        page.setList(dao.findListByUser(entity));

        return page;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Override
    public Integer updateDelflag(String id){

        return dao.updateDelflag(id);
    }

    /**
     * 获取练习配置对应的题目IDs
     * @param id
     * @return
     */
    @Override
    public Map<String, String> getQuestions(String id){

        Map<String, String> map = new HashMap<String, String>(2);
        map.put("id", id);

        String quest = this.findQuestionsById(id);
        map.put("quest", quest);

        return map;
    }

    /**
     * 获取配置对应的题目IDs
     * @param id
     * @return
     */
    public String findQuestionsById(String id){

        return dao.findQuestionsById(id);
    }
}
