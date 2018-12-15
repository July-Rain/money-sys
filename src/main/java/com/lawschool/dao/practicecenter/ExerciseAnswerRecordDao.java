package com.lawschool.dao.practicecenter;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.practicecenter.ExerciseAnswerRecordEntity;
import com.lawschool.form.ThemeAnswerForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @version V1.0
 * @Description: 随机练习Dao
 * @author: 中石电子科技 徐祥
 * @date: 2018-12-14 09:37
 */
public interface ExerciseAnswerRecordDao extends AbstractDao<ExerciseAnswerRecordEntity> {
    void save(ExerciseAnswerRecordEntity entity);

    List<ThemeAnswerForm> analysisAnswer(@Param("exerciseId") String exerciseId);
}
