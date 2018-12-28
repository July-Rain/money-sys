package com.lawschool.dao.practicecenter;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.practicecenter.TaskAnswerRecordEntity;
import com.lawschool.form.ThemeAnswerForm;
import org.apache.ibatis.annotations.Param;

/**
 * @Auther: Moon
 * @Date: 2018/12/21 17:57
 * @Description:
 */
public interface TaskAnswerRecordDao extends AbstractDao<TaskAnswerRecordEntity> {

    boolean saveForm(ThemeAnswerForm form);
}
