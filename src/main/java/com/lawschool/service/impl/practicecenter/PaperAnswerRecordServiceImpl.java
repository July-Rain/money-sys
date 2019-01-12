package com.lawschool.service.impl.practicecenter;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.practicecenter.PaperAnswerRecordEntity;
import com.lawschool.dao.practicecenter.PaperAnswerRecordDao;
import com.lawschool.form.ThemeAnswerForm;
import com.lawschool.service.practicecenter.PaperAnswerRecordService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: Moon
 * @Date: 2018/12/21 18:00
 * @Description:
 */
@Service
public class PaperAnswerRecordServiceImpl extends AbstractServiceImpl<PaperAnswerRecordDao, PaperAnswerRecordEntity>
        implements PaperAnswerRecordService {

    public boolean saveForm(ThemeAnswerForm form){

        return dao.saveForm(form);
    }

    public void saveBatch(List<ThemeAnswerForm> list){
        for(ThemeAnswerForm form : list){

            this.saveForm(form);
        }
    }
}
