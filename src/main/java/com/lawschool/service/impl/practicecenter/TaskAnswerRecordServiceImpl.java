package com.lawschool.service.impl.practicecenter;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.practicecenter.TaskAnswerRecordEntity;
import com.lawschool.dao.practicecenter.TaskAnswerRecordDao;
import com.lawschool.form.ThemeAnswerForm;
import com.lawschool.service.practicecenter.TaskAnswerRecordService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: Moon
 * @Date: 2019/1/3 11:47
 * @Description: 练习任务--答题记录--Service实现类
 */
@Service
public class TaskAnswerRecordServiceImpl extends AbstractServiceImpl<TaskAnswerRecordDao, TaskAnswerRecordEntity>
        implements TaskAnswerRecordService {

    public boolean saveForm(ThemeAnswerForm form){

        return dao.saveForm(form);
    }

    public void saveBatch(List<ThemeAnswerForm> list){
        for(ThemeAnswerForm form : list){

            this.saveForm(form);
        }
    }

}
