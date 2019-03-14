package com.lawschool.service.practicecenter;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.practicecenter.PaperRecordEntity;
import com.lawschool.form.CommonForm;

import java.util.List;

public interface PaperRecordService extends AbstractService<PaperRecordEntity> {

    boolean saveBatch(String configureId, List<String> list);

    List<String> getQuestions(String taskId);
}
