package com.lawschool.service.impl.practicecenter;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.User;
import com.lawschool.beans.practicecenter.PaperExerciseEntity;
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
