package com.lawschool.service.practicecenter;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.practicecenter.PaperExerciseEntity;
import com.lawschool.form.AnalysisForm;
import com.lawschool.form.CommonForm;
import com.lawschool.form.QuestionForm;
import com.lawschool.form.ThemeAnswerForm;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Moon
 * @Date: 2018/12/21 17:58
 * @Description:
 */
public interface PaperExerciseService extends AbstractService<PaperExerciseEntity> {

    /**
     * 更新任务状态
     * @param id
     * @param status
     * @return
     */
    boolean updateStatus(String id, Integer status);

    /**
     * 统计分析
     * @param month
     * @param userId
     * @return
     */
    AnalysisForm analysis(String month, String userId);

    /**
     * 获取组卷练习试题信息
     * @param configureId 组卷配置信息
     * @param id 个人练习ID
     * @oaram isNew 是否为新的练习
     * @return
     */
    List<QuestionForm> showQuestions(String configureId, String id, boolean isNew);
}
