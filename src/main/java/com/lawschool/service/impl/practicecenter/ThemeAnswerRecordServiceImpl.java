package com.lawschool.service.impl.practicecenter;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.practicecenter.ThemeAnswerRecordEntity;
import com.lawschool.dao.practicecenter.ThemeAnswerRecordDao;
import com.lawschool.form.ThemeAnswerForm;
import com.lawschool.service.practicecenter.ThemeAnswerRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ThemeAnswerRecordServiceImpl extends AbstractServiceImpl<ThemeAnswerRecordDao, ThemeAnswerRecordEntity>
        implements ThemeAnswerRecordService {

    public boolean saveForm(ThemeAnswerForm form){

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

//        List<ThemeAnswerForm> resultList = new ArrayList<>();
//
//        for(int i=0; i<list.size(); i++){
//            ThemeAnswerForm result = new ThemeAnswerForm();
//
//            ThemeAnswerForm form = list.get(i);
//            Integer right = form.getRight();
//            String typeName = form.getTypeName();
//
//            if(right == 0){// 错误
//                result.setErrorNum(form.getRightNum());
//
//            } else {// 正确
//                result.setRightNum(form.getRightNum());
//
//            }
//
//            if(i+1 < list.size()){
//                ThemeAnswerForm formNext = list.get(i+1);
//                String typeNameNext = formNext.getTypeName();
//                if(typeName.equals(typeNameNext)){
//                    // 同一题型合并
//                    Integer rightNext = formNext.getRight();
//                    if(rightNext == 0){// 错误
//                        result.setErrorNum(form.getRightNum());
//
//                    } else {// 正确
//                        result.setRightNum(form.getRightNum());
//
//                    }
//                    i= i+1;
//
//                }
//            }
//
//            resultList.add(result);
//        }

        return list;
    }
}
