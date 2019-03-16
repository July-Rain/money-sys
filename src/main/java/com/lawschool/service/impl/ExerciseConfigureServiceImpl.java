package com.lawschool.service.impl;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.base.Page;
import com.lawschool.beans.Collection;
import com.lawschool.beans.ExerciseConditionEntity;
import com.lawschool.beans.ExerciseConfigureEntity;
import com.lawschool.beans.User;
import com.lawschool.beans.personalCenter.CollectionEntity;
import com.lawschool.dao.ExerciseConfigureDao;
import com.lawschool.form.AnswerForm;
import com.lawschool.form.CommonForm;
import com.lawschool.form.QuestForm;
import com.lawschool.form.QuestionForm;
import com.lawschool.service.AnswerService;
import com.lawschool.service.ExerciseConditionService;
import com.lawschool.service.ExerciseConfigureService;
import com.lawschool.service.TestQuestionService;
import com.lawschool.service.personalCenter.CollectionService;
import com.lawschool.service.practicecenter.PaperRecordService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
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

    @Autowired private ExerciseConditionService exerciseConditionService;

    @Autowired private TestQuestionService testQuestionService;

    @Autowired private AnswerService answerService;

    @Autowired private PaperRecordService paperRecordService;

    @Autowired private CollectionService collectionService;

    /**
     * 保存组卷配置并生成试卷信息
     * @param entity
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveConfigure(ExerciseConfigureEntity entity){

        int total = 0;

        // 2.根据设置来源生成对应试卷
        Integer source = entity.getSource();

        if(source == ExerciseConfigureEntity.SOURCE_PERSON_SET
                || source == ExerciseConfigureEntity.SOURCE_DEPART_SET){
            // 3.个人组卷或部门组卷，保存条件信息
            List<ExerciseConditionEntity> list = entity.getList();

            if(CollectionUtils.isEmpty(list)){
                throw new RuntimeException("请设置配置条件");
            }
            exerciseConditionService.saveBatch(list, entity.getId());

            //生成试卷
            List<String> questList = this.doPaperForSet(list);

            total = questList.size();
            boolean result = paperRecordService.saveBatch(entity.getId(), questList);

        } else {

            User user = (User)SecurityUtils.getSubject().getPrincipal();

            // 错题组卷或重点试题组卷，生成试卷

            // 获取题目信息
            total = entity.getTotal();

            List<String> quests = new ArrayList<>();

            if(ExerciseConfigureEntity.SOURCE_COLLECT_KEY == source){
                // 重点试题
                quests = this.getQuestsFromCollection(CollectionEntity.VITAL_QUESTION, total, user.getId());
            } else {

                // 我的错题
                quests = this.getQuestsFromCollection(CollectionEntity.ERROR_QUESTION, total, user.getId());
            }

            // 保存试卷信息
            if(CollectionUtils.isNotEmpty(quests)){
                boolean result = paperRecordService.saveBatch(entity.getId(), quests);
            }
        }

        // 1.保存组卷练习设置
        entity.setTotal(total);
        dao.insert(entity);

    }

    /**
     * 根据设置生成试卷
     * @return
     */
    private List<String> doPaperForSet(List<ExerciseConditionEntity> list){
        // 用于返回的结果集，题目IDs
        List<String> result = new ArrayList<String>();

        // 封装各主题、难度对应的数量
        Map<String, Map<String, Integer>> numMap = new HashMap<String, Map<String, Integer>>();

        // 难度对应的最大数量
        int max = 0;
        // 存放主题类型
        List<String> topics = new ArrayList<String>();

        for(ExerciseConditionEntity entity : list){
            String key = entity.getTopicId();
            Map<String, Integer> tempMap = new HashMap<String, Integer>(3);
            tempMap.put("10001", entity.getPrimaryNum());
            tempMap.put("10002", entity.getMiddleNum());
            tempMap.put("10003", entity.getSeniorNum());
            numMap.put(key, tempMap);

            topics.add(key);

            // 获取数据库查询最大值
            int tempMax = getMax(entity.getPrimaryNum(), entity.getMiddleNum(), entity.getSeniorNum());
            if(max < tempMax){
                max = tempMax;
            }
        }

        // 查询各主题、难度对应的题目IDs
        List<CommonForm> questList = testQuestionService.selectByTopicAndNum(topics, max);

        for(CommonForm form : questList){

            String topic = form.getOpinion(); // 主题
            String diff = form.getKey();// 难度
            String ids = form.getValue();// 题目IDs

            Map<String, Integer> tempMap = numMap.get(topic);
            int numForSet = tempMap.get(diff);// 前端设置的难度题数

            // 若无对应题目信息，直接跳过此循环
            if(StringUtils.isBlank(ids)){ continue; }

            // 不直接用Arrays.asList得到的集合，因为Arrays.asList得到的是Arrays的内部类，没有相应的操作方法
            List<String> tempList = new ArrayList<String>(Arrays.asList(ids.split(",")));

            if(numForSet >= tempList.size()){
                // 题目不够或正好，直接添加到结果集，不用处理
                result.addAll(tempList);
            } else {

                result.addAll(tempList.subList(0, numForSet));
            }

        }

        return result;
    }

    /**
     * 获取收藏题目用于组卷练习
     * @param source
     * @param total
     * @param userId
     * @return
     */
    private List<String> getQuestsFromCollection(Integer source, Integer total, String userId){

        List<String> quests = collectionService.getQuestionsForPaper(source, total, userId);

        return quests;
    }

    /**
     * 获取最大值
     * @param param1
     * @param param2
     * @param param3
     * @return
     */
    protected Integer getMax(Integer param1, Integer param2, Integer param3){
        if(null == param1 || null == param2 || null == param3){
            throw new RuntimeException("getMax方法，参数错误...");
        }
        int result = param1 > param2 ? param1 : param2;

        result = result > param3 ? result : param3;

        return result;
    }

    /**
     * 试卷展示
     * @param id
     * @return
     */
    @Override
    public List<QuestForm> showPaper(String id){

        List<QuestForm> list = new ArrayList<>();

        // 根据组卷ID查询所有试题
        List<String> quests = paperRecordService.getQuestions(id);

        if(CollectionUtils.isNotEmpty(quests)){

            list = testQuestionService.getQuestions(quests);
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
