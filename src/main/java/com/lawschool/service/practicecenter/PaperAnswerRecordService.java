package com.lawschool.service.practicecenter;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.practicecenter.PaperAnswerRecordEntity;
import com.lawschool.form.ThemeAnswerForm;

/**
 * @Auther: Moon
 * @Date: 2018/12/21 17:58
 * @Description:
 */
public interface PaperAnswerRecordService extends AbstractService<PaperAnswerRecordEntity> {

    /**
     * 保存答题记录
     * @param form
     * @return
     */
    boolean saveForm(ThemeAnswerForm form);
}
