package com.lawschool.service.impl.practicecenter;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.User;
import com.lawschool.beans.personalCenter.CollectionEntity;
import com.lawschool.beans.practicecenter.ExerciseAnswerRecordEntity;
import com.lawschool.beans.practicecenter.ThemeAnswerRecordEntity;
import com.lawschool.dao.practicecenter.ExerciseAnswerRecordDao;
import com.lawschool.form.ThemeAnswerForm;
import com.lawschool.service.personalCenter.CollectionService;
import com.lawschool.service.practicecenter.ExerciseAnswerRecordService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @version V1.0
 * @Description: 随机练习service实现类
 * @author: 中石电子科技 徐祥
 * @date: 2018-12-14 09:36
 */
@Service
public class ExerciseAnswerRecordServiceImpl extends AbstractServiceImpl<ExerciseAnswerRecordDao, ExerciseAnswerRecordEntity>
        implements ExerciseAnswerRecordService {

    @Autowired private CollectionService collectionService;

    @Transactional(rollbackFor = Exception.class)
    public boolean saveForm(ThemeAnswerForm form){
        if(form.getRight() == 0){
            // 答错，自动收录进我的错题
            collectionService.doCollect(form.getqId(), CollectionEntity.ERROR_QUESTION, true, form.getAnswer());
        }
        return dao.saveForm(form);
    }

    public void saveBatch(List<ThemeAnswerForm> list){

        for(ThemeAnswerForm form : list){
            this.saveForm(form);
        }
    }

    /**
     * 分组统计主题答题情况
     * @param themeId
     * @return
     */
    @Override
    public List<ThemeAnswerForm> analysisAnswer(String themeId){
        List<ThemeAnswerForm> list = dao.analysisAnswer(themeId);

        return list;
    }
}
