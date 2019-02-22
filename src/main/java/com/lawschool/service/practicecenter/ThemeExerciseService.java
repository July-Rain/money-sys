package com.lawschool.service.practicecenter;

import java.util.List;
import java.util.Map;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.practicecenter.ThemeExerciseEntity;
import com.lawschool.form.*;

/**
 * 主题练习service
 * @author xuxiang
 * @date 2018/12/4 11:50
 */
public interface ThemeExerciseService extends AbstractService<ThemeExerciseEntity> {

    /**
     * 首页list
     * @param userId
     * @return
     */
    List<ThemeExerciseForm> indexList(String userId);

    /**
     * 获取题目信息并关联答题情况
     * @param ids 题目IDS
     * @param userId 用户ID
     * @param taskId 任务配置ID
     * @return
     */
    List<QuestForm> getQuestions(List<String> ids, String userId, String taskId);

    /**
     * 展示题目信息（带分页）
     * @param id 个人任务ID
     * @param userId 当前用户ID
     * @param index 当前题目序号
     * @param index 是否回顾
     * @return
     */
    Map<String, Object> showPaper(String id, String userId, Integer index, String isReview);

    /**
     * 保存个人任务，并返回生成的ID
     * @param form
     * @return
     */
    String saveTask(ThemeExerciseForm form, String userId);

    /**
     * 保存答题记录
     * @param form
     * @param userId
     */
    void preserve(ThemeAnswerForm form, String userId, String id);

    /**
     * 提交 OR 保存
     * @param form
     * @return
     */
    AnalysisForm commit(ThemeForm form);

    /**
     * 获取分析信息
     * @param themeId
     * @return
     */
    AnalysisForm analysisAnswer(String themeId);

    /**
     * 清空重做
     * @param id
     * @return
     */
    String restart(String id);

    AnalysisForm analysis(String month, String userId);

    /**
     * 更新收藏状态
     * @param id
     * @param recordId
     * @return
     */
    boolean doCollect(String id, String recordId, Integer type);
}
