package com.lawschool.service.impl.practicecenter;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.Collection;
import com.lawschool.beans.User;
import com.lawschool.beans.practicecenter.TaskExerciseConfigureEntity;
import com.lawschool.beans.practicecenter.TaskExerciseEntity;
import com.lawschool.dao.practicecenter.TaskExerciseDao;
import com.lawschool.form.AnalysisForm;
import com.lawschool.form.AnswerForm;
import com.lawschool.form.QuestForm;
import com.lawschool.form.ThemeAnswerForm;
import com.lawschool.service.AnswerService;
import com.lawschool.service.CollectionService;
import com.lawschool.service.TestQuestionService;
import com.lawschool.service.practicecenter.TaskAnswerRecordService;
import com.lawschool.service.practicecenter.TaskExerciseConfigureService;
import com.lawschool.service.practicecenter.TaskExerciseService;
import com.lawschool.util.Result;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Auther: Moon
 * @Date: 2019/1/2 16:51
 * @Description: 个人练习任务实现类
 */
@Service
public class TaskExerciseServiceImpl extends AbstractServiceImpl<TaskExerciseDao, TaskExerciseEntity>
        implements TaskExerciseService {
    @Autowired
    private TestQuestionService testQuestionService;

    @Autowired
    private TaskExerciseConfigureService taskExerciseConfigureService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private TaskAnswerRecordService taskAnswerRecordService;

    @Autowired
    private CollectionService collectionService;

    /**
     * 题目展示
     * @param taskId 任务配置ID
     * @param id 个人任务ID
     * @param userId 当前用户ID
     * @param isNew 是否需要创建新的个人任务
     * @param index 当前题目序号
     * @param isReview 是否是错题回顾
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> showPaper(String taskId, String id, String userId, Boolean isNew,
                                  Integer index, String isReview){
        // 定义返回结果的Map
        Map<String, Object> resultMap = new HashMap<String, Object>();

        int total = 0;// 总题目数
        int answerNum = 0;// 已回答数

        resultMap.put("id", id);
        resultMap.put("taskId", taskId);

        TaskExerciseConfigureEntity configure = taskExerciseConfigureService.findOne(taskId);
        total = configure.getTotal();

        // 判断是否需要创建个人任务
        if(isNew){
            // 创建个人任务
            this.createTask(id, taskId, userId, total);
        } else {
            TaskExerciseEntity task = dao.findOne(id);
        }

        // 获取需要展示的题目信息
        // 封装查询参数
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("start", index);
        params.put("end", index);
        params.put("themeId", configure.getThemeId());
        params.put("difficulty", configure.getDifficulty());
        params.put("classify", configure.getClassify());
        params.put("type", configure.getType());
        params.put("", isReview);

        List<String> ids = testQuestionService.selectIdsForPage(params);

        // 没有题目则返回空值
        if(CollectionUtils.isEmpty(ids)){
            return null;
        }

        List<QuestForm> resultList = dao.getQuestions(ids, id, userId, isReview);
        if(CollectionUtils.isEmpty(resultList)){
            return null;
        }
        List<AnswerForm> answerForms = answerService.findByQuestionIds(ids);

        resultList = testQuestionService.handleAnswers(resultList, answerForms);

        // 每次只取一条
        resultMap.put("question", resultList.get(0));
        return resultMap;
    }

    /**
     * 保存答题情况
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void preserve(ThemeAnswerForm form){
        Date date = new Date();

        taskAnswerRecordService.saveForm(form);

        // 更新整体练习答题情况
        dao.updateAnswerNum(form.getTaskId(), 1);
    }

    @Override
    public boolean updateStatus(String id, Integer status){

        return dao.updateStatus(id, status);
    }

    /**
     * 创建任务
     * @param id 任务ID
     * @param taskId 任务配置ID
     * @param userId 用户ID
     * @return
     */
    private void createTask(String id, String taskId, String userId, Integer total){

        TaskExerciseEntity entity = new TaskExerciseEntity();
        entity.preInsert(userId);
        entity.setId(id);
        entity.setTaskId(taskId);
        entity.setStatus(TaskExerciseEntity.STATUS_ON);
        entity.setTotal(total);
        entity.setStatus(TaskExerciseEntity.STATUS_ON);

        int result = dao.insert(entity);

        if(result != 1){
            throw new RuntimeException("新建个人任务保存失败...");
        }
    }

    /**
     * 根据题目IDs获取题目信息，
     * 此处与testQuestionService中的区别在于需要回显用户的答题情况
     * @param ids
     * @return
     */
    @Override
    public List<QuestForm> getQuestions(List<String> ids, String taskId, String userId){

        return dao.getQuestions(ids, taskId, userId);
    }

    public AnalysisForm analysis(String month, String userId){
        AnalysisForm result = new AnalysisForm();

        return  result;
    }

    /**
     * 随机练习收藏/取消
     * @param id
     * @param recordId
     * @param type 1收藏，0取消收藏
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean doCollect(String id, String recordId, Integer type){

        User user = (User) SecurityUtils.getSubject().getPrincipal();

        if(type == 1){
            com.lawschool.beans.Collection collect = new Collection();
            collect.setId(IdWorker.getIdStr());
            collect.setType(20);
            collect.setComStucode(id);


            // 0成功，1失败
            int result = collectionService.addCollection(collect, user);
        } else {
            // 取消收藏
            boolean result = collectionService.cancle(id, user.getId());
        }

        dao.updateCollect(recordId, type);

        return true;
    }
}
