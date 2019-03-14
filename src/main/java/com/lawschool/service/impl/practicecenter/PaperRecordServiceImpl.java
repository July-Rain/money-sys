package com.lawschool.service.impl.practicecenter;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.practicecenter.PaperRecordEntity;
import com.lawschool.dao.practicecenter.PaperRecordDao;
import com.lawschool.form.CommonForm;
import com.lawschool.service.practicecenter.PaperRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Moon
 * @Date: 2019/3/11 18:58
 * @Description: 组卷练习service实现类
 */
@Service
public class PaperRecordServiceImpl extends AbstractServiceImpl<PaperRecordDao, PaperRecordEntity> implements PaperRecordService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveBatch(String configureId, List<String> list){
        List<CommonForm> saveList = new ArrayList<>(list.size());

        for(String qid : list){
            CommonForm form = new CommonForm();
            form.setKey(IdWorker.getIdStr());
            form.setValue(qid);
            form.setOpinion(configureId);

            saveList.add(form);
        }

        return dao.saveBatch(saveList);
    }

    @Override
    public List<String> getQuestions(String taskId){

        return dao.getQuestions(taskId);
    }
}
