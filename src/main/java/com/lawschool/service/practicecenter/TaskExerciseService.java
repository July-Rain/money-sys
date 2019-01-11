package com.lawschool.service.practicecenter;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.practicecenter.TaskExerciseEntity;
import com.lawschool.form.AnalysisForm;
import com.lawschool.form.QuestForm;
import com.lawschool.form.ThemeAnswerForm;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Moon
 * @Date: 2019/1/2 16:50
 * @Description: 个人联系任务Service
 */
public interface TaskExerciseService extends AbstractService<TaskExerciseEntity> {

    /**
     * 展示题目信息（带分页）
     * @param taskId 任务配置ID
     * @param id 个人任务ID
     * @param userId 当前用户ID
     * @param isNew 是否需要创建新的个人任务
     * @param limit 每页显示题目数量
     * @return
     */
    Map<String, Object> showPaper(String taskId, String id, String userId, Boolean isNew,
                                  Integer limit, Integer page);

    /**
     * 获取题目信息并关联答题情况
     * @param ids 题目IDS
     * @param userId 用户ID
     * @param taskId 任务配置ID
     * @return
     */
    List<QuestForm> getQuestions(List<String> ids, String userId, String taskId);

    /**
     * 练习保存答题情况
     * @param list
     */
    void preserve(List<ThemeAnswerForm> list, String userId);

    /**
     * 更新任务状态
     * @param id
     * @param status
     * @return
     */
    boolean updateStatus(String id, Integer status);

    AnalysisForm analysis(String month, String userId);
}
