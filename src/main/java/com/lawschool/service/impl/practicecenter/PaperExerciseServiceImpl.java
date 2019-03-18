package com.lawschool.service.impl.practicecenter;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.User;
import com.lawschool.beans.practicecenter.PaperExerciseEntity;
import com.lawschool.dao.practicecenter.PaperAnswerRecordDao;
import com.lawschool.dao.practicecenter.PaperExerciseDao;
import com.lawschool.form.*;
import com.lawschool.service.AnswerService;
import com.lawschool.service.ExerciseConfigureService;
import com.lawschool.service.TestQuestionService;
import com.lawschool.service.practicecenter.PaperAnswerRecordService;
import com.lawschool.service.practicecenter.PaperExerciseService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
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
public class PaperExerciseServiceImpl extends AbstractServiceImpl<PaperExerciseDao, PaperExerciseEntity>
        implements PaperExerciseService {

    @Autowired private PaperAnswerRecordDao paperAnswerRecordDao;

    @Override
    public boolean updateStatus(String id, Integer status){

        return dao.updateStatus(id, status);
    }

    @Override
    public AnalysisForm analysis(String month, String userId){
        AnalysisForm result = new AnalysisForm();

        result = dao.analysis(month, userId);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<QuestionForm> showQuestions(String configureId, String id, boolean isNew){

        User user = (User) SecurityUtils.getSubject().getPrincipal();

        // 判断是否需要保存练习记录
        if(isNew){
            PaperExerciseEntity entity = new PaperExerciseEntity();
            entity.setId(id);
            entity.setConfigureId(configureId);
            entity.setStatus(PaperExerciseEntity.STATUS_ON);
            entity.setUserId(user.getId());
            dao.insert(entity);
        }

        // 定义返回结果集
        List<QuestionForm> result = new ArrayList<QuestionForm>();

        result = dao.showQuestions(configureId, user.getId());

        // 获取选项信息
        List<AnswerForm> answerList = dao.getAnswers(configureId);

        // 处理选项和题目信息
        handleQuestion(result, answerList);

        return result;
    }

    /**
     * 暂存答题记录
     * @param list 题目信息
     * @param taskId 任务记录ID
     * @param isCommit true(提交)/false(暂存)
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean save(List<QuestionForm> list, String taskId, boolean isCommit){
        User user = (User)SecurityUtils.getSubject().getPrincipal();

        // 存放新增的答题记录
        List<QuestionForm> saveList = new ArrayList<>();

        // 存放更新的答题记录
        List<QuestionForm> updateList = new ArrayList<>();

        // 存放我的错题
        List<QuestionForm> errList = new ArrayList<>();

        // 处理操作参数
        Integer answerNum = handSaveList(list, saveList, updateList, errList, isCommit);

        // 保存答题记录
        if(CollectionUtils.isNotEmpty(saveList)){
            paperAnswerRecordDao.batchInsert(saveList, taskId, user.getId());
        }

        // 更新答题记录
        if(CollectionUtils.isNotEmpty(updateList)){
            paperAnswerRecordDao.batchUpdate(updateList, taskId, user.getId());
        }



        // 更新练习记录信息(答题数、练习状态)
        Integer status = 0;
        if(isCommit){
            status = PaperExerciseEntity.STATUS_OFF;
        }
        updateRecord(taskId, answerNum, status);

        return true;
    }

    /**
     * 处理保存参数
     * @param list
     * @param saveList
     * @param updateList
     * @param errList
     * @param isCommit
     * @return 答题数量
     */
    protected Integer handSaveList(List<QuestionForm> list, List<QuestionForm> saveList,
                                   List<QuestionForm> updateList, List<QuestionForm> errList, boolean isCommit){
        Integer result = 0;

        for(QuestionForm form : list){
            String recordId = form.getRecordId();// 记录ID
            String type = form.getType();// 题型

            if("10005".equals(type)){
                // 多选
                if(CollectionUtils.isNotEmpty(form.getUserAnswerList())){
                    form.setUserAnswer(String.join(",", form.getUserAnswerList()));

                    if(isCommit){
                        // 提交时，判断答题正确性
                        String[] arr = form.getAnswer().split(",");
                        List<String> rightList = new ArrayList<String>(Arrays.asList(arr));
                        if(rightList.containsAll(form.getUserAnswerList()) && form.getUserAnswerList().containsAll(rightList)){
                            form.setRight(1);
                        }else {
                            form.setRight(0);
                            errList.add(form);
                        }

                    }
                }

            } else {

                form.setUserAnswer(form.getUserAnswerStr());
                if(isCommit) {
                    // 提交时，判断答题正确性
                    if (StringUtils.isNotBlank(form.getUserAnswer())) {
                        if (form.getUserAnswer().equals(form.getAnswer())) {
                            form.setRight(1);
                        } else {

                            form.setRight(0);
                            errList.add(form);
                        }
                    }
                }
            }

            if(StringUtils.isBlank(recordId)){
                form.setRecordId(IdWorker.getIdStr());

                // 新增
                saveList.add(form);
            } else {

                updateList.add(form);
            }

            // 统计答题数量
            if(StringUtils.isNotBlank(form.getUserAnswer())){
                result ++;
            }
        }

        return result;
    }

    /**
     * 更新组卷练习的状态
     * @param taskId ID
     * @param answerNum 回答数量
     * @param status 练习记录状态 （为NULL不处理）
     * @return
     */
    private boolean updateRecord(String taskId, Integer answerNum, Integer status){

        return dao.updateRecord(taskId, answerNum, status);
    }

    /**
     * 关联题目和选项信息
     * @param questList
     * @param answerList 按题目ID排序（必须）
     */
    protected void handleQuestion(List<QuestionForm> questList, List<AnswerForm> answerList){

        for(QuestionForm form : questList){
            // 题目ID
            String qId = form.getQuestId();
            List<AnswerForm> childList = new ArrayList<>();

            // 0、未开始，1、进行中，2、已结束
            int isEnd = 0;
            for(AnswerForm answer : answerList){
                // 题目ID
                String key = answer.getQuestionId();

                if(key.equals(qId)){
                    childList.add(answer);
                    isEnd = 1;

                } else {
                    if(isEnd == 1){
                        isEnd = 2;
                    }
                }

                if(isEnd == 2){
                    break;
                }
            }

            // 关联选项
            form.setOptions(childList);

            // 去除已处理选项
            answerList.removeAll(childList);
        }
    }
}
