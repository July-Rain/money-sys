package com.lawschool.service.practicecenter;

import com.lawschool.base.AbstractService;
import com.lawschool.base.Page;
import com.lawschool.beans.ExerciseConfigureEntity;
import com.lawschool.beans.practicecenter.TaskExerciseEntity;
import com.lawschool.form.CommonForm;
import com.lawschool.form.QuestForm;
import com.lawschool.form.ThemeAnswerForm;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Moon
 * @Date: 2018/12/21 17:58
 * @Description:
 */
public interface TaskExerciseService extends AbstractService<TaskExerciseEntity> {

    /**
     * 查询练习任务，根据用户和练习任务list
     * @param userId
     * @param list
     * @return
     */
    List<CommonForm> findByUserAndConIds(String userId, List<String> list);

    /**
     * 组卷练习展示试卷信息
     * @param configureId 练习配置ID
     * @param id 个人练习ID
     * @param userId 用户ID
     * @param isNew 是否需要创建个人练习任务
     * @param limit 每页显示条数
     * @param page 当前页
     * @return 包含2个结果，一个题目list，一个总页数
     */
    Map<String, Object> showPaper(String configureId, String id,
                                  String userId, Boolean isNew,
                                  Integer limit, Integer page);

    /**
     * 组卷练习保存答题情况
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
}
