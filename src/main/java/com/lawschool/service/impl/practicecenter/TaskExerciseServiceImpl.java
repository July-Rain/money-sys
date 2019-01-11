package com.lawschool.service.impl.practicecenter;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.practicecenter.TaskExerciseConfigureEntity;
import com.lawschool.beans.practicecenter.TaskExerciseEntity;
import com.lawschool.dao.practicecenter.TaskExerciseDao;
import com.lawschool.form.AnalysisForm;
import com.lawschool.form.AnswerForm;
import com.lawschool.form.QuestForm;
import com.lawschool.form.ThemeAnswerForm;
import com.lawschool.service.AnswerService;
import com.lawschool.service.TestQuestionService;
import com.lawschool.service.practicecenter.TaskAnswerRecordService;
import com.lawschool.service.practicecenter.TaskExerciseConfigureService;
import com.lawschool.service.practicecenter.TaskExerciseService;
import com.lawschool.util.Result;
import org.apache.commons.collections.CollectionUtils;
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

    /**
     * 题目展示
     * @param taskId 任务配置ID
     * @param id 个人任务ID
     * @param userId 当前用户ID
     * @param isNew 是否需要创建新的个人任务
     * @param limit 每页显示题目数量
     * @param page 当前页（为-1时，根据answerNum去计算，否则直接取当前页）
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> showPaper(String taskId, String id, String userId, Boolean isNew,
                                  Integer limit, Integer page){
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
            answerNum = task.getAnswerNum();
        }

        // 计算取题范围
        Integer[] pageResult = CommonUtils.computeDataRange(total, answerNum, limit, page);
        if(pageResult[0] == -1){
            resultMap.put("list", null);
            return resultMap;
        }

        resultMap.put("page", pageResult[2]);

        // 获取需要展示的题目信息
        // 封装查询参数
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("start", pageResult[0]);
        params.put("end", pageResult[1]);
        params.put("themeId", configure.getThemeId());
        params.put("difficulty", configure.getDifficulty());
        params.put("classify", configure.getClassify());
        params.put("type", configure.getType());

        List<String> ids = testQuestionService.selectIdsForPage(params);

        if(CollectionUtils.isEmpty(ids)){
            resultMap.put("list", null);
            return resultMap;
        }

        // 获取题目详细信息
        List<QuestForm> resultList = this.getQuestions(ids, id, userId);

        if(CollectionUtils.isEmpty(resultList)){
            resultMap.put("list", null);
            return resultMap;
        }
        resultMap.put("list", resultList);

        List<AnswerForm> answerForms = answerService.findByQuestionIds(ids);

        if(CollectionUtils.isEmpty(answerForms)){
            return resultMap;
        }
        resultList = testQuestionService.handleAnswers(resultList, answerForms);
        resultMap.put("list", resultList);
        resultMap.put("count", total);

        return resultMap;
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

}
