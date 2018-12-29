package com.lawschool.service.impl.practicecenter;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.base.Page;
import com.lawschool.beans.ExerciseConfigureEntity;
import com.lawschool.beans.practicecenter.TaskExerciseEntity;
import com.lawschool.dao.practicecenter.TaskExerciseDao;
import com.lawschool.form.AnswerForm;
import com.lawschool.form.CommonForm;
import com.lawschool.form.QuestForm;
import com.lawschool.form.ThemeAnswerForm;
import com.lawschool.service.AnswerService;
import com.lawschool.service.ExerciseConfigureService;
import com.lawschool.service.TestQuestionService;
import com.lawschool.service.practicecenter.TaskAnswerRecordService;
import com.lawschool.service.practicecenter.TaskExerciseService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Auther: Moon
 * @Date: 2018/12/21 17:59
 * @Description:
 */
@Service
public class TaskExerciseServiceImpl extends AbstractServiceImpl<TaskExerciseDao, TaskExerciseEntity>
        implements TaskExerciseService {

    @Autowired
    private ExerciseConfigureService exerciseConfigureService;

    @Autowired
    private TestQuestionService testQuestionService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private TaskAnswerRecordService taskAnswerRecordService;

    public List<CommonForm> findByUserAndConIds(String userId, List<String> list){
        List<CommonForm> resultList = dao.findByUserAndConIds(userId, list);

        return resultList;
    }

    /**
     * 组卷练习展示试卷页面
     *
     * @param configureId 练习配置ID
     * @param id 个人练习ID
     * @param userId 用户ID
     * @param isNew 是否需要创建个人练习任务
     * @param limit 每页显示条数
     * @param page 当前页
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> showPaper(String configureId, String id,
                                     String userId, Boolean isNew,
                                     Integer limit, Integer page){

        Map<String, Object> resultMap = new HashMap<String, Object>();

        // 存放个人练习ID和所有题目IDs
        Map<String, String> map = new HashMap<String, String>(2);

        // 1.判断是否需要生成练习任务
        if(isNew){// 对应的练习配置尚未开始练习
            map = this.createTask(configureId, userId, id);

        } else {
            // 获取所有题目
            map = exerciseConfigureService.getQuestions(configureId);
        }

        // 2.根据题目IDs获取题目信息
        List<QuestForm> resultList = new ArrayList<>();
        if(StringUtils.isNotBlank(map.get("quest"))){
            // 有题目才去查询
            List<String> ids = Arrays.asList(map.get("quest").split(","));
            resultMap.put("count", ids.size());

            // 截取ids
            int[] pageArr = this.handlePage(ids.size(), limit, page);
            List<String> tempList = ids.subList(pageArr[0], pageArr[1]);

            resultList = this.getQuestions(id, tempList, isNew, userId, limit, page);

        } else {
            resultMap.put("count", 0);
        }

        resultMap.put("list", resultList);

        return resultMap;
    }

    /**
     * 生成组卷练习任务，并返回题目IDs
     * @param configureId
     * @return
     */
    private Map<String, String> createTask(String configureId, String userId, String id){
        Map<String, String> result = new HashMap<String, String>(2);
        String quest = exerciseConfigureService.findQuestionsById(configureId);

        result.put("quest", quest);

        TaskExerciseEntity entity = new TaskExerciseEntity();
        entity.setId(id);
        entity.setConfigureId(configureId);
        entity.setQuestions(quest);
        entity.setStatus(TaskExerciseEntity.STATUS_ON);
        entity.setUserId(userId);
        entity.setNo(1);
        if(StringUtils.isBlank(quest)){
            entity.setTotal(0);
        } else {
            entity.setTotal(quest.split(",").length);
        }

        dao.insert(entity);
        result.put("id", entity.getId());

        return result;
    }

    /**
     * 根据题目IDs获取题目信息
     * @param id 自定义练习ID
     * @param list 题目IDs
     * @param isNeed 是否需要查询答题情况
     * @return
     */
    private List<QuestForm> getQuestions(String id, List<String> list,
                                         boolean isNeed, String userId,
                                         Integer limit, Integer page){

        List<QuestForm> resultList = new ArrayList<>();

        if(!isNeed){
            // 查询题目信息并附带答题情况
            resultList = this.getQuestAndAnswer(list, id, userId);
        } else {
            resultList = testQuestionService.findByIds(list);
        }

        if(CollectionUtils.isEmpty(resultList)){
            return resultList;
        }
        List<AnswerForm> answerForms = answerService.findByQuestionIds(list);

        if(CollectionUtils.isEmpty(answerForms)){
            return resultList;
        }
        resultList = testQuestionService.handleAnswers(resultList, answerForms);

        return resultList;
    }

    /**
     * 获取题目信息和用户答题情况
     * @param list
     * @param id
     * @return
     */
    private List<QuestForm> getQuestAndAnswer(List<String> list, String id, String userId){

        return dao.getQuestAndAnswer(list, id, userId);
    }

    /**
     * 处理分页参数
     *
     * @param total
     * @param limit
     * @param page
     * @return
     */
    private int[] handlePage(int total, int limit, int page){

        int[] result = new int[2];

        int start = (page - 1) * limit;
        int end = page * limit;
        if(start > total || end < start){
            throw new RuntimeException("分页参数错误");
        }

        if(end > total){
            end = total;
        }

        result[0] = start;
        result[1] = end;

        return result;
    }

    /**
     * 保存答题情况
     * @param list
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void preserve(List<ThemeAnswerForm> list, String userId){
        Date date = new Date();

        for(ThemeAnswerForm form : list){
            form.setCreateUser(userId);
            form.setId(IdWorker.getIdStr());
            form.setCreateTime(date);
            taskAnswerRecordService.saveForm(form);
        }

        // 更新整体练习答题情况
        int num = list.size();
        if(num > 0){
            dao.updateAnswerNum(list.get(0).getTaskId(), num);
        }
    }

    @Override
    public boolean updateStatus(String id, Integer status){

        return dao.updateStatus(id, status);
    }
}
