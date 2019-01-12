package com.lawschool.dao.practicecenter;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.practicecenter.PaperAnswerRecordEntity;
import com.lawschool.form.ThemeAnswerForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther: Moon
 * @Date: 2018/12/21 17:57
 * @Description: 组卷练习--答题记录Dao
 */
public interface PaperAnswerRecordDao extends AnswerRecordDao<PaperAnswerRecordEntity> {

    /**
     * 分析答题结果
     * @param id
     * @return
     */
    List<ThemeAnswerForm> analysisAnswer(@Param("id") String id);
}
