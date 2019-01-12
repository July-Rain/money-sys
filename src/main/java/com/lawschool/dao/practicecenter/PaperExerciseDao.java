package com.lawschool.dao.practicecenter;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.practicecenter.PaperExerciseEntity;
import com.lawschool.form.AnalysisForm;
import com.lawschool.form.CommonForm;
import com.lawschool.form.QuestForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther: Moon
 * @Date: 2018/12/21 17:56
 * @Description:
 */
public interface PaperExerciseDao extends AbstractDao<PaperExerciseEntity> {

    List<CommonForm> findByUserAndConIds(@Param("userId") String userId,
                                         @Param("list") List<String> list);

    /**
     * 获取题目信息和用户答题情况
     * @param list
     * @param id
     * @return
     */
    List<QuestForm> getQuestAndAnswer(@Param("list") List<String> list,
                                      @Param("id") String id,
                                      @Param("userId") String userId);

    /**
     * 更新练习任务的整体答题情况
     * @param id
     * @param num
     * @return
     */
    boolean updateAnswerNum(@Param("id") String id,
                            @Param("num") Integer num);

    boolean updateStatus(@Param("id") String id, @Param("status") Integer status);

    AnalysisForm analysis(@Param("month") String month, @Param("userId") String userId);
}
