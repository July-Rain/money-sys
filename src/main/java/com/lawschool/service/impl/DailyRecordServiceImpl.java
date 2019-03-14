package com.lawschool.service.impl;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.DailyRecord;
import com.lawschool.dao.DailyRecordDao;
import com.lawschool.form.QuestForm;
import com.lawschool.form.ThemeAnswerForm;
import com.lawschool.service.DailyRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DailyRecordServiceImpl extends AbstractServiceImpl<DailyRecordDao, DailyRecord> implements DailyRecordService {

//    @Autowired
//    private CollectionService collectionService;

    /**
     * 查询用户每日一题答题记录
     * @param date 日期
     * @param userId 用户Id
     * @return
     */
    @Override
    public QuestForm getRecord(String date, String userId){
        QuestForm form = null;

        form = dao.getRecord(date, userId);

        return form;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer mySave(ThemeAnswerForm form){
        // 定义返回结果
        Integer result = 0;
        result = dao.mySave(form);

        // 保存成功
        if(result == 1){
            // 若答题错误，需要添加错题信息
            if(form.getRight() != 1){
                // collectionService.doCollection("30", form.getqId(), false);
            }
        }

        return result;
    }

    /**
     * 收藏功能
     * @param id 题目ID
     * @param recordId 记录ID
     * @param type 收藏/取消收藏
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean doCollect(String id, String recordId, Integer type){
        boolean result = true;
        //        collectionService.doCollection("20", id, type==1 ? false : true);

        if(result){
            dao.updateCollect(recordId, type);
        }

        return true;
    }
}
