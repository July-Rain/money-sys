package com.lawschool.dao.practicecenter;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.practicecenter.ThemeAnswerRecordEntity;
import com.lawschool.form.ThemeAnswerForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 主题练习--答题记录
 */
public interface ThemeAnswerRecordDao extends AnswerRecordDao<ThemeAnswerRecordEntity> {

    /**
     * 分析答题结果
     * @param themeId
     * @return
     */
    List<ThemeAnswerForm> analysisAnswer(@Param("themeId") String themeId);

}
