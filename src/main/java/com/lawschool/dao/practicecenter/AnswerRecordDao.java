package com.lawschool.dao.practicecenter;

import com.lawschool.base.AbstractDao;
import com.lawschool.form.ThemeAnswerForm;

/**
 * @Auther: Moon
 * @Date: 2019/1/4 14:38
 * @Description: 练习中心--答题记录Dao--Base
 */
public interface AnswerRecordDao<T> extends AbstractDao<T> {
    /**
     * 保存答题记录
     * @param form
     * @return
     */
    boolean saveForm(ThemeAnswerForm form);
}
