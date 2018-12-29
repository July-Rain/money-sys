package com.lawschool.dao.practicecenter;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.practicecenter.PaperAnswerRecordEntity;
import com.lawschool.form.ThemeAnswerForm;

/**
 * @Auther: Moon
 * @Date: 2018/12/21 17:57
 * @Description:
 */
public interface PaperAnswerRecordDao extends AbstractDao<PaperAnswerRecordEntity> {

    boolean saveForm(ThemeAnswerForm form);
}
