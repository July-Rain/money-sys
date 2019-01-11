package com.lawschool.service.practicecenter;

import com.lawschool.base.AbstractService;
import com.lawschool.form.ThemeAnswerForm;

import java.util.List;

/**
 * @Auther: Moon
 * @Date: 2019/1/4 14:48
 * @Description:
 */
public interface AnswerRecordService<T> extends AbstractService<T> {
    /**
     * 批量保存答题记录
     * @param list
     */
    void saveBatch(List<ThemeAnswerForm> list);

    /**
     * 保存答题记录
     * @param form
     * @return
     */
    boolean saveForm(ThemeAnswerForm form);
}
