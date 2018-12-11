package com.lawschool.dao.practicecenter;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.practicecenter.ThemeAnswerRecordEntity;
import com.lawschool.form.ThemeAnswerForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ThemeAnswerRecordDao extends AbstractDao<ThemeAnswerRecordEntity> {

    void save(ThemeAnswerRecordEntity entity);

    List<ThemeAnswerForm> analysisAnswer(@Param("themeId") String themeId);

}
